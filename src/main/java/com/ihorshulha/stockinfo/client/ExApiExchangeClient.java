package com.ihorshulha.stockinfo.client;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import reactor.core.publisher.Flux;

import java.net.URI;

public interface ExApiExchangeClient {

    public Flux<CompanyDTO> callToApi();

    public URI getUri();

    public String getUri(String symbol);
}
