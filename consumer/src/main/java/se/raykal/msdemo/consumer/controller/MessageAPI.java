package se.raykal.msdemo.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.raykal.msdemo.consumer.service.KafkaConsumer;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class MessageAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAPI.class);

    @Autowired
    KafkaConsumer kafkaConsumer;

    @RequestMapping(method = RequestMethod.GET, value = "/message/lastid")
    public ResponseEntity<String> getMessageId() {
        Optional<Integer> msgId = kafkaConsumer.getLastMessageId();
        String msgIdStr = msgId.orElse(0).toString();
        LOGGER.info("Get last message id: {}", msgIdStr);
        return ResponseEntity.ok(msgIdStr);
    }


}
