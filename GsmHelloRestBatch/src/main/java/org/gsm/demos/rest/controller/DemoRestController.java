package org.gsm.demos.rest.controller;

import java.time.ZonedDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = DemoRestController.MAPPING)
@Api(value = "RestService Demo invoking a Spring Batch Job")
public class DemoRestController {

	public static final String MAPPING = "/demo/rest";

	@Autowired
	private Job importUserJob;

	@Autowired
	private JobLauncher jobLauncher;

	@ApiOperation(value = "Execute Job")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully Executien"), //
			@ApiResponse(code = 400, message = "Your request is wrong")
	})
	@GetMapping(value = "/run")
	public ResponseEntity<String> runJob(){
		try {
			ZonedDateTime timeNullChecker = ZonedDateTime.now();
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()//
					.addLong("dateTime", timeNullChecker.toInstant().toEpochMilli())//
					.addString("date", timeNullChecker.toInstant().toString())
					.addString("jobId", importUserJob.getName() + "_" + timeNullChecker.toInstant().toEpochMilli());


			JobExecution jobExecution = jobLauncher.run(importUserJob, //
					jobParametersBuilder.toJobParameters());
			return ResponseEntity.ok((String)jobExecution.getExitStatus().getExitCode());
		} catch (JobExecutionException e) {
			System.err.println("runJob :: " + e.getMessage()) ;
			e.printStackTrace();

			return ResponseEntity.ok((String)e.getMessage());
		}

	}
}
