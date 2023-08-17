package com.ihorshulha.asyncapidatamanager.job;

import com.ihorshulha.asyncapidatamanager.service.DataProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ProcessDataJob {

    private final DataProcessingService dataProcessingService;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 1)
    public void onStartupProcessingCompanyDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::processingOfCompanyData)
                .thenAccept(dataProcessingService::saveCompanies)
                .join();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void runProcessingStockDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::processingOfStocksData)
                .thenAccept(dataProcessingService::saveStocks)
                .join();
    }
}
