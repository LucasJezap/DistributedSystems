package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientThread extends Thread {
    String nickname;
    private final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);

            this.nickname = br.readLine();
            System.out.println("Client " + nickname + " has connected to the server.");

            if (ChatServer.nicknames.contains(nickname)) {
                for (String message : ChatServer.pastMessages) {
                    pw.println(message);
                }
            } else
                ChatServer.nicknames.add(nickname);

            while (!Thread.currentThread().isInterrupted()) {
                String message;
                try {
                    message = br.readLine();
                    if (message != null)
                        ChatServer.pastMessages.add(nickname + ": " + message);
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

    public void closeSocket() throws IOException {
        clientSocket.close();
        ChatServer.clients.remove(this);
        System.out.println("Client " + nickname + " has disconnected from the server.");
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}