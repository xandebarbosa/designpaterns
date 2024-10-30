package com.alexandre.designpaterns;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Projeto Spring Boot gerado via Spring Initializr.
 * Os seguintes módulos foram selecionados:
 * - Spring Data JPA
 * - Spring Web
 * - H2 Database
 * - OpenFeign
 *
 * @author alexandrebarbosa
 */

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition
public class DesignpaternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesignpaternsApplication.class, args);
	}

}
