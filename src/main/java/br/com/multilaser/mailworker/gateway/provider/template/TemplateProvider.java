package br.com.multilaser.mailworker.gateway.provider.template;

import br.com.multilaser.mailworker.gateway.provider.template.dto.response.TemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "template", url = "localhost:8080/")
public interface TemplateProvider {

    @RequestMapping(path = "api/v1/template/{id}",
                    method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    TemplateDTO getTemplate(@PathVariable Integer id);

}