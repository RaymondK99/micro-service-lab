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

@Service
public class KuCoinIntegration {

    private static final Logger log = LoggerFactory.getLogger(KuCoinIntegration.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaSender kafkaSender;

    private String baseUrl = "https://api.kucoin.com/api/v1/";
    private static final String exchangeName = "KuCoin";

    @Value("${markets.kucoin.currencylist}")
    private String currencyList;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    protected void produceNewPrice() {


        /*
        // Example response
        {"code":"200000","data":{"time":1599470428791,"sequence":"1594226383945","price":"0.054216","size":"1374.8721","bestBid":"0.054087","bestBidSize":"4684","bestAsk":"0.054216","bestAskSize":"11265.5804"}}
        */

        for(String currency : currencyList.split(",")) {
            ResponseEntity<String> response = restTemplate.exchange(constructUrl(currency), HttpMethod.GET, null, String.class);

            log.info("Got response form server:{}, body:{}", response.getStatusCodeValue(), response.getBody());
            JsonObject responseBody = new JsonParser().parse(response.getBody()).getAsJsonObject();
            JsonObject data = responseBody.getAsJsonObject("data");

            kafkaSender.sendOrderBookUpdate(convert(exchangeName, currency, data));
        }
    }


    private String constructUrl(String currency) {
        String uri = "market/orderbook/level1?symbol=" + currency + "-USDT";
        String fullUrl = baseUrl + uri;
        return fullUrl;
    }

    private OrderBookUpdate convert(String exchangeName, String ticker, JsonObject jsonObject) {
        OrderBookUpdate update = OrderBookUpdate.builder()
                .symbol(ticker)
                .exchangeName(exchangeName)
                .timestamp(jsonObject.get("time").getAsLong())
                .askQty(jsonObject.get("bestAskSize").getAsBigDecimal())
                .askPrice(jsonObject.get("bestAsk").getAsBigDecimal())
                .bidPrice(jsonObject.get("bestBid").getAsBigDecimal())
                .bidQty(jsonObject.get("bestBidSize").getAsBigDecimal())
                .build();
        return update;
    }
}
