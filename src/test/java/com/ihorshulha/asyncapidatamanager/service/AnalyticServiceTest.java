package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.BaseAbstractTest;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import com.ihorshulha.asyncapidatamanager.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AnalyticServiceTest extends BaseAbstractTest {
    @SpyBean
    private AnalyticService analyticService;
    @MockBean
    private StockRepository stockRepository;

    @Test
    public void whenContextLoadsSuccessful() {
        assertThat(stockRepository).isNotNull();
    }

    @Test
    void whenGetTopFiveStockPriceIsSuccessful() {
//        When
        when(stockRepository.findTopFiveExpensiveStocks()).thenReturn(Flux.just(new Stock()));
        analyticService.getTopFiveStockPrice();
//        Then
        verify(stockRepository, atLeastOnce()).findTopFiveExpensiveStocks();
    }

    @Test
    void whenGetTopFiveDeltaStockPriceIsSuccessful() {//        Given
//        When
        when(stockRepository.findTopFiveHighestGrowthStocks()).thenReturn(Flux.just(new Stock()));
        analyticService.getTopFiveDeltaStocksPrice();
//        Then
        verify(stockRepository, atLeastOnce()).findTopFiveHighestGrowthStocks();
    }
}