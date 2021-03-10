import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class ChatClient {
    public static volatile boolean threadsStopped = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        int serverPortNumber = 1234;

        InetAddress address = InetAddress.getByName("localhost");
        Socket serverSocket = new Socket(address, serverPortNumber);

        DataInputStream dis = new DataInputStream(serverSocket.getInputStream());
        DataOutputStream dos = new DataOutputStream(serverSocket.getOutputStream());

        ExecutorService threads = Executors.newFixedThreadPool(2);

        String msg = dis.readUTF();
        System.out.println("Hello " + msg + ". To end chat write 'exit' or 'logout'");

        ClientReadMessage crm = new ClientReadMessage(dis);
        ClientSendMessage csm = new ClientSendMessage(dos, msg);
        var fut1 = threads.submit(crm);
        var fut2 = threads.submit(csm);

        // TODO: add Ctrl + C in Client, close threads in client when ctrl+c in server
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                fut1.cancel(true);
//            }
//        });

        while (!threadsStopped) {
        }

        threads.shutdown();
        threads.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("You have been disconnected from the server.");
    }
}
