package org.gsm.demos.hello.listener;

import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * The listener interface for receiving customStep events.
 * The class that is interested in processing a customStep
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCustomStepListener<code> method. When
 * the customStep event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CustomStepEvent
 */
public class StepListener implements StepExecutionListener {

	/** Logger for this class. */
	private static final Logger logger = LogManager.getLogger(StepListener.class.getName());

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.
	 * springframework.batch.core.StepExecution)
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {

		StringBuilder protocol = new StringBuilder();

		protocol.append(getStepExecutionData(stepExecution));

		protocol.append(getJobContextParams(stepExecution.getJobExecution().getExecutionContext().entrySet()));

		logger.info(protocol.toString());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.
	 * springframework.batch.core.StepExecution)
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		StringBuilder protocol = new StringBuilder();

		protocol.append(getStepExecutionData(stepExecution));

		protocol.append(getJobContextParams(stepExecution.getJobExecution().getExecutionContext().entrySet()));

		logger.info(protocol.toString());

		return stepExecution.getExitStatus();
	}

	/**
	 * Gets the step execution data.
	 *
	 * @param stepExecution
	 *            the step execution
	 * @return the step execution data
	 */
	private String getStepExecutionData(StepExecution stepExecution) {

		StringBuilder protocol = new StringBuilder();
		protocol.append("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");
		protocol.append("Step ").append(stepExecution.getStepName()).append(" \n");
		protocol.append("WriteCount: ").append(stepExecution.getWriteCount()).append("\n");
		protocol.append("Commits: ").append(stepExecution.getCommitCount()).append("\n");
		protocol.append("SkipCount: ").append(stepExecution.getSkipCount()).append("\n");
		protocol.append("Rollbacks: ").append(stepExecution.getRollbackCount()).append("\n");
		protocol.append("Filter: ").append(stepExecution.getFilterCount()).append("\n");
		protocol.append("Status: ").append(stepExecution.getExitStatus().toString()).append("\n");
		protocol.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		return protocol.toString();
	}

	/**
	 * Gets the job context params.
	 *
	 * @param params the params
	 * @return the job context params
	 */
	private String getJobContextParams(Set<Entry<String, Object>> params) {

		StringBuilder protocol = new StringBuilder();
		protocol.append("Contenxt Parameters: \n");
		for (Entry<String, Object> entry : params) {
			protocol.append("  ").append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
		}

		protocol.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n");

		return protocol.toString();
	}

}
