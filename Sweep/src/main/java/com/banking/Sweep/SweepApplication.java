package com.banking.Sweep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SweepApplication {

	private static final Logger logger = LoggerFactory.getLogger(SweepApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SweepApplication.class, args);
	}

}
