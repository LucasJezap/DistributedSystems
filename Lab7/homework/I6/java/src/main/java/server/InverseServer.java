package server;

import io.grpc.ServerBuilder;
import service.InverseService;

import java.io.IOException;

public class InverseServer {
    private io.grpc.Server server;
    private final int port;

    public InverseServer(int port) {
        this.port = port;
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server != null) {
                server.shutdown();
            }
            System.out.println(SquareServer.printTimestamp() + "The server is closing...");
        }));
    }

    public void run() throws IOException, InterruptedException {
        server = ServerBuilder
                .forPort(port)
                .addService(new InverseService())
                .build()
                .start();
        System.out.println(SquareServer.printTimestamp() + "The inverse server has started on port " + port + ".");

        addShutdownHook();
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        InverseServer server = new InverseServer(Integer.parseInt(args[0]));
        server.run();
        System.out.println(SquareServer.printTimestamp() + "The server has closed.");
    }
}
