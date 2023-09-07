package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;
import reactor.core.publisher.Mono;

public interface CustomRepository {

    Mono<Company> save(Company company);

    Mono<Stock> saveStock(Stock stock);
}
