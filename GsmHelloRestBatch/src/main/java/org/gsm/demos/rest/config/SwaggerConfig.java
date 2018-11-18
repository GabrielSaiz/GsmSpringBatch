package org.gsm.demos.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		// @formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.gsm.demos.rest.controller"))
				.paths(PathSelectors.any())
				.build();
		// @formatter:on
	}

	private ApiInfo apiInfo() {

		// @formatter:off
		Contact contact = new Contact("Gabriel Saiz", "", "gsaiz.ricoh@gmail.com");
		return new ApiInfoBuilder()
				.title("Demo of Spring Batch with REST API")
				.description("Spring Boot REST API for Demo Spring Batch processes")
				.version("0.0.1")
				.license("License 1.0")
				.licenseUrl("")
				.contact(contact)
				.build();
		// @formatter:on
	}

}
