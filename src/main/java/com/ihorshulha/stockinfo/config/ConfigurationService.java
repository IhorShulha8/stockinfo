package com.ihorshulha.stockinfo.config;

import com.ihorshulha.stockinfo.exception.RestTemplateResponseErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Configuration
@EnableR2dbcRepositories
public class ConfigurationService {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        (request, response) -> log.error("RestClient status error: " + response.getStatusText()))
                .build();
    }
}

