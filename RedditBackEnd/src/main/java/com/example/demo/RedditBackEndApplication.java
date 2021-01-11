package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.demo.config.SwaggerConfig;

@SpringBootApplication
@EnableAsync
//The following annotation will invoke spring fox at the start of application
//It'll scan for all controllers and dto's to generate documentation for REST APIs
@Import(SwaggerConfig.class)
public class RedditBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditBackEndApplication.class, args);
	}

}
