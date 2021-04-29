package event;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import zookeeper.ZookeeperApp;

import java.io.IOException;

/**
 * This class is responsible for handling events caused by ZooKeeper watchers.
 */
public class EventHandler implements Watcher {

    final String nodePath;
    final String executablePath;
    final ZookeeperApp zookeeperApp;
    final Runtime runtime;
    Process executable;

    public EventHandler(ZookeeperApp zookeeperApp, String nodePath, String executablePath) {
        this.nodePath = nodePath;
        this.zookeeperApp = zookeeperApp;
        this.executablePath = executablePath;
        this.runtime = Runtime.getRuntime();
        this.executable = null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.Expired) {
            zookeeperApp.stopApp();
        } else if (watchedEvent.getPath().equals(nodePath)) {
            switch (watchedEvent.getType()) {
                case NodeCreated -> startExecutable();
                case NodeDeleted -> closeExecutable();
            }
        } else if (watchedEvent.getPath().startsWith(nodePath)) {
            switch (watchedEvent.getType()) {
                case NodeCreated, NodeDeleted -> zookeeperApp.appLogic.getChildrenCount(nodePath);
            }
        }
    }

    void startExecutable() {
        try {
            executable = runtime.exec(executablePath);
            zookeeperApp.appLogic.getChildrenCount(nodePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeExecutable() {
        if (executable != null) {
            try {
                executable.destroy();
                executable.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executable = null;
        }
    }
}