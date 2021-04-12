package connection;

import logic.DataProcessor;
import logic.DataProcessorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionExecutor implements Watcher, Runnable, DataProcessorListener {
    String hostPort;
    String znode;
    String filename;
    String[] exec;
    ZooKeeper zooKeeper;
    DataProcessor dataProcessor;
    Process child;

    public ConnectionExecutor(String hostPort, String znode, String filename,
                              String[] exec) throws IOException {
        this.hostPort = hostPort;
        this.znode = znode;
        this.filename = filename;
        this.exec = exec;
        this.zooKeeper = new ZooKeeper(hostPort, 3000, this);
        this.dataProcessor = new DataProcessor(zooKeeper, znode, null, this);
    }

    static class StreamWriter extends Thread {
        OutputStream outputStream;
        InputStream inputStream;

        StreamWriter(OutputStream outputStream, InputStream inputStream) {
            this.outputStream = outputStream;
            this.inputStream = inputStream;
            start();
        }

        public void run() {
            byte[] data = new byte[100];
            int reasonCode;
            try {
                while ((reasonCode = inputStream.read(data)) > 0) {
                    outputStream.write(data, 0, reasonCode);
                }
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dataProcessor.isDead()) {
                    wait();
                }
            }
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void existenceStatusChanged(byte[] data) {
        if (data == null) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException ignored) {
                }
            }
            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filename);
                fileOutputStream.write(data);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(System.out, child.getInputStream());
                new StreamWriter(System.err, child.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sessionToClose(int reasonCode) {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        dataProcessor.process(watchedEvent);
    }
}
