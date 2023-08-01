package com.bartek.Medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MedicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalApplication.class, args);
	}
}
