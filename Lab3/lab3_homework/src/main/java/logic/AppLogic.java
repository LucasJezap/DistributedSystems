package logic;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import zookeeper.ZookeeperApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class is responsible for general logic of the app.
 */
public class AppLogic {
    final ZookeeperApp zookeeperApp;
    final ZooKeeper zooKeeper;

    public AppLogic(ZookeeperApp zookeeperApp, ZooKeeper zooKeeper) {
        this.zookeeperApp = zookeeperApp;
        this.zooKeeper = zooKeeper;
    }

    public void getChildrenCount(String nodePath) {
        try {
            System.out.println("Children count of " + nodePath + ": " + zooKeeper.getAllChildrenNumber(nodePath));
        } catch (KeeperException e) {
            System.out.println("There's no node " + nodePath);
            zookeeperApp.failure();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTree(String nodePath) {
        List<String> tree = new ArrayList<>();
        try {
            DFS(nodePath, tree);
        } catch (KeeperException e) {
            System.out.println("There's no node " + nodePath);
            zookeeperApp.failure();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tree;
    }

    void DFS(String nodePath, List<String> tree) throws InterruptedException, KeeperException {
        Stack<String> stack = new Stack<>();
        stack.push(nodePath);
        while (!stack.empty()) {
            String currentPath = stack.pop();
            for (String childPath : zooKeeper.getChildren(currentPath, false)) {
                stack.push(String.join("/", currentPath, childPath));
            }
            tree.add(currentPath);
        }
    }
}
