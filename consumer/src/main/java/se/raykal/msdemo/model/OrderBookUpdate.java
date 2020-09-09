package se.raykal.msdemo.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderBookUpdate {
    private String exchangeName;
    private Long timestamp;
    private String symbol;
    private BigDecimal askPrice;
    private BigDecimal bidPrice;
    private BigDecimal askQty;
    private BigDecimal bidQty;

}
