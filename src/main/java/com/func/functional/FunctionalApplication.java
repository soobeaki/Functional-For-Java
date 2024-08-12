package com.func.functional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yml")
public class FunctionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunctionalApplication.class, args);
	}

}
