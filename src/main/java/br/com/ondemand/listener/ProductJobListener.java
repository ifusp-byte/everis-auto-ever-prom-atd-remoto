package br.com.ondemand.listener;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import main.java.br.com.ondemand.constants.ProductConstants;

@Component
public class ProductJobListener implements JobExecutionListener {
	private static final Logger LOG = LoggerFactory.getLogger(ProductJobListener.class);
	private JobExecution status;
	private JobOperator jobOperator;
	private final MessageSource messageSource;

	public ProductJobListener(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		JobExecution jobExecutionParam = jobExecution;
		synchronized (jobExecutionParam) {
			if (status != null && status.isRunning()) {
				try {
					jobOperator.stop(jobExecution.getId());
				} catch (NoSuchJobExecutionException | JobExecutionNotRunningException e) {
					LOG.error(Util.getMessageSource(ProductConstants.PROCCESS_PRODUCT_IMPORT_FAILED, messageSource), e);
				}
			} else {
				status = jobExecutionParam;
			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void afterJob(JobExecution jobExecution) {
		JobExecution jobExecutionParam = jobExecution;
		synchronized (jobExecutionParam) {
			if (jobExecutionParam == status) {
				status = null;
			}
		}
	}

}