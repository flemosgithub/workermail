package br.com.multilaser.mailworker;

import br.com.multilaser.mailworker.gateway.provider.aws.dto.request.SESRequestDTO;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class AwsUtilsContractApplicationTest {

    @RegisterExtension
    public StubRunnerExtension stubRunner = new StubRunnerExtension()
            .downloadStub(
                    "br.com.awsutils",
                    "awsutils",
                    "0.0.1-SNAPSHOT",
                    "stubs"
            )
            .withPort(8080)
            .stubsMode(StubRunnerProperties.StubsMode.LOCAL);

    @Test
    public void sendMailContract() {

        final SESRequestDTO sesRequestDTO = SESRequestDTO.builder()
                .fromAddress("fabiojavax@gmail.com")
                .toAddress("mogi@multilaser.com.br")
                .subject("mock data for contract")
                .textBody("a simple mock data in order to implement springcloud contract")
                .htmlBody("a simple mock data in order to implement springcloud contract")
                .build();

        ResponseEntity<?> responseEntity = new RestTemplate().postForEntity("http://localhost:8080/mail/send",
                sesRequestDTO, Boolean.class);

        BDDAssertions.then(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
