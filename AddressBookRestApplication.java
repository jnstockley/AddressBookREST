package com.github.jnstockley.addressbookrest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication

public class AddressBookRestApplication {

	Set<String> produces = new HashSet<>();

	public static void main(String[] args) {
		SpringApplication.run(AddressBookRestApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		produces.add("application/json");
		return new Docket(DocumentationType.SWAGGER_2).ignoredParameterTypes(ResponseEntity.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.github.jnstockley.addressbookrest"))
				.build()
				.produces(produces)
				.useDefaultResponseMessages(false)
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Address Book REST",
				"Address Book RESTful Service with full CRUD support",
				"3.1",
				"",
				new springfox.documentation.service.Contact("Jack Stockley", "https://jnstockley.github.io/", ""),
				"Apache License 2.0",
				"https://github.com/jnstockley/AddressBookREST/blob/master/LICENSE",
				Collections.emptyList());
	}
}