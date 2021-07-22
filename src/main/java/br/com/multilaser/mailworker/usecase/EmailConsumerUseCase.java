package br.com.multilaser.mailworker.usecase;

import br.com.multilaser.mailworker.gateway.provider.aws.AwsProvider;
import br.com.multilaser.mailworker.gateway.provider.aws.dto.request.SESRequestDTO;
import br.com.multilaser.mailworker.gateway.provider.template.TemplateProvider;
import br.com.multilaser.mailworker.gateway.provider.template.dto.response.TemplateDTO;
import br.com.multilaser.mailworker.usecase.dto.EmailConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class EmailConsumerUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumerUseCase.class);
    private final ObjectMapper objectMapper;

    private final AwsProvider awsProvider;
    private final TemplateProvider templateProvider;

    private final KafkaTemplate<String, String> dlqProducer;
    private final KafkaTemplate<String, String> errorProducer;

    @KafkaListener(topics = "sap-mail-integration", groupId = "mensageria-email")
    public String consume(String message) {

        try {

            EmailConfig emailConfig = objectMapper.readValue(message, EmailConfig.class);

            TemplateDTO templateDTO = templateProvider.getTemplate(emailConfig.template);

            String mergedEmail = this.getMergedEmailContent(templateDTO.content, 
                                                            emailConfig.config.parameters, 
                                                            emailConfig.config.delimiter, 
                                                            emailConfig.config.separator);

//            final SESRequestDTO sesRequestDTO = SESRequestDTO.builder()
//                .fromAddress(emailConfig.address.from)
//                .toAddress("fabiojavax@gmail.com")
//                .subject(emailConfig.config.subject)
//                .textBody(mergedEmail)
//                .htmlBody(mergedEmail)
//                .build();

            final SESRequestDTO sesRequestDTO = SESRequestDTO.builder()
                    .fromAddress("fabiojavax@gmail.com")
                    .toAddress("fabiojavax@gmail.com")
                    .subject("Teste de email com Anexo")
                    .textBody("Essa mensagem deven possuir uma imagem como anexo")
                    .htmlBody("Essa mensagem deven possuir uma imagem como anexo")
                    .attachments(Collections.singletonList("multilaser.png"))
                    .build();

            awsProvider.sendMail(sesRequestDTO);

            return mergedEmail;

        } catch (Exception e) {
            logger.error(String.format("error when consuming message: %s", e.getMessage()));
            errorProducer.send("sap-mail-integration-dlq", message);
        }

        return null;

    }

    private String getMergedEmailContent(String templateContent, 
                                         String parametersToMerge, 
                                         String delimiter,
                                         String separator) {
        String mergedContent = templateContent;

        String[] parameters = parametersToMerge.split(delimiter);
        for (String parameter: parameters) {
            String[] parameterKeyValue = parameter.split(separator);
            mergedContent = mergedContent.replace(parameterKeyValue[0], parameterKeyValue[1]);
        }

        return mergedContent;
    }

}