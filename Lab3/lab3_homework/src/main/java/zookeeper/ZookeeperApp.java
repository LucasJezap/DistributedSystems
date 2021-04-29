package zookeeper;

import event.EventHandler;
import logic.AppLogic;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import tree.TreePrinter;

import java.io.IOException;

/**
 * This class is the main class of the app. It connects all threads and logic.
 */
public class ZookeeperApp implements Runnable {

    public final AppLogic appLogic;
    final ZooKeeper zooKeeper;
    final TreePrinter treePrinter;
    final Thread treePrinterThread;
    boolean isAppAlive;

    public ZookeeperApp(String hostlist, String nodePath, int timeout, String executablePath) throws IOException, KeeperException, InterruptedException {
        this.isAppAlive = true;
        EventHandler eventHandler = new EventHandler(this, nodePath, executablePath);
        this.zooKeeper = new ZooKeeper(hostlist, timeout, eventHandler);
        this.treePrinter = new TreePrinter(this, nodePath);
        this.treePrinterThread = new Thread(treePrinter);
        this.appLogic = new AppLogic(this, zooKeeper);
        this.addNodeWatcher(nodePath);
    }

    @Override
    public void run() {
        treePrinterThread.start();
        synchronized (this) {
            while (isAppAlive) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopApp() {
        synchronized (this) {
            treePrinter.stopThread();
            isAppAlive = false;
            // it breaks run() loop
            notify();
        }
    }

    private void addNodeWatcher(String nodePath) throws KeeperException, InterruptedException {
        zooKeeper.addWatch(nodePath, AddWatchMode.PERSISTENT_RECURSIVE);
    }

    public void failure() {
        ZooKeeper.States serviceState = zooKeeper.getState();
        if (!serviceState.isConnected() || !serviceState.isAlive()) {
            stopApp();
        }
    }
}