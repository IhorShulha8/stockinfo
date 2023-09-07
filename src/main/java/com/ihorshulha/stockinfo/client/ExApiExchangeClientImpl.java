package com.ihorshulha.stockinfo.client;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.dto.StockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExApiExchangeClientImpl implements ExApiExchangeClient {

    @Value("${api.external.ref-data-url}")
    private String refDataUrl;
    @Value("${api.external.stock-data-url}")
    private String stockPriceUrl;
    @Value("${api.external.token}")
    private String token;

    private final WebClient webClient;

    public Flux<CompanyDTO> callToCompanyApi() {
        return webClient.get()
                .uri(getUri())
                .retrieve()
                .bodyToFlux(CompanyDTO.class);
    }

    public Mono<StockDto> callToStockApi(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(StockDto[].class)
                .filter(obj -> Objects.nonNull(obj[0]))
                .map(obj -> obj[0]);
    }

    public URI getUri() {
        return UriComponentsBuilder
                .fromUriString(refDataUrl)
                .build(token, "token");
    }

    public String getUri(String symbol) {
        return UriComponentsBuilder
                .fromUriString(stockPriceUrl)
                .build(symbol, token)
                .toString();
    }
}
