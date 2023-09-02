package com.ihorshulha.stockinfo.client;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.dto.StockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static com.ihorshulha.stockinfo.util.IgnoreRuntimeException.ignoredException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExApiExchangeClient {

    @Value("${api.external.ref-data-url}")
    private String refDataUrl;
    @Value("${api.external.stock-data-url}")
    private String stockPriceUrl;
    @Value("${api.external.token}")
    private String token;

    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private ResponseEntity<StockDto[]> response;

    public List<CompanyDTO> getCompanies() {
        return restClient.get()
                .uri(String.format(refDataUrl, token))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<CompanyDTO>>() {
                });
    }

    public StockDto getOneCompanyStock(String url) {
        return Optional.of(response = getExtResponse(url))
                .filter(response -> (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())))
                .map(response -> response.getBody()[0])
                .orElseThrow(RuntimeException::new);
    }

    private ResponseEntity<StockDto[]> getExtResponse(String url) {
        ignoredException(() -> response = restTemplate.getForEntity(url, StockDto[].class));
        return response;
    }

    public String getStocksUrl(String symbol) {
        String url = String.format(stockPriceUrl, symbol, token);
        log.debug("Url {} was generated.", url);
        return url;
    }
}
