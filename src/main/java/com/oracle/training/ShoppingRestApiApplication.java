package com.oracle.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.oracle.training.repository")
public class ShoppingRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingRestApiApplication.class, args);
	}

}
