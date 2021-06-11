package br.com.multilaser.mailworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MailworkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailworkerApplication.class, args);
    }

}
