import java.io.DataInputStream;
import java.io.IOException;

public class ClientReadMessage implements Runnable {
    DataInputStream dis;

    public ClientReadMessage(DataInputStream dis) {
        this.dis = dis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = dis.readUTF();
                if (msg.equals("exit"))
                    break;
                System.out.println('\r' + msg);
            } catch (IOException e) {
                break;
            }
        }
        System.out.println("The read thread has been closed.");
    }
}
