package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientReadMessage extends Thread {
    private final BufferedReader br;
    private final ChatClient chatClient;

    ClientReadMessage(Socket serverSocket, ChatClient chatClient) throws IOException {
        this.br = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.chatClient = chatClient;
    }

    @Override
    public void run() {
        System.out.println("TCP read thread: UP");
        try {
            while (chatClient.isOnline()) {
                if (br.ready()) {
                    String message = br.readLine();
                    System.out.print('\r' + message + "\n" + chatClient.getNickname() + ": ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}