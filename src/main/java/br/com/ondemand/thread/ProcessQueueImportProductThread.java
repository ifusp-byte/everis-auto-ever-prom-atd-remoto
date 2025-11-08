package br.com.ondemand.thread;

import static ondemand.constants.ProductConstants.ID_FILE_AMAZON;
import static ondemand.constants.ProductConstants.URL_FILE_AMAZON;
import static ondemand.constants.ProductConstants.USER_EMAIL;
import static ondemand.constants.ProductConstants.USER_PERSISTENCE_UNIT;
import ondemand.service.ProductImportService;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("processQueueImportProductThread")
public class ProcessQueueImportProductThread extends ThreadsDefault {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessQueueImportProductThread.class);
    private final AwsSecretsDto awsSecretsDto;
    private final ServiceAwsSqs serviceAwsSqs;
    private final ProductImportService productImportService;
    private static final ObjectMapper mapper = new ObjectMapper();

    public ProcessQueueImportProductThread(AwsSecretsDto awsSecretsDto, ServiceAwsSqs serviceAwsSqs, ProductImportService productImportService) {
        this.awsSecretsDto = awsSecretsDto;
        this.serviceAwsSqs = serviceAwsSqs;
        this.productImportService = productImportService;
    }

    @Scheduled(fixedDelay = SECOND * MULTIPLE_FIVE)
    public void execute() {
        boolean condition = enableThread(awsSecretsDto.getProcessImportProductQueueFifo());
        while (condition) {
            List<Message> messagesToSyncList = serviceAwsSqs.returnMessages(awsSecretsDto.getImportProductQueueFifo());
            condition = !messagesToSyncList.isEmpty();
            for (Message message : messagesToSyncList) {
                try {
                    JsonNode body = mapper.readTree(String.valueOf(message.getBody()));
                    String userEmail = body.path(USER_EMAIL).asText();
                    String urlFileAmazon = body.path(URL_FILE_AMAZON).asText();
                    Long idFileAmazon = body.path(ID_FILE_AMAZON).asLong();
                    String userPersistenceUnit = body.path(USER_PERSISTENCE_UNIT).asText();
                    productImportService.productJob(userEmail, userPersistenceUnit, urlFileAmazon, idFileAmazon);
                    this.deleteMessage(message);
                } catch (Exception e) {
                    LOG.error("Error ", message.getBody(), e.getMessage(), e);
                }
            }
        }
    }

    private void deleteMessage(Message message) {
        serviceAwsSqs.getClienteSQS().deleteMessage(awsSecretsDto.getImportProductQueueFifo(), message.getReceiptHandle());
    }
}
