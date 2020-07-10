package com.github.jnstockley.addressbookrest;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.*;

@SpringBootApplication
@EnableSwagger2
public class AddressBookRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookRestApplication.class, args);
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.github.jnstockley"))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Address Book REST",
				"Address Book RESTful Service with full CRUD support",
				"3.0",
				"Free to use",
				new springfox.documentation.service.Contact("Jack Stockley", "https://jnstockley.github.io/", ""),
				"API License",
				"https://jnstockley.github.io/AddressBookREST",
				Collections.emptyList());
	}

}
