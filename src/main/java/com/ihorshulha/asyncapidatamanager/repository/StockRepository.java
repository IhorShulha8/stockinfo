package com.ihorshulha.asyncapidatamanager.repository;


import com.ihorshulha.asyncapidatamanager.entity.Stock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockRepository extends R2dbcRepository<Stock, Integer> {

    @Query("SELECT * FROM stock WHERE symbol = $1")
    Mono<Stock> findBySymbol(String symbol);

    @Query("SELECT CASE WHEN EXISTS (SELECT symbol FROM stock WHERE symbol = $1) THEN 1 ELSE 0 END")
    Mono<Boolean> existsSymbol(String symbol);

    @Query("select * from stock s where s.symbol = :symbol")
    Mono<Stock> findBySymbol2(String symbol);
}
