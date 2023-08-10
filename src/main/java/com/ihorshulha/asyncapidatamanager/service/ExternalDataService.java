package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import com.ihorshulha.asyncapidatamanager.mapper.CompanyMapper;
import com.ihorshulha.asyncapidatamanager.mapper.StockMapper;
import com.ihorshulha.asyncapidatamanager.repository.CompanyRepository;
import com.ihorshulha.asyncapidatamanager.repository.StockRepository;
import com.ihorshulha.asyncapidatamanager.util.ExternalApiClient;
import com.ihorshulha.asyncapidatamanager.util.QueueClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ExternalDataService {

    @Value("${service.numberOfCompanies}")
    private Integer NUMBER_OF_COMPANIES;
    Logger logger = LoggerFactory.getLogger(ExternalDataService.class);

    private final ExternalApiClient apiClient;
    private final QueueClient queueClient;
    private final CompanyRepository companyRepository;
    private final StockRepository stockRepository;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;

    private List<Company> companies = new ArrayList<>();

    public void processingOfCompanyData() {
        queueIsEmpty();
        apiClient.getCompanies().stream()
                .filter(CompanyDTO::isEnabled)
                .limit(NUMBER_OF_COMPANIES)
                .map(companyDTO -> {
                    Company company = companyMapper.companyDtoToCompany(companyDTO);
                    queueClient.getCompanyQueue().add(apiClient.getStockPriceUrl(companyDTO.symbol()));
                    return company;
                })
                .forEachOrdered(company -> companies.add(company));

        Flux.from(companyRepository.saveAll(companies))
                .then()
                .subscribe(i -> logger.info(" was saved"));
    }

    public void processingOfStocksData() {
        ExecutorService executor = Executors.newCachedThreadPool();

        List<Stock> stocks = companies.stream()
                .map(company -> CompletableFuture.supplyAsync(() -> apiClient.getStock(company.getSymbol()), executor))
                .map(contentFuture -> contentFuture.thenApply(Optional::orElseThrow))
                .map(stockDtoFuture -> stockDtoFuture.thenApply(stockMapper::stockDtoToStock))
                .map(CompletableFuture::join)
                .toList();
        logger.info("stocks stored to list");
        saveStocks(stocks);
    }

    private void saveStocks(List<Stock> stocks) {
        Flux.from(stockRepository.saveAll(stocks))
                .collectList()
                .blockOptional();
        logger.info("stocks stored to DB");
    }

    @Scheduled(fixedDelay = 1000 * 360, initialDelay = 100)
    public void onStartup() {
        companyRepository.deleteAll().blockOptional();
        processingOfCompanyData();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void getStockData() {
        processingOfStocksData();
        logger.info("stocks stored");
    }

    private void queueIsEmpty() {
        if (!queueClient.getCompanyQueue().isEmpty()) queueClient.getCompanyQueue().clear();
    }
}
