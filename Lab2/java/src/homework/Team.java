package homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

class Team extends Thread {
    private final String teamName;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
    Product[] orders;
    TeamSendMessage teamSendMessage;
    TeamReadMessage teamReadMessage;
    int numberOfProducts;
    volatile boolean readDone = true;
    volatile boolean sendDone = true;

    Team() throws IOException {
        System.out.print("\nProvide your team name: ");
        this.teamName = br.readLine();
    }

    Team(String teamName) {
        this.teamName = teamName;
    }


    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                teamReadMessage.interrupt();
                teamSendMessage.interrupt();
                Thread.sleep(10);
                System.out.println("\n\nThe team thread is going to close.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        try {
            teamSendMessage = new TeamSendMessage(this, this.teamName);
            teamReadMessage = new TeamReadMessage(this, this.teamName);
            teamReadMessage.start();
            teamSendMessage.start();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("\nWelcome " + teamName);
        System.out.println("The list of available products: " +
                Arrays.stream(Product.values()).limit(Product.values().length - 1).collect(Collectors.toList()));

        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("""

                        Please type what you need to order (Don't worry about letter capitalization).
                        You can order multiple products at once, e.g.,
                        Oxygen Oxygen Backpack Boots
                        Your order:\s""");
                orders = Arrays.stream(scanner.nextLine().split(" ")).
                        map(String::toUpperCase).
                        map(Product::getProduct).
                        toArray(Product[]::new);
                numberOfProducts = orders.length;
                sendDone = false;
                readDone = false;
                while (!sendDone || !readDone) {
                    Thread.onSpinWait();
                }
            } catch (Exception e) {
                break;
            }

        }
    }

    public static void main(String[] argv) throws Exception {
        Team newTeam;
        if (argv.length == 0)
            newTeam = new Team();
        else
            newTeam = new Team(argv[0]);
        newTeam.start();
    }

}
