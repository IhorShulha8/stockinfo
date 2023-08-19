//package com.ihorshulha.asyncapidatamanager.service;
//
//import com.ihorshulha.asyncapidatamanager.BaseAbstractTest;
//import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
//import com.ihorshulha.asyncapidatamanager.dto.StockDto;
//import com.ihorshulha.asyncapidatamanager.entity.Company;
//import com.ihorshulha.asyncapidatamanager.entity.Stock;
//import com.ihorshulha.asyncapidatamanager.repository.CompanyRepository;
//import com.ihorshulha.asyncapidatamanager.repository.StockRepository;
//import com.ihorshulha.asyncapidatamanager.client.ExApiExchangeClient;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import reactor.core.publisher.Flux;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class DataProcessingServiceTest extends BaseAbstractTest {
//    @Value("${service.numberOfCompanies}")
//    private Integer NUMBER_OF_COMPANIES;
//    @SpyBean
//    private DataProcessingService dataProcessingService;
//    @MockBean
//    private ExApiExchangeClient apiClient;
//    @MockBean
//    private CompanyRepository companyRepository;
//    @MockBean
//    private StockRepository stockRepository;
//
//    @Test
//    void whenProcessingOfCompanyDataSuccessful() {
////        Given
//        List<CompanyDTO> companyDTOS = List.of(new CompanyDTO(1, "symbol1", true),
//                new CompanyDTO(2, "symbol2", false),
//                new CompanyDTO(3, "symbol3", true));
//        List<Company> expected = List.of(Company.builder().cik("1").symbol("symbol1").build(),
//                Company.builder().cik("3").symbol("symbol3").build());
//        int expectedSize = expected.size();
//
////        When
//        when(apiClient.getCompanies()).thenReturn(companyDTOS);
//        List<Company> actual = dataProcessingService.processingOfCompanyData();
//
////        Then
//        assertEquals(expected, actual);
//        assertEquals(expectedSize, actual.size());
//    }
//
////    @Test
//    void whenProcessingOfStocksDataSuccessful() {
////        Given
//        Optional<StockDto> stockDtoOptional = Optional.of(new StockDto(1L, "symbol", BigDecimal.ONE,
//                BigDecimal.valueOf(0.01), 100, 100, "Name"));
//        List<Stock> expected = List.of(new Stock(1, "symbol", BigDecimal.ONE, BigDecimal.valueOf(0.01),
//                BigDecimal.valueOf(0.01), 100, 100, "Name"));
//        queueClient.getCompanyQueue().add("test-task");
//
////        When
//        when(apiClient.getOneCompanyStock(anyString())).thenReturn(/*stockDtoOptional*/ null);
//        List<Stock> actual = dataProcessingService.processingOfStocksData();
//
////        Then
//        assertTrue(queueClient.getCompanyQueue().isEmpty());
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void whenSaveCompaniesSuccessful() {
////        Given
//        List<Company> companies = List.of(new Company());
//
////        When
//        when(companyRepository.saveAll(companies)).thenReturn(Flux.just());
//        dataProcessingService.saveCompanies(companies);
//
////        Then
//        verify(companyRepository, times(1)).saveAll(companies);
//    }
//
//    @Test
//    void whenSaveStocksSuccessful() {
////        Given
//        List<Stock> stocks = List.of(new Stock());
//
////        When
//        when(stockRepository.saveAll(stocks)).thenReturn(Flux.just());
//        dataProcessingService.saveStocks(stocks);
//
////        Then
//        verify(stockRepository, times(1)).saveAll(stocks);
//    }
//}