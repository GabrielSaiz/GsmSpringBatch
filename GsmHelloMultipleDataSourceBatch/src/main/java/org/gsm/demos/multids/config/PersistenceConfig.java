package org.gsm.demos.multids.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * The Class PersistenceConfig.
 */
@Configuration
public class PersistenceConfig {

	@Bean(name = "dataSource")
	@Primary
	public DataSource dataSource() {

		return new EmbeddedDatabaseBuilder()
	            .setType(EmbeddedDatabaseType.HSQL)
	            .addScript("classpath:schema-all.sql")
	            .build();

	}

	@Bean(name = "batchDataSource")
	public DataSource batchDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		driverManagerDataSource.setUsername("sa");
		driverManagerDataSource.setPassword("sa");
		driverManagerDataSource.setDriverClassName("org.h2.Driver");

		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("/org/springframework/batch/core/schema-drop-h2.sql"));
		databasePopulator.addScript(new ClassPathResource("/org/springframework/batch/core/schema-h2.sql"));
		databasePopulator.setIgnoreFailedDrops(true);
		databasePopulator.setContinueOnError(false);
		DatabasePopulatorUtils.execute(databasePopulator, driverManagerDataSource);

		return driverManagerDataSource;
	}
}
