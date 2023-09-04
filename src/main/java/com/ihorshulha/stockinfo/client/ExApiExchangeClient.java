package com.ihorshulha.stockinfo.client;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.dto.StockDto;
import com.ihorshulha.stockinfo.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    private final RestClient restClient;
    private final StockMapper stockMapper;

    public List<CompanyDTO> getCompanies() {
        return restClient.get()
                .uri(String.format(refDataUrl, token))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> log.error(response.getStatusText()))
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> log.error(response.getStatusText()))
                .body(new ParameterizedTypeReference<List<CompanyDTO>>() {
                });
    }

    public StockDto getOneCompanyStock(String url) {
        return restClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange(this::exchangeData);
    }

    public String getStocksUrl(String symbol) {
        String url = String.format(stockPriceUrl, symbol, token);
        log.debug("Url {} was generated.", url);
        return url;
    }

    private StockDto exchangeData(HttpRequest request, ClientHttpResponse response) throws IOException {
        StockDto apiResponse = null;
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            log.error(response.getStatusText());
        } else {
            validateResponse(response.getBody());
            apiResponse = stockMapper.inputStreamToStockDto(response.getBody());
        }
        return apiResponse;
    }

    private void validateResponse(InputStream inputStream) throws IOException {
        if (inputStream.available() == 0) log.error("Response is skipped because it is null");
    }
}
