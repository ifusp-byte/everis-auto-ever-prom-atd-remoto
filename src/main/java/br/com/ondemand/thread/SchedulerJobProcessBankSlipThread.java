package br.com.ondemand.thread;

import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.Message;

@Component
public class SchedulerJobProcessBankSlipThread extends ThreadsDefault {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerJobProcessBankSlipThread.class);
	private static final String SCHEDULER_JOB_BANKSLIP_THREAD_ERROR_PROCESS = "schedulerJobProcessBankSlipThread.error.process";

	private final AwsSecretsDto awsSecretsDto;
	private final MessageSource messageSource;
	private final ServiceAwsSqs serviceAwsSqs;
	private final UsuarioService userService;
	private final TokenInterceptorService tokenInterceptorService;
	private final AccountsReceivableByContractService accountsReceivableByContractService;

	public SchedulerJobProcessBankSlipThread(AwsSecretsDto awsSecretsDto, MessageSource messageSource,
			ServiceAwsSqs serviceAwsSqs, UsuarioService userService, TokenInterceptorService tokenInterceptorService,
			AccountsReceivableByContractService accountsReceivableByContractService) {
		this.awsSecretsDto = awsSecretsDto;
		this.messageSource = messageSource;
		this.serviceAwsSqs = serviceAwsSqs;
		this.userService = userService;
		this.tokenInterceptorService = tokenInterceptorService;
		this.accountsReceivableByContractService = accountsReceivableByContractService;
	}

	@Scheduled(fixedDelay = MINUTE)
	public void run() {
		if (!enableThread(awsSecretsDto.getProcessSchedulerJobBankSlip())) {
			return;
		}

		List<Message> queueMessages = serviceAwsSqs
				.returnMessages(awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSchedulerJobBankSlip());

		SchedulerJobDto schedulerJobDto = null;
		for (Message message : queueMessages) {
			try {
				schedulerJobDto = convertJsonToSchedulerJobDto(message);
				setAuthenticationByUserId(schedulerJobDto.getUserId());

				accountsReceivableByContractService.automaticGenerationBankSlipBySchedulerJob(schedulerJobDto);

				deleteMessage(message);
			} catch (Exception e) {
				LOG.error(Util.formatExceptionMessage(SCHEDULER_JOB_BANKSLIP_THREAD_ERROR_PROCESS, messageSource,
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
				awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSchedulerJobBankSlip(),
				message.getReceiptHandle());
	}

	private void setAuthenticationByUserId(Long userId) {
		Usuario user = userService.findById(userId);
		tokenInterceptorService.getUserOauth(user.getLogin(), user.getSenha(), Util.NOT_ACCESS_FROM_API_KEY);
	}
}