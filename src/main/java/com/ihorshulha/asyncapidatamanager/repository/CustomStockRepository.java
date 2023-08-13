package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Stock;
import reactor.core.publisher.Mono;

public interface CustomStockRepository {

    Mono<Void> updateOneStock(Stock stock);

    Mono <Void> saveOneStock(Stock stock);

    Mono<Stock> getOne(String symbol);
}
