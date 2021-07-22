package br.com.multilaser.mailworker.gateway.provider.aws.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SESRequestDTO {

    private String fromAddress;
    private String toAddress;
    private String subject;
    private String textBody;
    private String htmlBody;
    private List<String> attachments;

}
