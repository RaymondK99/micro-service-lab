package se.raykal.msdemo.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import se.raykal.msdemo.model.JsonDemoMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        //Schema registry location.
        //props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
         //       "http://localhost:8081");
        return props;
    }

    @Bean
    public ConsumerFactory<String, JsonDemoMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(JsonDemoMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JsonDemoMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JsonDemoMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }



}