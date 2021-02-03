package com.conrad.dataprovider.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RandomizerService {

    @Value("${generator.incidents.random.speed.max}")
    private Integer maxSpeed;

    @Value("${generator.incidents.random.speed.min}")
    private Integer minSpeed;


    public Timestamp getRandomTime() {
        long offset = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2021-02-02 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long) (Math.random() * diff));
    }

    public int getRandomSpeed() {
        return (int) ((Math.random() * (maxSpeed - minSpeed)) + minSpeed);
    }
}
