package com.newsnow.media.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.newsnow.media.*"})
@EnableReactiveMongoRepositories(basePackages = "com.newsnow.media.outside.driven.adapter.persistence.repositories")
public class MediaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaSpringApplication.class, args);
    }

}