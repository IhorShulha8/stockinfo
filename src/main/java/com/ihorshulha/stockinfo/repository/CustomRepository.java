package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomRepository {

    Mono<Company> save(Company company);

    void saveStocks(List<Stock> stocks);
}
