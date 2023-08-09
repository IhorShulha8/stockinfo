package com.ihorshulha.asyncapidatamanager.util;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalApiClient {

    Logger logger = LoggerFactory.getLogger(ExternalApiClient.class);

    @Value("${api.external.ref-data-url}")
    protected String refDataUrl;
    @Value("${api.external.stock-data-url}")
    protected String stockPriceUrl;
    @Value("${api.external.token}")
    protected String token;

    private final RestTemplate restTemplate;

    public List<CompanyDTO> getCompanies() {
        List<CompanyDTO> companies;
        ParameterizedTypeReference<List<CompanyDTO>> typeReference = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<CompanyDTO>> response =
                restTemplate.exchange(String.format(refDataUrl, token), HttpMethod.GET, null, typeReference);
        if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
            companies = response.getBody();
        } else {
            throw new RuntimeException("status code is not 200");
        }
        return companies;
    }

    public Optional<Stock> getStockPrices(String symbol) {
        Optional<Stock> result = Optional.empty();
        try {
            ResponseEntity<Stock[]> response = restTemplate.exchange(String.format(stockPriceUrl, symbol, token), HttpMethod.GET, null, Stock[].class);
            if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
                result = Optional.ofNullable(response.getBody()[0]);
                logger.info("response - {}", result);
            }
        } catch (HttpStatusCodeException ex) {
            HttpStatusCode statusCode = ex.getStatusCode();
            if (statusCode.equals(HttpStatus.TOO_MANY_REQUESTS)) {
                logger.info("status code is 429");
                result = Optional.empty();
            }
        }
        return result;
    }

    public String getRefDataUrl() {
        return String.format(refDataUrl, token);
    }

    public String getStockPriceUrl(CompanyDTO company) {
        return String.format(stockPriceUrl, company.symbol(), token);
    }
}
