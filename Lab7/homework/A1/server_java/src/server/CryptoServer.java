package server;

import crypto.CryptoCurrency;
import crypto.Currency;
import io.grpc.ServerBuilder;
import service.CryptoService;
import subscription.ClientSubscription;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CryptoServer {
    private io.grpc.Server server;
    private final ClientSubscription clientSubscription = new ClientSubscription();

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server != null) {
                server.shutdown();
            }
            System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] The server is closing...!");
        }));
    }

    private void handleRequests() throws IOException, InterruptedException {
        while (true) {
            long start = System.currentTimeMillis();
            Map<Currency, String> currencyPrices = CryptoCurrency.getCurrencyPrices(Arrays.asList(Currency.values()));
            long end = System.currentTimeMillis();

            clientSubscription.sendCryptoPricesNotifications(currencyPrices);

            TimeUnit.MILLISECONDS.sleep(5000 - (end - start));
        }
    }

    public void run() throws IOException, InterruptedException {
        server = ServerBuilder
                .forPort(55555)
                .addService(new CryptoService(clientSubscription))
                .build()
                .start();
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] The server has started!");

        addShutdownHook();
        handleRequests();
        if (server != null) {
            server.awaitTermination();
        }
    }
}
