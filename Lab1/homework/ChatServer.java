import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

@SuppressWarnings("all")
public class ChatServer {

    static int clientCount = 0;
    static Vector<ClientThread> clients = new Vector<>();
    static Vector<Thread> clientThreads = new Vector<>();


    public static void main(String[] args) throws IOException {

        System.out.println("Chat Server\nWaiting for clients to connect...");
        int portNumber = 1234;
        ServerSocket serverSocket = new ServerSocket(portNumber);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < clients.size(); i++) {
                    try {
                        clients.get(i).disconnectClient();
                        clientThreads.get(i).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Socket clientSocket = null;

        while (true) {
            clientSocket = serverSocket.accept();
            String nickname = "User" + (clients.size() + 1);
            System.out.println(nickname + " is trying to connect to the server...");

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            ClientThread ct = new ClientThread(clientSocket, nickname, dis, dos);
            Thread t = new Thread(ct);

            clients.add(ct);
            clientThreads.add(t);
            t.start();
            clientCount++;
            System.out.println(nickname + " has connected successfully to the server.");
            System.out.println("Connected clients: " + clientCount);
            ct.dos.writeUTF(nickname);
        }
    }


}
