package br.com.multilaser.mailworker.fixture;

import br.com.multilaser.mailworker.gateway.provider.template.dto.response.TemplateDTO;

public class TemplateFixture {

    public static TemplateDTO defaultValues() {
        return TemplateDTO.builder()
                .id(1)
                .status(1)
                .content("{campo1} {campo2}")
                .build();
    }

}
