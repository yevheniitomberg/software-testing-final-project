package fun.tomberg.swedbankhm.service;

import fun.tomberg.swedbankhm.entity.Currency;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public interface ApiService {
    LinkedList<Currency> getAllCurrencies() throws IOException;
    JSONObject getCurrenciesRatesBasedOn(String currency) throws IOException;
    StringBuilder getResponse(String url) throws IOException;
    double getSpecificExchangeRate(String from, String to, double amount) throws IOException;
    void logRequest(String message, String urlRequest, String jsonResponse);
}
