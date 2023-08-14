package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticService {

    private final StockRepository stockRepository;

    public void getTopFiveStockPrice() {
        stockRepository.findTopFiveExpensiveStocks()
                .subscribe(stocks -> {
                    log.info("Top 5 companies with the expensive price of stock:");
                    stocks.forEach(stock ->
                            log.info("Price: {} Company: {}, ", stock.getLatestPrice(), stock.getCompanyName()));
                });
    }

    public void getTopFiveDeltaStocksPrice() {
        stockRepository.findTopFiveHighestGrowth()
                .subscribe(stocks -> {
                    log.info("Top 5 companies with the highest price increase:");
                    stocks.forEach(stock ->
                            log.info("Price: {} Delta: {} Company: {}, ", stock.getLatestPrice(), stock.getDeltaPrice(), stock.getCompanyName()));
                });
    }
}