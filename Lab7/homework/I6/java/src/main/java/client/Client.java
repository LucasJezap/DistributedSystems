package client;

import inverse.InverseRequest;
import inverse.InverseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import power.PowerRequest;
import power.PowerServiceGrpc;
import server.SquareServer;
import square.SquareRequest;
import square.SquareServiceGrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Client {

    private final ManagedChannel channel;
    private final Integer proxyPort;
    private final SquareServiceGrpc.SquareServiceBlockingStub squareServiceStub;
    private final PowerServiceGrpc.PowerServiceBlockingStub powerServiceStub;
    private final InverseServiceGrpc.InverseServiceBlockingStub inverseServiceStub;

    public Client(int proxyPort) {
        this.proxyPort = proxyPort;
        this.channel = ManagedChannelBuilder.forAddress("127.0.0.1", proxyPort).usePlaintext().build();
        this.squareServiceStub = SquareServiceGrpc.newBlockingStub(channel);
        this.powerServiceStub = PowerServiceGrpc.newBlockingStub(channel);
        this.inverseServiceStub = InverseServiceGrpc.newBlockingStub(channel);
    }

    public void run() throws InterruptedException, IOException {
        System.out.println(SquareServer.printTimestamp() +
                "The client has started - looking for reverse proxy on port " + proxyPort + ".");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;

        while (running) {
            System.out.print(SquareServer.printTimestamp() + "Enter command: ");
            String line = in.readLine();
            String[] split = line.split(" ");
            try {
                switch (split[0].toLowerCase()) {
                    case "square":
                        float square = squareServiceStub.squareNumber(SquareRequest.newBuilder()
                                .setNumber(Float.parseFloat(split[1]))
                                .build()).getSquare();
                        System.out.println(SquareServer.printTimestamp() +
                                "The square of " + split[1] + " is " + square + ".");
                        break;
                    case "power":
                        float power = powerServiceStub.powerNumber(PowerRequest.newBuilder()
                                .setNumber(Float.parseFloat(split[1]))
                                .build()).getPower();
                        System.out.println(SquareServer.printTimestamp() +
                                "The power of " + split[1] + " is " + power + ".");
                        break;
                    case "inverse":
                        float inverse = inverseServiceStub.inverseNumber(InverseRequest.newBuilder()
                                .setNumber(Float.parseFloat(split[1]))
                                .build()).getInverse();
                        System.out.println(SquareServer.printTimestamp() +
                                "The inverse of " + split[1] + " is " + inverse + ".");
                        break;
                    case "exit":
                    case "quit":
                    case "q":
                        System.out.println(SquareServer.printTimestamp() + "Exiting client.");
                        running = false;
                    default:
                        System.out.println(SquareServer.printTimestamp() + "Wrong command!");
                }
            } catch (StatusRuntimeException e) {
                System.out.println(SquareServer.printTimestamp() +
                        "The proxy at port " + proxyPort + " is unavailable or " +
                        "the servers are not up.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(SquareServer.printTimestamp() + "You did not provide the argument.");
            }
        }

        this.channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client(Integer.parseInt(args[0]));
        client.run();
    }
}
