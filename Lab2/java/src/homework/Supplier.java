package homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

class Supplier extends Thread {
    private final String supplierName;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);
    ArrayList<Product> products;
    Product order;
    String teamName;
    volatile boolean readyToOrder = true;
    SupplierReadMessage supplierReadMessage;
    SupplierSendMessage supplierSendMessage;

    Supplier() throws IOException {
        System.out.print("\nProvide your supplier name: ");
        this.supplierName = br.readLine();

    }

    Supplier(String supplierName) {
        this.supplierName = supplierName;
    }


    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                supplierSendMessage.interrupt();
                supplierReadMessage.interrupt();
                Thread.sleep(100);
                System.out.println("\n\nThe supplier thread is going to close.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println("\nWelcome " + supplierName);
        System.out.println("The list of available products: " +
                Arrays.stream(Product.values()).limit(Product.values().length - 1).collect(Collectors.toList()));

        System.out.print("""

                Please type what you supply (Don't worry about letter capitalization).
                You can supply multiple products , e.g.,
                Oxygen Backpack Boots
                You supply:\s""");
        products = Arrays.stream(scanner.nextLine().split(" ")).
                map(String::toUpperCase).
                map(Product::getProduct).collect(Collectors.toCollection(ArrayList::new));

        try {
            supplierReadMessage = new SupplierReadMessage(this, this.supplierName);
            supplierSendMessage = new SupplierSendMessage(this, this.supplierName);
            supplierSendMessage.start();
            supplierReadMessage.start();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) throws Exception {
        Supplier newSupplier;
        if (argv.length == 0)
            newSupplier = new Supplier();
        else
            newSupplier = new Supplier(argv[0]);
        newSupplier.start();
    }

}
