package com.example.seaSideEscape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.example.seaSideEscape.model")
@EnableJpaRepositories(basePackages = "com.example.seaSideEscape.repository")
@EnableTransactionManagement
public class SeaSideEscapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeaSideEscapeApplication.class, args);
	}
}
