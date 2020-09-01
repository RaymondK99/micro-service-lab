package se.raykal.msdemo.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import se.raykal.msdemo.model.JsonDemoMessage;

import java.util.Optional;

@Component
public class KafkaConsumer {

    private static Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    @Value("${kafka.default-topic}")
    private String defaultTopic;

    private Optional<Integer> lastMessageId = Optional.empty();


    @KafkaListener(
            topics = "Topic1",
            groupId = "group2")
    void commonListenerForMultipleTopics(JsonDemoMessage message) {
        LOG.info("MultipleTopicListener - {}", message);
        this.lastMessageId = Optional.of(message.getMessageId());
    }

    public Optional<Integer> getLastMessageId() {
        return lastMessageId;
    }

}
