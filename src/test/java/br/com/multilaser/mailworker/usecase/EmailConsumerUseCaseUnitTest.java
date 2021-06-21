package br.com.multilaser.mailworker.usecase;

import br.com.multilaser.mailworker.fixture.EmailConfigFixture;
import br.com.multilaser.mailworker.fixture.TemplateFixture;
import br.com.multilaser.mailworker.gateway.provider.aws.AwsProvider;
import br.com.multilaser.mailworker.gateway.provider.template.TemplateProvider;
import br.com.multilaser.mailworker.usecase.dto.EmailConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmailConsumerUseCaseUnitTest {

    @InjectMocks
    private EmailConsumerUseCase emailConsumerUseCase;

    @Mock
    private TemplateProvider templateProvider;

    @Mock
    private AwsProvider awsProvider;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void shouldSendEmailWithAwsProvider() throws JsonProcessingException {
        final Integer templateId = 1;

        when(objectMapper.readValue(anyString(), eq(EmailConfig.class))).thenReturn(EmailConfigFixture.defaultValues());

        when(templateProvider.getTemplate(templateId)).thenReturn(TemplateFixture.defaultValues());
        when(awsProvider.sendMail(any())).thenReturn(true);

        String response = emailConsumerUseCase.consume("test");

        Assert.notNull(response, "response should not be null");
        Assert.isTrue(response.contains("testing1"), "merged email is invalid");
        verify(awsProvider, times(1)).sendMail(any());
    }

    @Test
    @Disabled
    public void shouldHitAnErrorAndSendEmailToKafkaDLQ() {

    }

}
