package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final DataProcessingService dataProcessingService;
    private final AnalyticService analyticService;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void onStartup() {
        List<Company> companies = dataProcessingService.processingOfCompanyData();
        dataProcessingService.saveCompanies(companies);
    }

    @Scheduled(fixedDelay = 4000, initialDelay = 1000)
    public void getStockData() {
        List<Stock> stocks = dataProcessingService.processingOfStocksData();
        dataProcessingService.saveStocks(stocks);
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void getAnalyticData() {
        analyticService.getTopFiveStockPrice();
        analyticService.getTopFiveDeltaStocksPrice();
    }
}
