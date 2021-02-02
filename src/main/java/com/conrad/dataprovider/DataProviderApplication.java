package com.conrad.dataprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DataProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProviderApplication.class, args);
    }

}
