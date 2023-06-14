package com.votehaeduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VoteHaeDuoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteHaeDuoApplication.class, args);
    }

}
