import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class ChatClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        int serverPortNumber = 1234;
        Scanner scanner = new Scanner(System.in);

        InetAddress address = InetAddress.getByName("localhost");
        Socket serverSocket = new Socket(address, serverPortNumber);

        DataInputStream dis = new DataInputStream(serverSocket.getInputStream());
        DataOutputStream dos = new DataOutputStream(serverSocket.getOutputStream());

        ExecutorService threads = Executors.newFixedThreadPool(2);

        ClientReadMessage crm = new ClientReadMessage(dis);
        ClientSendMessage csm = new ClientSendMessage(scanner, dos);
        threads.submit(crm);
        threads.submit(csm);

        String msg = dis.readUTF();
        System.out.println("Hello " + msg + ". To end chat write 'exit' or 'logout'");

        // TODO: add Ctrl + C in Client, close threads in client when ctrl+c in server

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                threads.shutdownNow();
            }
        });

        threads.shutdown();
        threads.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("You have been disconnected from the server.");
    }
}
