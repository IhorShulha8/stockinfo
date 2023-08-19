package com.ihorshulha.asyncapidatamanager.repository;


import com.ihorshulha.asyncapidatamanager.entity.Stock;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StockRepository extends R2dbcRepository<Stock, Long> {

    @Query("SELECT * FROM stock ORDER BY latest_price DESC, company_name ASC LIMIT 5")
    Flux<Stock> findTop5ExpensiveStocks();

    @Query("SELECT * FROM stock ORDER BY CASE WHEN delta_price IS NULL THEN 1 ELSE 0 END, delta_price DESC, company_name ASC LIMIT 5")
    Flux<Stock> findTop5HighestDeltaPrice();
}
