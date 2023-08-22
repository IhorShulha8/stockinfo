package com.ihorshulha.asyncapidatamanager.job;

import com.ihorshulha.asyncapidatamanager.repository.CustomRepositoryImpl;
import com.ihorshulha.asyncapidatamanager.service.DataProcessingService;
import com.ihorshulha.asyncapidatamanager.util.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDataJob {

    private final DataProcessingService dataProcessingService;
    private final CustomRepositoryImpl customRepository;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 1)
    public void onStartupProcessingCompanyDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::getCompaniesData)
                .thenAccept(customRepository::saveCompanies)
                .join();
    }

    @TrackExecutionTime
    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void runProcessingStockDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::getStocksData)
                .thenAccept(customRepository::saveStocks)
                .join();
    }
}
