package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientThread extends Thread {
    private String nickname;
    private final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            this.nickname = br.readLine();
            System.out.println("Client " + nickname + " has connected to the server.");

            while (true) {
                String message;
                try {
                    message = br.readLine();
                } catch (SocketException e) {
                    closeSocket();
                    break;
                }

                if (message != null) {
                    System.out.println(nickname + ": " + message);

                    for (Thread clientThread : ChatServer.clients) {
                        ClientThread ct = (ClientThread) clientThread;
                        PrintWriter pw2 = new PrintWriter(ct.getClientSocket().getOutputStream(), true);
                        if (ct != this)
                            pw2.println(nickname + ": " + message);
                    }
                } else {
                    closeSocket();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSocket() throws IOException {
        clientSocket.close();
        ChatServer.clients.remove(this);
        System.out.println("Client " + nickname + " has disconnected from the server.");
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}