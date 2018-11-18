package org.gsm.demos.rest.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.gsm.demos.rest"
})
@PropertySource(ignoreResourceNotFound = true, //
		value = {
				"classpath:application.properties"
		})
public class Application {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(Application.class, args);
	}
}
