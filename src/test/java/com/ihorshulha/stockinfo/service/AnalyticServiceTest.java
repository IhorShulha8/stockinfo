package com.ihorshulha.stockinfo.service;

import com.ihorshulha.stockinfo.BaseAbstractTest;
import com.ihorshulha.stockinfo.entity.Stock;
import com.ihorshulha.stockinfo.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AnalyticServiceTest extends BaseAbstractTest {
    @SpyBean
    private AnalyticServiceImpl analyticService;
    @MockBean
    private StockRepository stockRepository;

    @Test
    public void whenContextLoadsSuccessful() {
        assertThat(stockRepository).isNotNull();
    }

    @Test
    void whenGetTopFiveStockPriceIsSuccessful() {
        when(stockRepository.findTop5ExpensiveStocks()).thenReturn(Flux.just(new Stock()));
        analyticService.getTopFiveStockPrice();

        verify(stockRepository, atLeastOnce()).findTop5ExpensiveStocks();
    }

    @Test
    void whenGetTopFiveDeltaStockPriceIsSuccessful() {//        Given
        when(stockRepository.findTop5HighestDeltaPrice()).thenReturn(Flux.just(new Stock()));
        analyticService.getTopFiveDeltaStocksPrice();

        verify(stockRepository, atLeastOnce()).findTop5HighestDeltaPrice();
    }
}