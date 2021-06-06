package server;

import io.grpc.ServerBuilder;
import service.SquareService;

import java.io.IOException;
import java.sql.Timestamp;

public class SquareServer {
    private io.grpc.Server server;
    private final int port;

    public SquareServer(int port) {
        this.port = port;
    }

    public static String printTimestamp() {
        return "[" + new Timestamp(System.currentTimeMillis()) + "] ";
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server != null) {
                server.shutdown();
            }
            System.out.println(printTimestamp() + "The server is closing...");
        }));
    }

    public void run() throws IOException, InterruptedException {
        server = ServerBuilder
                .forPort(port)
                .addService(new SquareService())
                .build()
                .start();
        System.out.println(printTimestamp() + "The square server has started on port " + port + ".");

        addShutdownHook();
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SquareServer server = new SquareServer(Integer.parseInt(args[0]));
        server.run();
        System.out.println(printTimestamp() + "The server has closed.");
    }
}
