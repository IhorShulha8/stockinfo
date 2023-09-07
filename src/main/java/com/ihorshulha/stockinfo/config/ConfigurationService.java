package com.ihorshulha.stockinfo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@EnableR2dbcRepositories
public class ConfigurationService {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}

