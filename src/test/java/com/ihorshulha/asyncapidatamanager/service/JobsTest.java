package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.BaseAbstractTest;
import com.ihorshulha.asyncapidatamanager.job.AnalyticDataJob;
import com.ihorshulha.asyncapidatamanager.job.ProcessDataJob;
import com.ihorshulha.asyncapidatamanager.repository.CustomRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "scheduling.enabled=true")
public class JobsTest extends BaseAbstractTest {
    @SpyBean
    private AnalyticDataJob analyticsDataJob;
    @SpyBean
    private ProcessDataJob processingDataJob;
    @MockBean
    private DataProcessingService dataProcessingService;
    @MockBean
    private AnalyticService analyticService;
    @MockBean
    private CustomRepositoryImpl customRepository;


    @Test
    public void scheduleOnStartupTest() {
        await()
                .pollDelay(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(processingDataJob, atMostOnce()).onStartupProcessingCompanyDataJob());
    }

    @Test
    public void scheduleGetStockDataTest() {
        await()
                .pollDelay(6, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(processingDataJob, atMost(1)).runProcessingStockDataJob());
    }

    @Test
    public void scheduleOnGetAnalyticDataTest() {
        await()
                .pollDelay(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(analyticsDataJob, atLeast(1)).runAnalyticsJob());
    }

    @Test
    public void callOnStartupTest(){
//        When
        processingDataJob.onStartupProcessingCompanyDataJob();
//        Then
        verify(dataProcessingService, atLeastOnce()).processingOfCompanyData();
        verify(customRepository, atLeastOnce()).saveCompanies(anyList());
    }

    @Test
    public void callGetStockDataTest(){
//        When
        processingDataJob.runProcessingStockDataJob();
//        Then
        verify(dataProcessingService, atLeastOnce()).processingOfStocksData();
        verify(customRepository, atLeastOnce()).saveStocks(anyList());
    }

    @Test
    public void callGetAnalyticDataTest(){
//        When
        analyticsDataJob.runAnalyticsJob();
//        Then
        verify(analyticService, atLeastOnce()).getTopFiveStockPrice();
        verify(analyticService, atLeastOnce()).getTopFiveDeltaStocksPrice();
    }
}