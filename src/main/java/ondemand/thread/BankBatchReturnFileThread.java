package ondemand.thread;

import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.amazonaws.services.sqs.model.Message;

@Component
public class BankBatchReturnFileThread extends ThreadsDefault {

    private static final Logger LOG = LoggerFactory.getLogger(BankBatchReturnFileThread.class);
    private static final String ERROR_BANK_BATCH_RETURN_FILE_PROCESS = "bankBatchReturnFileThread.error.process";
    private static final String USER_ID = "userId";
    private static final String FILE_NAME = "fileName";
    
    private final AwsSecretsDto awsSecretsDto;
    private final MessageSource messageSource;
    private final ServiceAwsSqs serviceAwsSqs;
    private final UsuarioService userService;
    private final TokenInterceptorService tokenInterceptorService;
    private final BankBatchReturnFileService bankBatchReturnFileService;

    public BankBatchReturnFileThread(AwsSecretsDto awsSecretsDto, MessageSource messageSource, ServiceAwsSqs serviceAwsSqs, 
            UsuarioService userService, TokenInterceptorService tokenInterceptorService, BankBatchReturnFileService bankBatchReturnFileService) {
        this.awsSecretsDto = awsSecretsDto;
        this.messageSource = messageSource;
        this.serviceAwsSqs = serviceAwsSqs;
        this.userService = userService;
        this.tokenInterceptorService = tokenInterceptorService;
        this.bankBatchReturnFileService = bankBatchReturnFileService;
    }
    
    @Scheduled(fixedDelay = TEN_SECONDS) 
    public void run() {
        if (!enableThread(awsSecretsDto.getProcessBankBatchReturnFile())) {
            return;
        }

        List<Message> queueMessages = serviceAwsSqs
                .returnMessages(awsSecretsDto.getUrlFilasSqs() 
                + awsSecretsDto.getQueueBankBatchReturnFile());

        JSONObject jsonReturnFile = null;
        for (Message message : queueMessages) {
            try {
                jsonReturnFile = new JSONObject(message.getBody());
                this.setAuthenticationByUserId(jsonReturnFile.getLong(USER_ID));
                
                BankBatchReturnFileImported bankBatchReturnFileImported = bankBatchReturnFileService.processBankBatchReturnFile(jsonReturnFile);

                bankBatchReturnFileService.afterProcessingReturnFileSendEmailAndDeleteFileAws(jsonReturnFile, bankBatchReturnFileImported);
                serviceAwsSqs.getClienteSQS().deleteMessage(awsSecretsDto.getUrlFilasSqs() + awsSecretsDto.getQueueBankBatchReturnFile(), message.getReceiptHandle());
                
            } catch (Exception e) {
                LOG.error(Util.formatExceptionMessage(ERROR_BANK_BATCH_RETURN_FILE_PROCESS, messageSource,
                        String.valueOf(jsonReturnFile.getLong(USER_ID)), 
                        jsonReturnFile.getString(FILE_NAME)), e.getMessage(), e);
            }
        }
    }
    
    private void setAuthenticationByUserId(Long userId) {
        Usuario user = userService.findById(userId);
        tokenInterceptorService.getUserOauth(user.getLogin(), user.getSenha(), Util.NOT_ACCESS_FROM_API_KEY);
    }    
}