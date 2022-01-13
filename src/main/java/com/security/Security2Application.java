package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Security2Application {

	
//	@Bean
//	public WebMvcConfigurer crosConfigure() {
//		return new WebMvcConfigurer() {
//			
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowCredentials(true);
//			}
//		};
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(Security2Application.class, args);
//		BCryptPasswordEncoder en = new BCryptPasswordEncoder();
//		
//		System.out.println(en.encode("11"));
	}

}
