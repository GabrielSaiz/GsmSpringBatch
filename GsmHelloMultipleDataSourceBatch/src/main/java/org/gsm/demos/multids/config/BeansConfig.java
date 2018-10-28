package org.gsm.demos.multids.config;

import javax.sql.DataSource;

import org.gsm.demos.multids.entity.Person;
import org.gsm.demos.multids.listener.JobListener;
import org.gsm.demos.multids.listener.StepListener;
import org.gsm.demos.multids.processor.PersonItemProcessor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Fichero de configuracion donde se definen los beans.
 */
@Configuration
public class BeansConfig {

	/**
	 * Job logging listener.
	 *
	 * @return the job execution listener
	 */
	@Bean
	@Autowired
	public JobExecutionListener jobListener(JdbcTemplate jdbcTemplate) {

		return new JobListener(jdbcTemplate);
	}

	/**
	 * Step logging listener.
	 *
	 * @return the step execution listener
	 */
	@Bean
	public StepExecutionListener stepListener() {

		return new StepListener();

	}

	/**
	 * Reader.
	 *
	 * @return the flat file item reader
	 */
	@Bean
	public FlatFileItemReader<Person> reader() {
		FlatFileItemReaderBuilder<Person> builder = new FlatFileItemReaderBuilder<>();
		builder.name("personItemReader");
		builder.resource(new ClassPathResource("sample-data.csv"));
		builder.delimited().names(new String[] {
				"firstName", "lastName"
		});

		BeanWrapperFieldSetMapper<Person> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(Person.class);

		builder.fieldSetMapper(mapper);

		return builder.build();

	}

	/**
	 * Processor.
	 *
	 * @return the person item processor
	 */
	@Bean
	public PersonItemProcessor processor() {

		return new PersonItemProcessor();
	}


	@Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		JdbcBatchItemWriterBuilder<Person> builder = new JdbcBatchItemWriterBuilder<>();
		builder.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		builder.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
		builder.dataSource(dataSource);

		return builder.build();
    }

}
