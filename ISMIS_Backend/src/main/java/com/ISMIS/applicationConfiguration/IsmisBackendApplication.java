package com.ISMIS.applicationConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.ISMIS")

public class IsmisBackendApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(IsmisBackendApplication.class, args);
	}

	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(IsmisBackendApplication.class);
	 }

	
	/*
	 * @Bean public WebMvcConfigurer Configurer() { return new WebMvcConfigurer() {
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/*").allowedOrigins("*"); } }; }
	 */
}
