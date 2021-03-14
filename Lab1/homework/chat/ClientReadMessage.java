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
            Thread.sleep(10);
            while (chatClient.isOnline()) {
                if (br.ready()) {
                    String message = br.readLine();
                    if (message.length() > 20) {
                        break;
                    }
                    System.out.print('\r' + message + "\n" + chatClient.getNickname() + ": ");
                }
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Closing reading thread...");
    }
}