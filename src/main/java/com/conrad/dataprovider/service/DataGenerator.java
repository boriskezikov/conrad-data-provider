package com.conrad.dataprovider.service;

import com.conrad.dataprovider.dto.CameraData;
import com.conrad.dataprovider.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataGenerator {


    private final KafkaProducer kafkaProducer;
    private final RandomizerService randomizer;

    @Value("${generator.incidents.count}")
    private Integer incidentsCount;

    @Scheduled(cron = "${generator.incidents.cron}")
    public void generateIncidents() {
        Stream.generate(this::getRandomIncident).limit(incidentsCount).parallel()
                .forEach(kafkaProducer::publishIncident);
    }

    private CameraData getRandomIncident() {
        return CameraData.builder()
                .incidentTimestamp(randomizer.getRandomTime())
                .actualSpeed(randomizer.getRandomSpeed())
                .cameraUuid(UUID.randomUUID())
                .carNumber(RandomStringUtils.random(4, true, true))
                .build();
    }

}
