package org.gsm.demos.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ControllerConfig.
 */
@Configuration
@ComponentScan(basePackages = {
		"org.gsm.demos.rest.controller"
})
public class ControllerConfig {

}
