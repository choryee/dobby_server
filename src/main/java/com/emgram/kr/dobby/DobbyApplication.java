package com.emgram.kr.dobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DobbyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DobbyApplication.class, args);
	}

}
