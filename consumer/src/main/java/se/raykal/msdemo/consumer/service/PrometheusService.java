package se.raykal.msdemo.consumer.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PrometheusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrometheusService.class);

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    TickerFeedService tickerFeedService;

    @PostConstruct
    private void init() {
        String currencyTagName = "currency";
        String exchangeTagName = "exchange";

        String exchanges[] = {"KuCoin", "BitHumb", "Binance"};
        String currencies[] = {"CHR","BTC","LTC"};

        for (String exchange : exchanges) {
            for (String curr : currencies) {
                Gauge.builder("bestAskPrice", tickerFeedService, tickerFeedService -> tickerFeedService.getOrderBookUpdate(exchange, curr).get().getAskPrice().doubleValue())
                        .tag(currencyTagName, curr)
                        .tag(exchangeTagName, exchange)
                        .description("Best ask price")
                        .register(meterRegistry);

                Gauge.builder("bestBidPrice", tickerFeedService, tickerFeedService -> tickerFeedService.getOrderBookUpdate(exchange, curr).get().getBidPrice().doubleValue())
                        .tag(currencyTagName, curr)
                        .tag(exchangeTagName, exchange)
                        .description("Best ask price")
                        .register(meterRegistry);
            }
        }

    }

}
