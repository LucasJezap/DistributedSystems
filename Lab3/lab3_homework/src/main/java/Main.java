import connection.ConnectionExecutor;

class Main {
    static String znode = "/z";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err
                    .println("Please provide arguments: hostPort program [args ...]");
            System.exit(2);
        }

        String hostPort = args[0];
        String[] exec = new String[args.length - 1];
        System.arraycopy(args, 1, exec, 0, exec.length);
        try {
            new ConnectionExecutor(hostPort, znode, filename, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
