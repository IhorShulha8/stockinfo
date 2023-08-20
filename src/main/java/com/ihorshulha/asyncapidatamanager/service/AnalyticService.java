package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticService {

    private final StockRepository stockRepository;
    private final R2dbcEntityTemplate R2dbcEntityTemplate;


    public void getTopFiveStockPrice() {
        AtomicInteger atomicInteger = new AtomicInteger();
        CompletableFuture.runAsync(() -> {
            stockRepository.findTop5ExpensiveStocks()
                    .subscribe(stock ->
                            log.info(atomicInteger.incrementAndGet() + " of the top 5 most expensive stocks: price-{} company-{}",
                                    stock.getLatestPrice(), stock.getCompanyName()));
        }).join();
    }

    public void getTopFiveDeltaStocksPrice() {
        AtomicInteger atomicInteger = new AtomicInteger();
        CompletableFuture.runAsync(() -> {
            stockRepository.findTop5HighestDeltaPrice()
                    .subscribe(stock ->
                            log.info(atomicInteger.incrementAndGet() + " of the 5 fastest growing stocks: change-{}, price-{}, company-{}",
                                    stock.getChange(), stock.getLatestPrice(), stock.getCompanyName()));
        }).join();
    }
}
