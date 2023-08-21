package com.ihorshulha.asyncapidatamanager.repository;


import com.ihorshulha.asyncapidatamanager.entity.Stock;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StockRepository extends R2dbcRepository<Stock, Long> {

    @Query("SELECT DISTINCT symbol, latest_price, company_name FROM stock ORDER BY latest_price DESC, company_name ASC LIMIT 5;")
    Flux<Stock> findTop5ExpensiveStocks();

    @Query("SELECT DISTINCT symbol, latest_price, change, company_name FROM stock ORDER BY change DESC, company_name ASC LIMIT 5;")
    Flux<Stock> findTop5HighestDeltaPrice();
}
