package se.raykal.msdemo.consumer.service.markets;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.raykal.msdemo.consumer.service.KafkaSender;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRatesApiIntegration {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRatesApiIntegration.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private BinanceIntegration binanceIntegration;

    private String baseUrl = "https://api.exchangeratesapi.io/";
    private String exchangeName = "exchangeratesapi";

    Map<String,Double> latestRates = new HashMap<>();

    @Scheduled(fixedRate = 5000)
    private void produceNewPrice() {
        String uri = "latest?base=USD";

        String fullUrl = baseUrl + uri;

        /*
{
    "base": "USD",
    "date": "2020-09-07",
    "rates": {
        "AUD": 1.3728010825,
        "BGN": 1.6540933694,
        "BRL": 5.299729364,
        "CAD": 1.3095399188,
        "CHF": 0.9141576455,
        "CNY": 6.8326285521,
        "CZK": 22.3849797023,
        "DKK": 6.2932171854,
        "EUR": 0.8457374831,
        "GBP": 0.7597682679,
        "HKD": 7.7504228687,
        "HRK": 6.3734776725,
        "HUF": 304.397834912,
        "IDR": 14791.948579161,
        "ILS": 3.376691475,
        "INR": 73.4510317997,
        "ISK": 139.4621109608,
        "JPY": 106.2161705007,
        "KRW": 1188.3372801083,
        "MXN": 21.5770466847,
        "MYR": 4.1574763194,
        "NOK": 8.9185554804,
        "NZD": 1.4920500677,
        "PHP": 48.6696549391,
        "PLN": 3.7638700947,
        "RON": 4.1064783491,
        "RUB": 75.9667625169,
        "SEK": 8.7694519621,
        "SGD": 1.3667117727,
        "THB": 31.3700947226,
        "TRY": 7.4566982409,
        "USD": 1.0,
        "ZAR": 16.7163396482
    }
}

         */

        ResponseEntity<String> chrResponse = restTemplate.exchange(fullUrl, HttpMethod.GET, null, String.class);

        log.info("Got server:{}, body:{}",chrResponse.getStatusCodeValue(),chrResponse.getBody());
        JsonObject responseBody = new JsonParser().parse(chrResponse.getBody()).getAsJsonObject();
        JsonObject rates = responseBody.getAsJsonObject("rates");

        String currencies[] = {"SEK","KRW","EUR"};
        for (String curr : currencies) {
            this.latestRates.put(curr,rates.get(curr).getAsDouble());
        }
    }


    public Double getExchangeRateAgainstUSD(String currency) {
        return this.latestRates.get(currency);
    }


}
