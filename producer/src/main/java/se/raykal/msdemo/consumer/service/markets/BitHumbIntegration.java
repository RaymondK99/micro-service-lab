package se.raykal.msdemo.consumer.service.markets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.raykal.msdemo.consumer.service.KafkaSender;
import se.raykal.msdemo.model.OrderBookUpdate;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class BitHumbIntegration {

    private static final Logger log = LoggerFactory.getLogger(BitHumbIntegration.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private ExchangeRatesApiIntegration exchangeRatesApiIntegration;

    private String baseUrl = "https://api.bithumb.com/public/";
    private static final String exchangeName = "BitHumb";

    @Value("${markets.binance.currencylist}")
    private String currencyList;



    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    protected void produceNewPrice() {


        /*
        {
    "data": {
        "asks": [
            {
                "price": "62.23",
                "quantity": "399.6528"
            }
        ],
        "bids": [
            {
                "price": "61.31",
                "quantity": "401.9136"
            }
        ],
        "order_currency": "CHR",
        "payment_currency": "KRW",
        "timestamp": "1599488701659"
    },
    "status": "0000"
}
         */
        for(String currency : currencyList.split(",")) {

            ResponseEntity<String> chrResponse = restTemplate.exchange(buildUrl(currency), HttpMethod.GET, null, String.class);

            log.info("Got server:{}, body:{}", chrResponse.getStatusCodeValue(), chrResponse.getBody());
            JsonObject chrResponseBody = new JsonParser().parse(chrResponse.getBody()).getAsJsonObject();
            JsonObject chrData = chrResponseBody.getAsJsonObject("data");

            // Push to kafka
            kafkaSender.sendOrderBookUpdate(convert(exchangeName, currency, chrData));
        }
    }

    private String buildUrl(String currency) {
        String uriChr = "orderbook/" + currency + "_KRW?count=1";
        String fullUrl = baseUrl + uriChr;
        return fullUrl;
    }

    private OrderBookUpdate convert(String exchangeName, String ticker, JsonObject chrObject) {
        // Get exchange rate against USD
        double krwUsd = exchangeRatesApiIntegration.getExchangeRateAgainstUSD("KRW");


        BigDecimal bidPrice = chrObject.getAsJsonArray("bids").get(0).getAsJsonObject().get("price").getAsBigDecimal();
        BigDecimal bidQty = chrObject.getAsJsonArray("bids").get(0).getAsJsonObject().get("quantity").getAsBigDecimal();
        BigDecimal askPrice = chrObject.getAsJsonArray("asks").get(0).getAsJsonObject().get("price").getAsBigDecimal();
        BigDecimal askQty = chrObject.getAsJsonArray("asks").get(0).getAsJsonObject().get("quantity").getAsBigDecimal();


        OrderBookUpdate update = OrderBookUpdate.builder()
                .symbol(ticker)
                .exchangeName(exchangeName)
                .timestamp(chrObject.get("timestamp").getAsLong())
                .askQty(askQty)
                .askPrice(new BigDecimal(askPrice.doubleValue() /krwUsd).round(new MathContext(5)))
                .bidPrice(new BigDecimal(bidPrice.doubleValue() / krwUsd).round(new MathContext(5)))
                .bidQty(bidQty)
                .build();
        return update;
    }


}
