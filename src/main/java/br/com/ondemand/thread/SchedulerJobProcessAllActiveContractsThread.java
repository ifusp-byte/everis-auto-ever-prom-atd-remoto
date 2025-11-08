package br.com.ondemand.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bsh.util.Util;

@Component
public class SchedulerJobProcessAllActiveContractsThread extends ThreadsDefault {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerJobProcessAllActiveContractsThread.class);
	private static final String SCHEDULER_JOB_PROCESS_THREAD_PROCESS = "schedulerJobProcessAllActiveContractsThread.error.process";

	private final AwsSecretsDto awsSecretsDto;
	private final MessageSource messageSource;
	private final ServiceAwsSqs serviceAwsSqs;
	private final UsuarioService userService;
	private final TokenInterceptorService tokenInterceptorService;
	private final ContractBySchedulerJobService contractBySchedulerJobService;

	public SchedulerJobProcessAllActiveContractsThread(AwsSecretsDto awsSecretsDto, MessageSource messageSource,
			ServiceAwsSqs serviceAwsSqs, UsuarioService userService, TokenInterceptorService tokenInterceptorService,
			ContractBySchedulerJobService contractBySchedulerJobService) {
		this.awsSecretsDto = awsSecretsDto;
		this.messageSource = messageSource;
		this.serviceAwsSqs = serviceAwsSqs;
		this.userService = userService;
		this.tokenInterceptorService = tokenInterceptorService;
		this.contractBySchedulerJobService = contractBySchedulerJobService;
	}

	@Scheduled(fixedDelay = MINUTE)
	public void run() {
		if (!enableThread(awsSecretsDto.getProcessSchedulerJob())) {
			return;
		}

		List<Message> queueMessages = serviceAwsSqs
				.returnMessages(awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSchedulerJob());

		SchedulerJobDto schedulerJobDto = null;
		for (Message message : queueMessages) {
			try {
				schedulerJobDto = convertJsonToSchedulerJobDto(message);
				setAuthenticationByUserId(schedulerJobDto.getUserId());

				contractBySchedulerJobService.automaticGenerationContractBySchedulerJob(schedulerJobDto);

				deleteMessage(message);
			} catch (Exception e) {
				LOG.error(Util.formatExceptionMessage(SCHEDULER_JOB_PROCESS_THREAD_PROCESS, messageSource,
						schedulerJobDto.getUserId().toString()), e.getMessage(), e);
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
				awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSchedulerJob(), message.getReceiptHandle());
	}

	private void setAuthenticationByUserId(Long userId) {
		Usuario user = userService.findById(userId);
		tokenInterceptorService.getUserOauth(user.getLogin(), user.getSenha(), Util.NOT_ACCESS_FROM_API_KEY);
	}
}