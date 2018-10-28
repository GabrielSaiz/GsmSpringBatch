# GSM Multiple DataSource Batch #

I think that in a proyect you find a restrictions with database access.
It's probably that you have data in one database and spring batch persistence in other one...
Or at least two diferent database's schema

In this example I will try to show my workarround to solve this case.


## Environment ##

- SO: Unix (Ubuntu 16-04 gnome)
- IDE: Eclipse Neon
- Spring Batch
- Spring Boot: 2.0.1.RELEASE
- Spring
- Database: HSQLDB


## Conclusion ##

When I found whit this problem, my first attempt was to search in google, and many people had the same problem...

But no solution was the right one... I tried multiple altenatives and this is what worked for me.

In a configuration class I add this bean

```
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

```

Notice that two bean have qualified name *dataSource* and *batchDataSource*. Even though where you have to put special attention is un de *@Primary* annotation.

But this isn't the only thing to do...

The *@EnableBatchProcessing* makes **Spring** load the necessary configuration to start the **Spring Batch**, and for this the **Spring Batch** needs to load a **dataSource** ... but we want it to load the ***batchDataSource*** so we must indicate it in some way ...

I have put it this way...

First I do that my **BatchConfig** configuration class extends the ***DefaultBatchConfigurer***, and then I override the *setDatasource* method indicanting that the datasource is ***batchDataSource***


```
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

	@Override
	@Autowired
	public void setDataSource(@Qualifier("batchDataSource") DataSource dataSource) {

		super.setDataSource(dataSource);
	}
}

```

