package logic;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.KeeperException.Code;

import java.util.Arrays;

@SuppressWarnings("all")
public class DataProcessor implements Watcher, AsyncCallback.StatCallback {
    ZooKeeper zooKeeper;
    String znode;
    Watcher watcher;
    DataProcessorListener dataProcessorListener;
    boolean isDead;
    byte[] previousSavedData;

    public DataProcessor(ZooKeeper zooKeeper, String znode, Watcher watcher,
                         DataProcessorListener dataProcessorListener) {
        this.zooKeeper = zooKeeper;
        this.znode = znode;
        this.watcher = watcher;
        this.dataProcessorListener = dataProcessorListener;

        zooKeeper.exists(znode, true, this, null);
    }

    @Override
    public void processResult(int reasonCode, String path, Object ctx, Stat stat) {
        boolean exists = false;
        switch (reasonCode) {
            case Code.Ok -> exists = true;
            case Code.NoNode -> {
            }
            case Code.SessionExpired, Code.NoAuth -> {
                isDead = true;
                dataProcessorListener.sessionToClose(reasonCode);
                return;
            }
            default -> zooKeeper.exists(znode, true, this, null);
        }

        byte[] previousData = null;
        if (exists) {
            try {
                previousData = zooKeeper.getData(znode, false, null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
        if ((previousData == null && previousData != previousSavedData)
                || (previousData != null && Arrays.equals(previousSavedData, previousData))) {
            dataProcessorListener.existenceStatusChanged(previousData);
            previousSavedData = previousData;
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();
        if (watchedEvent.getType() == Event.EventType.None) {
            switch (watchedEvent.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    isDead = true;
                    dataProcessorListener.sessionToClose(Code.SessionExpired);
                    break;
            }
        } else {
            if (path != null && path.equals(znode)) {
                zooKeeper.exists(znode, true, this, null);
            }
        }
        if (watcher != null) {
            watcher.process(watchedEvent);
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
