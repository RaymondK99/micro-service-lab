package se.raykal.msdemo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JsonDemoMessage {
    private Integer messageId;
    private String message;
    private String customerId;

}
