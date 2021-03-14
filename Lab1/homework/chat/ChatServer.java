package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

@SuppressWarnings("all")
public class ChatServer {

    public static final Vector<ClientThread> clients = new Vector<>();
    public static final Vector<String> pastMessages = new Vector<>();
    public static final Vector<String> nicknames = new Vector<>();
    private static final int port = 1234;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("\nCHAT SERVER\n\nServer Port: " + port + "\nUDP Port: " + port + "\n\nTCP channel: UP");

        ServerUdpThread serverUdpThread = new ServerUdpThread(port);
        serverUdpThread.start();
        System.out.println("UDP channel: UP\n\nWaiting for clients...\n");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

        }));

        while (true) {
            ClientThread connectedClient = new ClientThread(serverSocket.accept());
            clients.add(connectedClient);
            connectedClient.start();
        }
    }


}
