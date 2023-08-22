package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.BaseAbstractTest;
import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.dto.StockDto;
import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import com.ihorshulha.asyncapidatamanager.client.ExApiExchangeClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DataProcessingServiceTest extends BaseAbstractTest {
    @Value("${service.number-of-companies}")
    private Integer NUMBER_OF_COMPANIES;
    @SpyBean
    private DataProcessingService dataProcessingService;
    @MockBean
    public ExApiExchangeClient apiClient;

    @BeforeTestExecution
    public void setUp() {
        queueClient.getCompanyQueue().clear();
    }

    @Test
    void whenProcessingOfCompanyDataSuccessful() {
        String url = "url";
        CompanyDTO companyDTO1 = new CompanyDTO(1, "symbol1", true);
        CompanyDTO companyDTO2 = new CompanyDTO(2, "symbol2", false);
        CompanyDTO companyDTO3 = new CompanyDTO(3, "symbol3", true);
        Company company1 = new Company(1, "symbol1", true);
        Company company3 = new Company(3, "symbol3", true);
        List<CompanyDTO> companyDTOS = List.of(companyDTO1, companyDTO2, companyDTO3);
        List<Company> expected = List.of(company1, company3);
        int expectedSize = expected.size();
        companyDTOS.forEach(dto -> queueClient.putToQueue(dto.symbol()));

        when(apiClient.getCompanies()).thenReturn(companyDTOS);
        when(apiClient.getStocksUrl(anyString())).thenReturn(url);
        List<Company> actual = dataProcessingService.getCompaniesData();

        assertEquals(expected, actual);
        assertEquals(expectedSize, actual.size());
        verify(apiClient, times(1)).getCompanies();
        verify(apiClient, times(2)).getStocksUrl(anyString());
        verify(queueClient, times(1)).getCompanyQueue();
        verify(queueClient, times(2)).putToQueue(url);
    }

    @Test
    void whenProcessingOfStocksDataSuccessful() {
        StockDto stockDtoOptional = new StockDto(1L, "symbol", BigDecimal.ONE,
                BigDecimal.valueOf(0.01), 100, 100, "Name");
        List<Stock> expected = List.of(
                new Stock(1L, "symbol", BigDecimal.ONE, BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.01), 100, 100, "Name", true));
        queueClient.getCompanyQueue().add("test-task");

        when(apiClient.getOneCompanyStock(anyString())).thenReturn(stockDtoOptional);
        List<Stock> actual = dataProcessingService.getStocksData();

        assertEquals(expected, actual);
        verify(queueClient, times(2)).getCompanyQueue();
        verify(apiClient, times(1)).getOneCompanyStock(anyString());
    }
}