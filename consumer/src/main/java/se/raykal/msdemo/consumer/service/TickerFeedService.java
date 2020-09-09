package se.raykal.msdemo.consumer.service;

import org.springframework.stereotype.Service;
import se.raykal.msdemo.model.OrderBookUpdate;

import java.util.*;

@Service
public class TickerFeedService {

    private Map<String, OrderBookUpdate> latestTickers = new HashMap<>();
    private Set<String> exchanges = new HashSet<>();
    private Set<String> currencies = new HashSet<>();

    public void updateTickers(OrderBookUpdate update) {

        String key = update.getExchangeName() + "-" + update.getSymbol();
        latestTickers.put(key, update);

        currencies.add(update.getSymbol());
        exchanges.add(update.getExchangeName());

    }

    public Optional<OrderBookUpdate> getOrderBookUpdate(String exchangeName, String currency) {
        String key = exchangeName + "-" + currency;

        if (latestTickers.containsKey(key)) {
            return Optional.of(latestTickers.get(key));
        } else {
            return Optional.empty();
        }
    }
}
