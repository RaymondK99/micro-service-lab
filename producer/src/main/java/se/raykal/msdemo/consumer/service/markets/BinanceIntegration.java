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


@Service
public class BinanceIntegration {

    private static final Logger log = LoggerFactory.getLogger(BinanceIntegration.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaSender kafkaSender;

    private String baseUrl = "https://api.binance.com/api/v3/";
    private static final String exchangeName = "Binance";

    @Value("${markets.binance.currencylist}")
    private String currencyList;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    protected void produceNewPrice() {
        /*
        {"symbol":"CHRUSDT","bidPrice":"0.05280000","bidQty":"86693.40000000","askPrice":"0.05290000","askQty":"7361.60000000"}
         */
        for(String currency : currencyList.split(",")) {

            ResponseEntity<String> response = restTemplate.exchange(buildUrl(currency), HttpMethod.GET, null, String.class);
            log.info("Got response form server:{}, body:{}", response.getStatusCodeValue(), response.getBody());
            JsonObject responseBody = new JsonParser().parse(response.getBody()).getAsJsonObject();

            kafkaSender.sendOrderBookUpdate(convert(exchangeName, currency, responseBody));
        }
    }

    private String buildUrl(String currency) {
        String uri = "ticker/bookTicker?symbol=" + currency + "USDT";
        String fullUrl = baseUrl + uri;
        return fullUrl;
    }

    private OrderBookUpdate convert(String exchangeName, String ticker, JsonObject jsonObject) {
        OrderBookUpdate update = OrderBookUpdate.builder()
                .symbol(ticker)
                .exchangeName(exchangeName)
                .timestamp(System.currentTimeMillis())
                .askQty(jsonObject.get("askQty").getAsBigDecimal())
                .askPrice(jsonObject.get("askPrice").getAsBigDecimal())
                .bidPrice(jsonObject.get("bidPrice").getAsBigDecimal())
                .bidQty(jsonObject.get("bidQty").getAsBigDecimal())
                .build();
        return update;
    }
}
