package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final CompanyRepository companyRepository;
    private final DatabaseClient databaseClient;

    @Override
    public Mono<Company> save(Company company) {
        return r2dbcEntityTemplate.insert(company)
                .doOnSuccess(c -> log.debug("Company with symbol {} was inserted", c.getSymbol()));
    }

    @Override
    public void saveStocks(List<Stock> stocks) {
        stocks.parallelStream()
                .filter(Objects::nonNull)
                .forEach(stock -> r2dbcEntityTemplate.insert(stock)
                        .doOnError(throwable -> log.error("Requesting stock: {} caused an error: {}", stock, throwable.getMessage()))
                        .subscribe());
        log.debug("{} stocks were saved", stocks.size());
    }

    private Mono<Long> save(Company company) {
        return this.databaseClient.sql("INSERT INTO  company (symbol) VALUES (:symbol)")
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                .bind("symbol", company.getSymbol())
                .fetch()
                .first()
                .map(c -> (Long) c.get("id"));
    }
}