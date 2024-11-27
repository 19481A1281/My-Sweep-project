package com.banking.Sweep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SweepApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweepApplication.class, args);
	}

}
