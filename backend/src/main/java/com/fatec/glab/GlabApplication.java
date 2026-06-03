package com.fatec.glab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class GlabApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlabApplication.class, args);
	}

}
