package se.raykal.msdemo.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateFactory {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
