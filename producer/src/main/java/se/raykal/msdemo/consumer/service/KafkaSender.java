package se.raykal.msdemo.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.raykal.msdemo.model.OrderBookUpdate;

import java.util.Random;

@Service
public class KafkaSender {

    private KafkaTemplate<String, OrderBookUpdate> kafkaStringTemplate;


    @Value("${kafka.default-topic}")
    private String defaultTopic;

    @Autowired
    public  KafkaSender(KafkaTemplate<String, OrderBookUpdate> kafkaTemplate) {
        this.kafkaStringTemplate = kafkaTemplate;
    }


    public void sendOrderBookUpdate(OrderBookUpdate orderBookUpdate) {
        kafkaStringTemplate.send(defaultTopic, orderBookUpdate);
    }
}
