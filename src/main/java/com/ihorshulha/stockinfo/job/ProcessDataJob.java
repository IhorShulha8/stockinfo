package com.ihorshulha.stockinfo.job;

import com.ihorshulha.stockinfo.mapper.StockMapper;
import com.ihorshulha.stockinfo.repository.CustomRepository;
import com.ihorshulha.stockinfo.service.DataProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDataJob {

    private final DataProcessingService dataProcessingService;
    private final CustomRepository customRepository;
    private final StockMapper stockMapper;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void runProcessingCompanyDataJob() {
        CompletableFuture.runAsync(() -> dataProcessingService.processingCompanyData().subscribe())
                .thenAccept(unused -> log.info("Processing data companies was finished"))
                .join();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void runProcessingStockDataJob() {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletableFuture.supplyAsync(dataProcessingService::getStocksData, executor)
                .thenApply(dataProcessingService::mapToEntity)
                .thenAccept(customRepository::saveStocks)
                .join();
    }
}
