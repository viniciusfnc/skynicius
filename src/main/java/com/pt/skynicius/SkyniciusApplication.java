package com.pt.skynicius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
@EnableR2dbcAuditing
public class SkyniciusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyniciusApplication.class, args);
	}

}
