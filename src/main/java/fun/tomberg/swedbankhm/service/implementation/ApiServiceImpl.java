package fun.tomberg.swedbankhm.service.implementation;

import fun.tomberg.swedbankhm.entity.Currency;
import fun.tomberg.swedbankhm.service.ApiService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

@Service
public class ApiServiceImpl implements ApiService {

    Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    @Override
    public LinkedList<Currency> getAllCurrencies() throws IOException {
        String url = "https://api.exchangerate.host/symbols";
        StringBuilder stringBuilder = getResponse(url);
        JSONObject myResponse = new JSONObject(stringBuilder.toString()).getJSONObject("symbols");
        Iterator<String> x = myResponse.keys();
        LinkedList<Currency> currencies = new LinkedList<>();

        JSONObject rates = getCurrenciesRatesBasedOn("EUR");

        while (x.hasNext()){
            JSONObject jsonObject = myResponse.getJSONObject(x.next());
            try {
                BigDecimal rate = rates.getBigDecimal((String) jsonObject.get("code"));
                currencies.add(new Currency((String) jsonObject.get("code"), (String) jsonObject.get("description"), rate));
            } catch (JSONException ignored) {}
        }
        Collections.sort(currencies);
        logRequest("GETTING ALL CURRENCIES", url, currencies.toString());
        return currencies;
    }

    @Override
    public JSONObject getCurrenciesRatesBasedOn(String currency) throws IOException {
        String url = "https://api.exchangerate.host/latest?base=" + currency;
        StringBuilder stringBuilder = getResponse(url);
        return new JSONObject(stringBuilder.toString()).getJSONObject("rates");
    }

    @Override
    public StringBuilder getResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        logRequest("GETTING ALL CURRENCY RATES VIA SPECIFIC CURRENCY", url, response.toString());
        return response;
    }

    @Override
    public double getSpecificExchangeRate(String from, String to, double amount) throws IOException {
        String url = "https://api.exchangerate.host/convert?from="+from+"&to="+to;
        StringBuilder stringBuilder = getResponse(url);
        double result = new JSONObject(stringBuilder.toString()).getDouble("result") * amount;
        logRequest("GETTING SPECIFIC EXCHANGE RATE [(" + from + ")" + " --> (" + to + ")]", url, amount + " (" + from +") --> (" + to + ") = " + result);
        return result;
    }

    @Override
    public void logRequest(String message, String urlRequest, String jsonResponse) {
        logger.info(message);
        logger.info("Request url: " + urlRequest);
        logger.info("Response: " + jsonResponse);
    }
}
