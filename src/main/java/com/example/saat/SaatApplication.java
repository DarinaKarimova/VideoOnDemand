package com.example.saat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaatApplication {
	public static void main(String[] args) {
		SpringApplication.run(SaatApplication.class, args);
	}
}
