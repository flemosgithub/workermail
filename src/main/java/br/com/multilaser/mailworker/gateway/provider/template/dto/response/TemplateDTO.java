package br.com.multilaser.mailworker.gateway.provider.template.dto.response;

import lombok.Builder;

@Builder
public class TemplateDTO {
    
    public Integer id;
    public String content;
    public Integer status;

}
