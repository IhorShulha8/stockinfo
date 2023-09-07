package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Company> save(Company company) {
        return r2dbcEntityTemplate.insert(company)
                .doOnSuccess(c -> log.debug("Company with symbol {} was inserted", c.getSymbol()));
    }

    @Override
    public Mono<Stock> saveStock(Stock stock) {
        return r2dbcEntityTemplate.insert(stock)
                .doOnError(throwable -> log.error("Requesting stock: {} caused an error: {}", stock, throwable.getMessage()))
                .doOnSuccess(c -> log.debug("success"));
    }
}