import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientSendMessage implements Runnable {
    Scanner scanner;
    DataOutputStream dos;

    public ClientSendMessage(Scanner scanner, DataOutputStream dos) {
        this.scanner = scanner;
        this.dos = dos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = scanner.nextLine();
                dos.writeUTF(msg);
                if (msg.equals("exit") || msg.equals("logout")) {
                    break;
                }
            } catch (NoSuchElementException | IOException e) {
                break;
            }
        }
        System.out.println("The send thread has been closed.");
    }
}
