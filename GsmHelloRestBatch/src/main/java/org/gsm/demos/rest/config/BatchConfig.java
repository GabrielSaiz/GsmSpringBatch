package org.gsm.demos.rest.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableBatchProcessing
@EnableAspectJAutoProxy
public class BatchConfig extends DefaultBatchConfigurer{

}
