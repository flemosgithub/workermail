package br.com.multilaser.mailworker.fixture;

import br.com.multilaser.mailworker.usecase.dto.EmailConfig;

import java.util.Arrays;

public class EmailConfigFixture {

    public static EmailConfig defaultValues() {

        EmailConfig.Config config = new EmailConfig.Config();
        config.delimiter = ",";
        config.separator = ":";
        config.subject = "worker mail - unit testing";
        config.parameters = "campo1:testing1,campo2:testing2";

        EmailConfig.Address address = new EmailConfig.Address();
        address.from = "fabiojavax@gmail.com";
        address.to = Arrays.asList("fabiojavax@gmail.com", "rodrigo.moge@multilaser.com.br");

        return EmailConfig.builder()
                .template(1)
                .config(config)
                .address(address)
                .build();
    }

}
