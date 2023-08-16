package com.ihorshulha.asyncapidatamanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsDataJob {

    private final AnalyticService analyticService;

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void runAnalyticsJob() {
        CompletableFuture.runAsync(() -> {
                    analyticService.getTopFiveStockPrice();
                    analyticService.getTopFiveDeltaStocksPrice();
                })
                .join();
    }
}
