# GSM Hello Rest Batch #

In this example I'm going to implement and Rest service that makes the call to the batch process

This can be a typical example of execution of a batch process on demand, unplanned

## Environment ##

- SO: Unix (Ubuntu 16-04 gnome)
- IDE: Eclipse Neon
- Spring Batch
- Spring Boot: 2.0.5.RELEASE
- Spring
- Database: HSQLDB


## Conclusion ##

The unique problem that you could have is the auto-run of the jobs.
By definition, all jobs that don't have a Scheduler annotation for scheduled start, they are executed just at the start of the application.

To avoid that execution we need to define a [specific property][Spring documentation] in the applicacion properties file:

```
spring.batch.job.enabled=false

```

[Spring documentation]: https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/


Below you can see the principal properties for the Spring Batch's start:

```
# SPRING BATCH (BatchProperties)
spring.batch.initialize-schema=embedded # Database schema initialization mode.
spring.batch.job.enabled=true # Execute all Spring Batch jobs in the context on startup.
spring.batch.job.names= # Comma-separated list of job names to execute on startup (for instance, `job1,job2`). By default, all Jobs found in the context are executed.
spring.batch.schema=classpath:org/springframework/batch/core/schema-@@platform@@.sql # Path to the SQL file to use to initialize the database schema.
spring.batch.table-prefix= # Table prefix for all the batch meta-data tables.
```



