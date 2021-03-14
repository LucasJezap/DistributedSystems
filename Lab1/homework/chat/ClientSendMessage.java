package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("all")
class ClientSendMessage extends Thread {

    private final PrintWriter pw;
    private final ChatClient chatClient;
    private final Scanner scanner;
    private final ClientUdpThread clientUdpThread;

    ClientSendMessage(Socket serverSocket, ChatClient chatClient, ClientUdpThread clientUdpThread) throws IOException {
        this.pw = new PrintWriter(serverSocket.getOutputStream(), true);
        this.chatClient = chatClient;
        this.scanner = new Scanner(System.in);
        this.clientUdpThread = clientUdpThread;

        this.pw.println(chatClient.getNickname());
    }

    @Override
    public void run() {
        System.out.println("TCP send thread: UP\n");
        while (true) {
            System.out.print(chatClient.getNickname() + ": ");
            String message = null;

            try {
                message = scanner.nextLine();
            } catch (Exception e) {
                break;
            }

            if (message.equals("logout") || message.equals("exit")) {
                chatClient.setOnline(false);
                break;
            } else if (message.length() > 2 && (message.substring(0, 3).equals("-m ") ||
                    message.substring(0, 3).equals("-M "))) {
                chatClient.clientMulticastThread.sendMessage(chatClient.getNickname() +
                        ": " + message.substring(3) + " (From Multicast)");
            } else if (message.length() > 2 && (message.substring(0, 3).equals("-u ") ||
                    message.substring(0, 3).equals("-U "))) {
                clientUdpThread.sendMessage(chatClient.getNickname() +
                        ": " + message.substring(3) + " (From UDP)");
            } else {
                pw.println(message);
            }
        }
        System.out.println("\n\nClosing sending thread...");
    }
}