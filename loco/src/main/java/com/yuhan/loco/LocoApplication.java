package com.yuhan.loco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.yuhan.loco.*")

public class LocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocoApplication.class, args);
	}

}
