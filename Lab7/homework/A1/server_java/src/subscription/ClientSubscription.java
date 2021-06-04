package subscription;

import crypto.Currency;
import crypto.Request;
import crypto.Response;
import io.grpc.stub.StreamObserver;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ClientSubscription {
    public Map<Request, StreamObserver<Response>> subscribers = new HashMap<>();
    public Map<Currency, String> previousPrices;


    public void addClient(Request request, StreamObserver<Response> streamObserver) {
        this.subscribers.put(request, streamObserver);
    }

    public void removeSubscriber(Request request) {
        this.subscribers.remove(request);
    }

    private boolean isSubsribedToPrice(Map.Entry<Request, StreamObserver<Response>> subscriber,
                                       Map.Entry<Currency, String> price) {
        return subscriber.getKey().getCurrenciesList().stream()
                .map(currency -> currency.toString().toLowerCase())
                .anyMatch(currency -> currency.equals(price.getKey().toString().toLowerCase()));
    }

    private String getPriceChange(Currency currency, String price) {
        if (previousPrices == null) {
            return "No changes.";
        }
        String previousPrice = previousPrices.get(currency);
        float change = Float.parseFloat(price) - Float.parseFloat(previousPrice);
        if (change == 0) {
            return "No changes.";
        } else if (change > 0) {
            return "The price has increased by " + 100 * (change / Float.parseFloat(previousPrice)) + "%.";
        } else
            return "The price has decreased by " + -100 * (change / Float.parseFloat(previousPrice)) + "%.";
    }

    public void sendCryptoPricesNotifications(Map<Currency, String> currencyPrices) {
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] Sending notifications");
        for (Map.Entry<Request, StreamObserver<Response>> subscriber : subscribers.entrySet()) {
            for (Map.Entry<Currency, String> price : currencyPrices.entrySet()) {
                if (isSubsribedToPrice(subscriber, price)) {
                    subscriber.getValue().onNext(Response.newBuilder()
                            .setCurrency(price.getKey())
                            .setPrice(Float.parseFloat(price.getValue()))
                            .setPriceChange(getPriceChange(price.getKey(), price.getValue()))
                            .build());
                }
            }
        }
        previousPrices = currencyPrices;
    }

}
