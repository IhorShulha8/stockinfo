package com.ihorshulha.stockinfo.job;

import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.repository.CustomRepository;
import com.ihorshulha.stockinfo.service.DataProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDataJob {

    private final DataProcessingService dataProcessingService;
    private final CustomRepository customRepository;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void onStartupProcessingCompanyDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::getCompaniesData)
                .thenAccept(customRepository::saveCompanies)
                .join();
    }

//    @TrackExecutionTime
    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void runProcessingStockDataJob() {
        CompletableFuture.supplyAsync(dataProcessingService::getStocksData)
                .thenAccept(customRepository::saveStocks)
                .join();
    }
}
