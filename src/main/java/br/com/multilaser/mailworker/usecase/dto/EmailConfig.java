package br.com.multilaser.mailworker.usecase.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class EmailConfig {

    public Integer template;
    public Address address;
    public Config config;
    public List<String> attachments;

    public static class Address {
        public String from;
        public List<String> to;
    }

    public static class Config {
        public String subject;
        public String delimiter;
        public String separator;
        public String parameters;
    }

}