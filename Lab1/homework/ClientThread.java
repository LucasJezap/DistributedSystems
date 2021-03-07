import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientThread implements Runnable {
    String nickname;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket clientSocket;
    boolean isOnline;

    public ClientThread(Socket clientSocket, String nickname, DataInputStream dis, DataOutputStream dos) {
        this.clientSocket = clientSocket;
        this.nickname = nickname;
        this.dis = dis;
        this.dos = dos;
        this.isOnline = true;
    }

    public void disconnectClient() {
        if (!isOnline)
            return;
        System.out.println(nickname + " is disconnecting from the server...");
        try {
            this.isOnline = false;
            this.clientSocket.close();
            ChatServer.clientCount--;
            this.dis.close();
            this.dos.close();
            System.out.println(nickname + " successfully disconnected from the server.");
        } catch (IOException e) {
            System.out.println(nickname + " has encountered errors while disconnecting from the server.");
        }
    }

    @Override
    public void run() {
        String messageReceived;
        while (true) {
            try {
                messageReceived = dis.readUTF();
                System.out.println(nickname + " has send a message: " + messageReceived);

                if (messageReceived.equals("logout") || messageReceived.equals("exit")) {
                    dos.writeUTF("exit");
                    disconnectClient();
                    break;
                }

                for (ClientThread client : ChatServer.clients) {
                    client.dos.writeUTF(nickname + ": " + messageReceived);
                }

            } catch (IOException e) {
                disconnectClient();
                break;
            }
        }
    }
}