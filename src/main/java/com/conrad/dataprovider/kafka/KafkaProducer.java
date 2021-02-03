package com.conrad.dataprovider.kafka;

import com.conrad.dataprovider.dto.CameraData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.conrad.dataprovider.Utils.RQ_TM_HEADER;
import static com.conrad.dataprovider.Utils.RQ_UUID_HEADER;
import static com.conrad.dataprovider.Utils.SYSTEM_ID_HEADER;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.LocalTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${generator.kafka.incidents_topic}")
    private String CENTRAL_SERVER_TOPIC;

    public void publishIncident(CameraData cameraData) {
        var cameraUuid = cameraData.getCameraUuid();
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(CENTRAL_SERVER_TOPIC, cameraData);
        producerRecord.headers().add(RQ_TM_HEADER, now().toString().getBytes(UTF_8));
        producerRecord.headers().add(RQ_UUID_HEADER, UUID.randomUUID().toString().getBytes(UTF_8));
        producerRecord.headers().add(SYSTEM_ID_HEADER, cameraUuid.toString().getBytes(UTF_8));
        kafkaTemplate.send(producerRecord)
                .addCallback(
                        result -> log.info("Successfully delivered message to central topic: {} for camera {}.", result, cameraUuid),
                        error -> {
                            throw new KafkaException("Error while producing to STATUS topic", error);
                        });
    }

}


