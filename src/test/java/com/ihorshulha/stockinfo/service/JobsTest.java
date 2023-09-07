package com.ihorshulha.stockinfo.service;

import com.ihorshulha.stockinfo.BaseAbstractTest;
import com.ihorshulha.stockinfo.job.AnalyticDataJob;
import com.ihorshulha.stockinfo.job.ProcessDataJob;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import reactor.core.publisher.Mono;

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

    @Test
    public void scheduleOnStartupTest() {
        await()
                .pollDelay(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(processingDataJob, atMostOnce()).runProcessingCompanyDataJob());
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
    public void callOnStartupTest() {
        when(dataProcessingService.processingCompanyData()).thenReturn(Mono.empty());
        processingDataJob.runProcessingCompanyDataJob();
        verify(dataProcessingService, atLeastOnce()).processingCompanyData();
    }

    @Test
    public void callGetStockDataTest() {
        when(dataProcessingService.processingStockData()).thenReturn(Mono.empty());
        processingDataJob.runProcessingStockDataJob();
        verify(dataProcessingService, atLeastOnce()).processingStockData();
    }

    @Test
    public void callGetAnalyticDataTest() {
        analyticsDataJob.runAnalyticsJob();
        verify(analyticService, atLeastOnce()).getTopFiveStockPrice();
        verify(analyticService, atLeastOnce()).getTopFiveDeltaStocksPrice();
    }
}