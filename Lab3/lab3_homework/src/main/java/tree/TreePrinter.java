package tree;

import zookeeper.ZookeeperApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class is responsible for printing the tree of Zookeeper nodes.
 */
public class TreePrinter implements Runnable {

    final ZookeeperApp zookeeperApp;
    final String nodePath;
    final BufferedReader buffer;
    boolean isThreadAlive;

    public TreePrinter(ZookeeperApp zookeeperApp, String nodePath) {
        this.zookeeperApp = zookeeperApp;
        this.nodePath = nodePath;
        this.buffer = new BufferedReader(new InputStreamReader(System.in));
        this.isThreadAlive = true;
    }

    @Override
    public void run() {
        while (this.isThreadAlive) {
            try {
                System.out.println("To print the tree structure type 'show' or 'show flat'");
                String line = this.buffer.readLine();
                if (line.equals("show")) {
                    List<String> tree = zookeeperApp.appLogic.getTree(this.nodePath);
                    for (String child : tree) {
                        StringTokenizer tokenizer = new StringTokenizer(child, "/");
                        String token = null;
                        while (tokenizer.hasMoreTokens()) {
                            if (token != null)
                                System.out.print("\t");
                            token = tokenizer.nextToken();
                        }
                        if (token != null)
                            System.out.println("/" + token);
                    }
                } else if (line.equals("show flat")) {
                    List<String> tree = zookeeperApp.appLogic.getTree(this.nodePath);
                    for (String child : tree) {
                        System.out.println(child);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        try {
            this.isThreadAlive = false;
            this.buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}