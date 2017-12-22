package com.demo.station.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * StationSwaggerConfig details the Swagger 2 configuration for the Station API.
 * @author Stew
 *
 */
@EnableSwagger2
@Configuration
public class StationSwaggerConfig {

	@Bean
	public Docket stationApi() {
		  return new Docket(DocumentationType.SWAGGER_2)
			        .select()
			        	.apis(RequestHandlerSelectors.basePackage("com.demo.station"))
			        	.paths(PathSelectors.any())
			        	.build()
			        .pathMapping("/");
	}
}
