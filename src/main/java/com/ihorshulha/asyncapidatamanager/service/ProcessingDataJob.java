package com.ihorshulha.asyncapidatamanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ProcessingDataJob {

    private final DataProcessingService dataProcessingService;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 1)
    public void onStartup() {
        CompletableFuture.supplyAsync(dataProcessingService::processingOfCompanyData)
                .thenAccept(dataProcessingService::saveCompanies)
                .join();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void getStockData() {
        CompletableFuture.supplyAsync(dataProcessingService::processingOfStocksData)
                .thenAccept(dataProcessingService::saveStocks)
                .join();
    }
}
