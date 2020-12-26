package com.donation.csv.exporter.csvhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CsvHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvHandlerApplication.class,args);
	}

}
