package crypto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CryptoCurrency {

    private static String getPrice(String json) {
        return json.substring(json.lastIndexOf("priceUsd\":\"") + 11, json.lastIndexOf("\",\"change"));
    }

    public static String getCurrencyPrice(String currency) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.coincap.io/v2/assets?ids=" + currency))
                .GET()
                .build();

        String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return getPrice(response);
    }

    public static Map<Currency, String> getCurrencyPrices(List<Currency> currencies) throws IOException, InterruptedException {
        Map<Currency, String> prices = new HashMap<>();
        for (int i = 0; i < currencies.size() - 1; i++) {
            String currencyString = currencies.get(i).toString().toLowerCase();
            prices.put(currencies.get(i), getCurrencyPrice(currencyString));
        }

        return prices;
    }
}
