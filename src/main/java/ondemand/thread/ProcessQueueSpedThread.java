package ondemand.thread;

import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.amazonaws.services.sqs.model.Message;

@Component
public class ProcessQueueSpedThread extends ThreadsDefault {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessQueueSpedThread.class);

    private final AwsSecretsDto awsSecretsDto;
    private final ServiceAwsSqs serviceAwsSqs;
    private final UsuarioService userService;
    private final TokenInterceptorService tokenInterceptorService;
    private final SpedService spedService;

    public ProcessQueueSpedThread(AwsSecretsDto awsSecretsDto, ServiceAwsSqs serviceAwsSqs, UsuarioService userService, TokenInterceptorService tokenInterceptorService, SpedService spedService) {
        this.awsSecretsDto = awsSecretsDto;
        this.serviceAwsSqs = serviceAwsSqs;
        this.userService = userService;
        this.tokenInterceptorService = tokenInterceptorService;
        this.spedService = spedService;
    }

    @Scheduled(fixedDelay = TEN_SECONDS)
    public void run() {
        if (!enableThread(awsSecretsDto.getProcessQueueSped())) {
            return;
        }

        List<Message> queueMessages = serviceAwsSqs.returnMessages(awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSped());

        for (Message message : queueMessages) {
            try {
                JSONObject jsonRequestSped = new JSONObject(message.getBody());
                SpedDto spedDto = new SpedDto();
                JSONHelper.mergeJson(spedDto, jsonRequestSped, map());
                this.setAuthenticationByUserId(spedDto.getIdUser());

                spedService.generateSpedBySpedDto(spedDto);
                serviceAwsSqs.getClienteSQS().deleteMessage(awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueSped(), message.getReceiptHandle());

            } catch (Exception e) {
                LOG.error("Error processing ProcessQueueSpedThread failed: {} | Reason: {}", message.getBody(), e.getMessage(), e);
            }
        }
    }

    private void setAuthenticationByUserId(Long userId) {
        Usuario user = userService.findById(userId);
        tokenInterceptorService.getUserOauth(user.getLogin(), user.getSenha(), Util.NOT_ACCESS_FROM_API_KEY);
    }
}