package se.raykal.msdemo.consumer.controller.rest;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TickerFeedObject {
    private String exchangeName;
    private Long timestamp;
    private String symbol;
    private String askPrice;
    private String bidPrice;
    private String askQty;
    private String bidQty;
}
