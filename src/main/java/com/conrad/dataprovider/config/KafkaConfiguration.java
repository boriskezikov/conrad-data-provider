package com.conrad.dataprovider.config;

import com.conrad.dataprovider.kafka.KafkaJsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Configuration
public class KafkaConfiguration {

    @Value("${generator.kafka.bootstrap-servers}")
    private String bootstrap;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule()
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public DefaultKafkaProducerFactory<String, Object> producerFactory() {
        ObjectMapper mapper = objectMapper();
        Serializer<Object> serializer = new KafkaJsonSerializer<>(new TypeReference<>() {
        }, mapper);

        var props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props, Serdes.String().serializer(), serializer);
    }

}
