package br.com.hd.configs.openapi.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addSecurityItem(
					new SecurityRequirement().addList("Bearer Authentication")
				)
				.components(
					new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())
				)
				.info(
					new Info()
						.title("RESTful API For Help Desk Application")
						.description("Java 21 and Spring Boot 3")
						.version("1.0")
						.contact(
							new Contact()
								.name("Henrique Augusto")
								.email("henriqueaugustolobo.dev@gmail.com")
							)
						.license(
							new License()
								.name("Apache 2.0")
								.url("https://apache.org/licenses/LICENSE-2.0")
						)
				);
	}
	
	private SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer")
	        .in(In.HEADER);
	}
	
}
