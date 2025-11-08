package br.com.ondemand.thread;

import java.util.List;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bsh.util.Util;

@Component
public class SchedulerJobProcessInvoiceNfseThread extends ThreadsDefault {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerJobProcessInvoiceNfseThread.class);
	private static final String SCHEDULER_JOB_INVOICE_NFSE_THREAD_ERROR_PROCESS = "schedulerJobProcessInvoiceNfseThread.error.process";

	private final AwsSecretsDto awsSecretsDto;
	private final MessageSource messageSource;
	private final ServiceAwsSqs serviceAwsSqs;
	private final UsuarioService userService;
	private final TokenInterceptorService tokenInterceptorService;
	private final InvoiceNfseByContractService invoiceNfseByContractService;

	public SchedulerJobProcessInvoiceNfseThread(AwsSecretsDto awsSecretsDto, MessageSource messageSource,
			ServiceAwsSqs serviceAwsSqs, UsuarioService userService, TokenInterceptorService tokenInterceptorService,
			InvoiceNfseByContractService invoiceNfseByContractService) {
		this.awsSecretsDto = awsSecretsDto;
		this.messageSource = messageSource;
		this.serviceAwsSqs = serviceAwsSqs;
		this.userService = userService;
		this.tokenInterceptorService = tokenInterceptorService;
		this.invoiceNfseByContractService = invoiceNfseByContractService;
	}

	@Scheduled(fixedDelay = MINUTE)
	public void run() {
		if (!enableThread(awsSecretsDto.getProcessSchedulerJobInvoiceNfse())) {
			return;
		}

		List<Message> queueMessages = serviceAwsSqs
				.returnMessages(awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSchedulerJobInvoiceNfse());

		SchedulerJobDto schedulerJobDto = null;
		for (Message message : queueMessages) {
			try {
				schedulerJobDto = convertJsonToSchedulerJobDto(message);
				setAuthenticationByUserId(schedulerJobDto.getUserId());

				invoiceNfseByContractService.automaticGenerationInvoiceNfseBySchedulerJob(schedulerJobDto);

				deleteMessage(message);
			} catch (Exception e) {
				LOG.error(Util.formatExceptionMessage(SCHEDULER_JOB_INVOICE_NFSE_THREAD_ERROR_PROCESS, messageSource,
						schedulerJobDto.getContractId().toString()), e.getMessage(), e);
			}
		}
	}

	private SchedulerJobDto convertJsonToSchedulerJobDto(Message message) {
		SchedulerJobDto schedulerJobDto = new SchedulerJobDto();
		JSONObject jsonMessage = new JSONObject(message.getBody());
		JSONHelper.mergeJson(schedulerJobDto, jsonMessage, map());

		return schedulerJobDto;
	}

	private void deleteMessage(Message message) {
		serviceAwsSqs.getClienteSQS().deleteMessage(
				awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSchedulerJobInvoiceNfse(),
				message.getReceiptHandle());
	}

	private void setAuthenticationByUserId(Long userId) {
		Usuario user = userService.findById(userId);
		tokenInterceptorService.getUserOauth(user.getLogin(), user.getSenha(), Util.NOT_ACCESS_FROM_API_KEY);
	}
}