package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	/*
	 * It is required to override this method because the angular application needs
	 * to be configured explicitly
	 * Note that following way is not suggested for 
	 * production level deployment
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").
		allowedMethods("*").maxAge(3600L).allowedHeaders("*")
		.exposedHeaders("Authorization").allowCredentials(true);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {		
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars");
	}

}
