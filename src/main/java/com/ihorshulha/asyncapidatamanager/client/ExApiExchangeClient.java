package com.ihorshulha.asyncapidatamanager.client;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.dto.StockDto;
import com.ihorshulha.asyncapidatamanager.util.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.ihorshulha.asyncapidatamanager.util.IgnoreRuntimeException.ignoredException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExApiExchangeClient {

    @Value("${api.external.ref-data-url}")
    protected String refDataUrl;
    @Value("${api.external.stock-data-url}")
    protected String stockPriceUrl;
    @Value("${api.external.token}")
    protected String token;

    private final RestTemplate restTemplate;
    private ResponseEntity<StockDto[]> response;

    public List<CompanyDTO> getCompanies() {
        ParameterizedTypeReference<List<CompanyDTO>> typeRef = new ParameterizedTypeReference<>() {};

        List<CompanyDTO> companyDTOS = Optional.of(restTemplate.exchange(String.format(refDataUrl, token), HttpMethod.GET, null, typeRef))
                .filter(response -> (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())))
                .map(HttpEntity::getBody)
                .orElseThrow(RuntimeException::new);
        log.debug("List companies was received, size of list {}", companyDTOS.size());
        return companyDTOS;
    }

    @TrackExecutionTime
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
