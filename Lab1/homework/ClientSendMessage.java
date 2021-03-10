import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientSendMessage implements Runnable {
    Scanner scanner = new Scanner(System.in);
    DataOutputStream dos;
    String nickname;

    public ClientSendMessage(DataOutputStream dos, String nickname) {
        this.nickname = nickname;
        this.dos = dos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.print(nickname + ": ");
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