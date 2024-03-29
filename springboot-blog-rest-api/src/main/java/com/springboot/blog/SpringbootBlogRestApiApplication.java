package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest APTs",
				description = "Spring Boot Blog App Rest APTs Documentation",
				version = "v1.5",
				contact = @Contact(
						name = "Gowtham",
						email = "gowthamramajeyam@gmail.com"
//						url = ""
				)
//				,
//				license = @License(
//						name = "",
//						url = ""
//				)
		),
		externalDocs = @ExternalDocumentation(
				description = "LinkedIn",
				url = "https://www.linkedin.com/in/gowtham-r-b61a911b0/"
		)
)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}



}
