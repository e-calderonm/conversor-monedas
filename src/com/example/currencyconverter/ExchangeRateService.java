package com.example.currencyconverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

public class ExchangeRateService {
    private static final String API_KEY = "f23e4942ac167cff7b72b525";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";
    private final HttpClient client;
    private final Gson gson;

    public ExchangeRateService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public double getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            URI uri = new URI(API_URL + fromCurrency + "/" + toCurrency);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            RateResponse rateResponse = gson.fromJson(response.body(), RateResponse.class);
            return rateResponse.getConversionRate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
