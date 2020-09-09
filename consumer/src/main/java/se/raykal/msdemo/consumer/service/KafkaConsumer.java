package se.raykal.msdemo.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import se.raykal.msdemo.consumer.controller.rest.TickerFeedObject;
import se.raykal.msdemo.model.OrderBookUpdate;

import java.util.Optional;

@Component
public class KafkaConsumer {

    private static Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    @Value("${kafka.default-topic}")
    private String defaultTopic;

    @Autowired
    TickerFeedService tickerFeedService;


    @KafkaListener(
            topics = "OrderBookFeed",
            groupId = "group2")
    void commonListenerForMultipleTopics(OrderBookUpdate message) {
        LOG.info("{}", message);
        tickerFeedService.updateTickers(message);
    }


}
