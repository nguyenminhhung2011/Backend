package com.fitlife.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.fitlife.app","com.trainer"})
public class FitnessAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args);
	}
}
