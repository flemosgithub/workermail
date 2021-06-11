package br.com.multilaser.mailworker.gateway.provider.aws;

import br.com.multilaser.mailworker.gateway.provider.aws.dto.request.SESRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "awsutils", url = "localhost:8080/")
public interface AwsProvider {

    @RequestMapping(path = "mail/send",
                    method = RequestMethod.POST,
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    void sendMail(@RequestBody SESRequestDTO request);

}