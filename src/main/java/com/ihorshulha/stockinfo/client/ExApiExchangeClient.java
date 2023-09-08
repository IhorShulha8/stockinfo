package com.ihorshulha.stockinfo.client;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.dto.StockDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface ExApiExchangeClient {
    Flux<CompanyDTO> callToCompanyApi();

    Mono<StockDto> callToStockApi(String uri);

    URI getCompanyUri();

    String getStockUri(String symbol);
}
