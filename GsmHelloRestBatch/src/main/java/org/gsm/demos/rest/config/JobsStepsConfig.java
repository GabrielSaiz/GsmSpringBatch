package org.gsm.demos.rest.config;

import org.gsm.demos.rest.entity.Person;
import org.gsm.demos.rest.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class JobsStepsConfig.
 *
 * Fichero de configuracion de los jobs y los step de estos
 */
@Configuration
public class JobsStepsConfig {

	/**
	 * Step 1.
	 *
	 * @param stepBuilderFactory the step builder factory
	 * @param stepListener the step listener
	 * @param reader the reader
	 * @param processor the processor
	 * @param writer the writer
	 * @return the step
	 */
	@Bean
	@Autowired
	public Step step1(StepBuilderFactory stepBuilderFactory,//
			StepExecutionListener stepListener, //
			FlatFileItemReader<Person> reader, //
			PersonItemProcessor processor, //
			JdbcBatchItemWriter<Person> writer) {

		return stepBuilderFactory.get("step1") //
				.listener(stepListener)//
				.<Person, Person>chunk(10)//
				.reader(reader)//
				.processor(processor)//
				.writer(writer)//
				.build();
	}

	/**
	 * Import user job.
	 *
	 * @param jobBuilderFactory the job builder factory
	 * @param jobListener the job listener
	 * @param step1 the step 1
	 * @return the job
	 */
	@Bean
	@Autowired
	public Job importUserJob(JobBuilderFactory jobBuilderFactory, //
			JobExecutionListener jobListener, //
			Step step1) {

		return jobBuilderFactory.get("importUserJob")//
				.incrementer(new RunIdIncrementer())//
				.listener(jobListener)//
				.flow(step1).end()//
				.build();
	}

}
