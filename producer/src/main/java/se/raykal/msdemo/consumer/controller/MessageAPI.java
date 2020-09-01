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
import se.raykal.msdemo.consumer.service.KafkaSender;

@RestController
@RequestMapping(path = "/api")
public class MessageAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAPI.class);

    @Autowired
    private KafkaSender kafkaSender;

    @RequestMapping(method = RequestMethod.PUT, value = "/message/{value}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postNewMessage(@PathVariable String value) {

        LOGGER.info("Post message value: {}", value);
        kafkaSender.sendMessageOnDefaultTopic(value);
        return ResponseEntity.ok().build();
    }


}
