package se.raykal.msdemo.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.raykal.msdemo.model.JsonDemoMessage;

import java.util.Random;

@Service
public class KafkaSender {

    private KafkaTemplate<String, JsonDemoMessage> kafkaStringTemplate;


    @Value("${kafka.default-topic}")
    private String defaultTopic;

    @Autowired
    public  KafkaSender(KafkaTemplate<String, JsonDemoMessage> kafkaTemplate) {
        this.kafkaStringTemplate = kafkaTemplate;
    }


    public void sendMessageOnDefaultTopic(String message) {
        //MsDemoMessage msDemoMessage = MsDemoMessage.newBuilder().setMessage(message).setCustomerId("some_id").setNumber(new Random().nextInt()).build();
        JsonDemoMessage jsonDemoMessage = JsonDemoMessage.builder()
                .message(message)
                .messageId(new Random().nextInt())
                .customerId("cust_id_" + new Random().nextInt(100))
                .build();
        kafkaStringTemplate.send(defaultTopic, jsonDemoMessage);
    }
}
