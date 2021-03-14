package homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

class Admin extends Thread {
    private final String adminName;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    AdminReadMessage adminReadMessage;
    AdminSendMessage adminSendMessage;

    Admin() throws IOException {
        System.out.print("\nProvide your admin name: ");
        this.adminName = br.readLine();

    }

    Admin(String adminName) {
        this.adminName = adminName;
    }


    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                adminReadMessage.interrupt();
                adminSendMessage.interrupt();
                Thread.sleep(10);
                System.out.println("\n\nThe admin thread is going to close.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println("\nWelcome " + adminName);
        System.out.println("The list of available products: " +
                Arrays.stream(Product.values()).limit(Product.values().length - 1).collect(Collectors.toList()));

        System.out.print("""

                As an admin you see every message in the system.
                You can also send message to all teams or all suppliers.
                To do so, type:
                -t message (to send message to all teams)
                -s message (to send message to all suppliers)
                -a message (to send message to all teams and suppliers)
                
                """);

        try {
            adminReadMessage = new AdminReadMessage(this, this.adminName);
            adminSendMessage = new AdminSendMessage(this, this.adminName);
            adminReadMessage.start();
            adminSendMessage.start();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) throws Exception {
        Admin newAdmin;
        if (argv.length == 0)
            newAdmin = new Admin();
        else
            newAdmin = new Admin(argv[0]);

        newAdmin.start();
    }
}
