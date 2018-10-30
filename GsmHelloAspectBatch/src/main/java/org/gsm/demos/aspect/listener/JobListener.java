package org.gsm.demos.aspect.listener;

import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gsm.demos.aspect.entity.Person;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JobListener extends JobExecutionListenerSupport {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(JobListener.class.getName());

	private final JdbcTemplate jdbcTemplate;

	/**
	 * Instantiates a new job listener.
	 *
	 * @param jdbcTemplate the jdbc template
	 */
	@Autowired
	public JobListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.batch.core.listener.JobExecutionListenerSupport#
	 * beforeJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {

		StringBuilder protocol = new StringBuilder();
		protocol.append(getJobExecutionData(jobExecution));
		protocol.append(getJobParametersData(jobExecution.getJobParameters()));

		logger.info(protocol.toString());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.batch.core.listener.JobExecutionListenerSupport#
	 * afterJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {

		StringBuilder protocol = new StringBuilder();
		protocol.append(getJobExecutionData(jobExecution));

		logger.info(protocol.toString());

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("!!! JOB FINISHED! Time to verify the results");

			jdbcTemplate.query("SELECT first_name, last_name FROM people",
					(rs, row) -> new Person(
						rs.getString(1),
						rs.getString(2))
				).forEach(person -> logger.info("Found <" + person + "> in the database."));
		}

	}

	/**
	 * Gets the job execution data.
	 *
	 * @param jobExecution
	 *            the job execution
	 * @return the job execution data
	 */
	protected String getJobExecutionData(JobExecution jobExecution) {

		StringBuilder protocol = new StringBuilder();
		protocol.append("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		protocol.append("Protocol for ").append(jobExecution.getJobInstance().getJobName()).append(" \n");
		protocol.append("  Started     : ").append(jobExecution.getStartTime()).append("\n");
		protocol.append("  Finished    : ").append(jobExecution.getEndTime()).append("\n");
		protocol.append("  Exit-Code   : ").append(jobExecution.getExitStatus().getExitCode()).append("\n");
		protocol.append("  Exit-Descr. : ").append(jobExecution.getExitStatus().getExitDescription()).append("\n");
		protocol.append("  Status      : ").append(jobExecution.getStatus()).append("\n");
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		return protocol.toString();
	}

	/**
	 * Gets the job parameters data.
	 *
	 * @param jobExecution
	 *            the job execution
	 * @return the job parameters data
	 */
	protected String getJobParametersData(JobParameters jp) {

		StringBuilder protocol = new StringBuilder();
		protocol.append("Job-Parameter: \n");
		for (Entry<String, JobParameter> entry : jp.getParameters().entrySet()) {
			protocol.append("  ").append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
		}
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		return protocol.toString();
	}

}
