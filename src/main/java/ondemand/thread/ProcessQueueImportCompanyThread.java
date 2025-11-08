package ondemand.thread;

import static ondemand.constants.CompanyConstants.ID_FILE_AMAZON;
import static ondemand.constants.CompanyConstants.URL_FILE_AMAZON;
import static ondemand.constants.CompanyConstants.USER_EMAIL;
import static ondemand.constants.CompanyConstants.USER_PERSISTENCE_UNIT;
import ondemand.service.CompanyImportService;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("processQueueImportCompanyThread")
public class ProcessQueueImportCompanyThread extends ThreadsDefault {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessQueueImportCompanyThread.class);
    private final AwsSecretsDto awsSecretsDto;
    private final ServiceAwsSqs serviceAwsSqs;
    private final CompanyImportService companyImportService;
    private static final ObjectMapper mapper = new ObjectMapper();

    public ProcessQueueImportCompanyThread(AwsSecretsDto awsSecretsDto, ServiceAwsSqs serviceAwsSqs, CompanyImportService companyImportService) {
        this.awsSecretsDto = awsSecretsDto;
        this.serviceAwsSqs = serviceAwsSqs;
        this.companyImportService = companyImportService;
    }

    @Scheduled(fixedDelay = SECOND * MULTIPLE_FIVE)
    public void execute() {
        boolean condition = enableThread(awsSecretsDto.getProcessImportCompanyQueueFifo());
        while (condition) {
            List<Message> messagesToSyncList = serviceAwsSqs.returnMessages(awsSecretsDto.getImportCompanyQueueFifo());
            condition = !messagesToSyncList.isEmpty();
            for (Message message : messagesToSyncList) {
                try {
                    JsonNode body = mapper.readTree(String.valueOf(message.getBody()));
                    String userEmail = body.path(USER_EMAIL).asText();
                    String urlFileAmazon = body.path(URL_FILE_AMAZON).asText();
                    Long idFileAmazon = body.path(ID_FILE_AMAZON).asLong();
                    String userPersistenceUnit = body.path(USER_PERSISTENCE_UNIT).asText();
                    companyImportService.companyJob(userEmail, userPersistenceUnit, urlFileAmazon, idFileAmazon);
                    this.deleteMessage(message);
                } catch (Exception e) {
                    LOG.error("Error ", message.getBody(), e.getMessage(), e);
                }
            }
        }
    }

    private void deleteMessage(Message message) {
        serviceAwsSqs.getClienteSQS().deleteMessage(awsSecretsDto.getImportCompanyQueueFifo(), message.getReceiptHandle());
    }
}
