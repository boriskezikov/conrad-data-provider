package com.conrad.dataprovider.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfiguration {

    @Value("${generator.kafka.central_server_topic}")
    private String CENTRAL_SERVER_TOPIC;

    @Bean
    public NewTopic centralServerTopic() {
        return new NewTopic(CENTRAL_SERVER_TOPIC, 5, (short) 1);
    }
}
