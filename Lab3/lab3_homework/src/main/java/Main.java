import zookeeper.ZookeeperApp;

public class Main {

    /**
     * hostList is a list of all Zookeeper servers
     * it should be in a format: ip:port,ip:port,ip:port
     */
    public static String hostList;
    public static String nodePath = "/z";
    public static String pathToExecutable;
    public static int timeout = 5000;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("""
                    Not enough arguments!
                    Correct invocation: java -jar xxx.jar <host_list> <path_to_executable>

                    host_list should be in a format: ip:port,ip:port,...

                    example usage: java -jar xxx.jar 127.0.0.1:2180,127.0.0.1:2181 C:\\Windows\\System32\\mspaint.exe""");
            System.exit(-1);
        }

        hostList = args[0];
        pathToExecutable = args[1];
        try {
            ZookeeperApp zookeeperApp = new ZookeeperApp(hostList, nodePath, timeout, pathToExecutable);
            Thread thread = new Thread(zookeeperApp);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}