package com.ifusp.empresa.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileJobListener implements JobExecutionListener {

	private JobExecution status;

	@Autowired
	private JobOperator jobOperator;

	@Override
	public void beforeJob(JobExecution jobExecution) {

		final String jobName = jobExecution.getJobInstance().getJobName();
		final BatchStatus jobStatus = jobExecution.getStatus();

		// System.out.println("JobListener::beforeJob() -> jobExecution: " + jobName +
		// ", " + jobStatus.getBatchStatus());

		synchronized (jobExecution) {

			if (status != null && status.isRunning()) {

				System.out.println("JobListener::beforeJob(): isRunning() -> jobExecution: " + jobName + ", "
						+ jobStatus.isRunning());

				try {

					jobOperator.stop(jobExecution.getId());

				} catch (NoSuchJobExecutionException | JobExecutionNotRunningException e) {

					e.printStackTrace();

				}

			} else {

				status = jobExecution;

			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void afterJob(JobExecution jobExecution) {

		final String jobName = jobExecution.getJobInstance().getJobName();
		final BatchStatus jobStatus = jobExecution.getStatus();

		// System.out.println("JobListener::afterJob() -> jobExecution: " + jobName + ",
		// " + jobStatus.getBatchStatus());

		synchronized (jobExecution) {

			if (jobExecution == status) {

				status = null;

			}
		}
	}

}
