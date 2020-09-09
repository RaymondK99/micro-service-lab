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
import se.raykal.msdemo.consumer.controller.rest.TickerFeedObject;
import se.raykal.msdemo.consumer.service.KafkaConsumer;
import se.raykal.msdemo.consumer.service.TickerFeedService;
import se.raykal.msdemo.model.OrderBookUpdate;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class MessageAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAPI.class);

    @Autowired
    KafkaConsumer kafkaConsumer;

    @Autowired
    TickerFeedService tickerFeedService;

    @RequestMapping(method = RequestMethod.GET, value = "/tickerFeed/{exchangeName}/{currency}")
    public ResponseEntity<TickerFeedObject> getTickerFeed(@PathVariable String exchangeName,@PathVariable String currency) {

        LOGGER.info("Request ticker:{} from exchange:{}",currency,exchangeName);
        var tickerObj = tickerFeedService.getOrderBookUpdate(exchangeName, currency);
        if (tickerObj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convert(tickerObj.get()));
    }


    private TickerFeedObject convert(OrderBookUpdate update) {
        TickerFeedObject object = new TickerFeedObject();
        object.setAskPrice(update.getAskPrice().toString());
        object.setAskQty(update.getAskQty().toString());
        object.setBidPrice(update.getBidPrice().toString());
        object.setBidQty(update.getBidQty().toString());
        object.setExchangeName(update.getExchangeName());
        object.setSymbol(update.getSymbol());
        object.setTimestamp(update.getTimestamp());
        return object;
    }


}
