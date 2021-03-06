package org.gsm.demos.aspect.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableBatchProcessing
@EnableAspectJAutoProxy
public class BatchConfig {

}
