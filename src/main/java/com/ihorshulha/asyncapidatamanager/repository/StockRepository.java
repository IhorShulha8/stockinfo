package com.ihorshulha.asyncapidatamanager.repository;


import com.ihorshulha.asyncapidatamanager.entity.Stock;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface StockRepository extends R2dbcRepository<Stock, Integer> {

    @Query("SELECT * FROM stock ORDER BY latest_price DESC, company_name LIMIT 5")
    Flux<Stock> findTopFiveExpensiveStocks();

    @Query("SELECT * FROM stock ORDER BY CASE WHEN delta_price IS NULL THEN 1 ELSE 0 END, delta_price DESC LIMIT 5")
    Flux<Stock> findTopFiveHighestGrowthStocks();
}
