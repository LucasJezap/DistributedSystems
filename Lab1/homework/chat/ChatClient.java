package chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ChatClient extends Thread {
    private static final int port = 1234;
    private static final String host = "localhost";

    private final String nickname;
    private boolean isOnline = false;

    static final String multicastAddress = "225.225.225.225";
    static final int multicastPort = 1235;

    final ClientUdpThread clientUdpThread;
    final ClientMulticastThread clientMulticastThread;
    ClientSendMessage clientSendMessage;
    ClientReadMessage clientReadMessage;


    ChatClient(String nickname) {
        this.nickname = nickname;
        this.clientUdpThread = new ClientUdpThread(host, port, this);
        this.clientMulticastThread = new ClientMulticastThread(this);
    }

    @Override
    public void run() {
        isOnline = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                clientReadMessage.interrupt();
                Thread.sleep(10);
                System.out.println("Closing main thread...\nYou have been disconnected from the server");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        try {
            Socket serverSocket = new Socket(host, port);
            clientSendMessage = new ClientSendMessage(serverSocket, this, clientUdpThread);
            clientReadMessage = new ClientReadMessage(serverSocket, this);

            clientUdpThread.start();
            clientMulticastThread.start();
            clientReadMessage.start();
            Thread.sleep(10);
            clientSendMessage.start();

            clientReadMessage.join();
            clientSendMessage.join();

            clientUdpThread.closeSocket();
            clientUdpThread.join();

            clientMulticastThread.closeSocket();
            clientMulticastThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String nickname = null;
        if (args.length > 1) {
            System.out.println("Too much arguments!");
            System.exit(-1);
        } else if (args.length == 1)
            nickname = args[0];
        else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please provide nickname: ");
            nickname = scanner.nextLine();
        }
        System.out.println("\nCHAT CLIENT\nWelcome " + nickname + ".\n\nTo use UDP write -u/U before message.\n" +
                "To use Multicast write -m/M before message.\n\n" +
                "Server port: " + port + "\nUDP port: " + port + "\nMulticast address: " + multicastAddress +
                "\nMulticast port: " + multicastPort + '\n');
        ChatClient client = new ChatClient(nickname);
        client.start();
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
