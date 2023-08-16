package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.BaseAbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "scheduling.enabled=true")
public class ScheduleServiceTest extends BaseAbstractTest {
    @SpyBean
    private ScheduleService scheduleService;
    @MockBean
    private DataProcessingService dataProcessingService;
    @MockBean
    private AnalyticService analyticService;


    @Test
    public void scheduleOnStartupTest() {
        await()
                .pollDelay(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(scheduleService, atMostOnce()).onStartup());
    }

    @Test
    public void scheduleGetStockDataTest() {
        await()
                .pollDelay(6, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(scheduleService, atMost(1)).getStockData());
    }

    @Test
    public void scheduleOnGetAnalyticDataTest() {
        await()
                .pollDelay(1000, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(scheduleService, atLeast(1)).getAnalyticData());
    }

    @Test
    public void callOnStartupTest(){
//        When
        scheduleService.onStartup();
//        Then
        verify(dataProcessingService, atLeastOnce()).processingOfCompanyData();
        verify(dataProcessingService, atLeastOnce()).saveCompanies(anyList());
    }

    @Test
    public void callGetStockDataTest(){
//        When
        scheduleService.getStockData();
//        Then
        verify(dataProcessingService, atLeastOnce()).processingOfStocksData();
        verify(dataProcessingService, atLeastOnce()).saveStocks(anyList());
    }

    @Test
    public void callGetAnalyticDataTest(){
//        When
        scheduleService.getAnalyticData();
//        Then
        verify(analyticService, atLeastOnce()).getTopFiveStockPrice();
        verify(analyticService, atLeastOnce()).getTopFiveDeltaStocksPrice();
    }
}