package com.ihorshulha.stockinfo.service;

import com.ihorshulha.stockinfo.BaseAbstractTest;
import com.ihorshulha.stockinfo.job.AnalyticDataJob;
import com.ihorshulha.stockinfo.job.ProcessDataJob;
import com.ihorshulha.stockinfo.repository.CustomRepositoryImpl;
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
        processingDataJob.onStartupProcessingCompanyDataJob();

        verify(dataProcessingService, atLeastOnce()).getCompaniesData();
        verify(customRepository, atLeastOnce()).saveCompanies(anyList());
    }

    @Test
    public void callGetStockDataTest(){
        processingDataJob.runProcessingStockDataJob();

        verify(dataProcessingService, atLeastOnce()).getStocksData();
        verify(customRepository, atLeastOnce()).saveStocks(anyList());
    }

    @Test
    public void callGetAnalyticDataTest(){
        analyticsDataJob.runAnalyticsJob();

        verify(analyticService, atLeastOnce()).getTopFiveStockPrice();
        verify(analyticService, atLeastOnce()).getTopFiveDeltaStocksPrice();
    }
}