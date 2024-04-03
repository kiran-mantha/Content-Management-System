package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}

	Info info() {
		return new Info().title("Content-Management-System")
						 .description("Restful API with basic crud operation")
						 .version("v1")
						 .contact(contact());
	}

	Contact contact() {
		return new Contact().name("Kiran")
							.url("kiran.in")
							.email("kiranmantha7143@gmail.com");
	}
}
