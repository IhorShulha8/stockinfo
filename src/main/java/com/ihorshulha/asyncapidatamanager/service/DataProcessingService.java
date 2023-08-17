package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.dto.StockDto;
import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import com.ihorshulha.asyncapidatamanager.mapper.CompanyMapper;
import com.ihorshulha.asyncapidatamanager.mapper.StockMapper;
import com.ihorshulha.asyncapidatamanager.repository.CompanyRepository;
import com.ihorshulha.asyncapidatamanager.repository.StockRepository;
import com.ihorshulha.asyncapidatamanager.client.ExApiExchangeClient;
import com.ihorshulha.asyncapidatamanager.util.QueueClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessingService {

    @Value("${service.numberOfCompanies}")
    private Integer NUMBER_OF_COMPANIES;

    private final ExApiExchangeClient apiClient;
    private final QueueClient queueClient;
    private final CompanyRepository companyRepository;
    private final StockRepository stockRepository;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;

    public List<Company> processingOfCompanyData() {
        return apiClient.getCompanies().stream()
                .filter(CompanyDTO::isEnabled)
                .limit(NUMBER_OF_COMPANIES)
                .map(companyDTO -> {
                    queueClient.putToQueue(apiClient.getStockPriceUrl(companyDTO.symbol()));
                    return companyMapper.companyDtoToCompany(companyDTO);
                })
                .toList();
    }

    public List<Stock> processingOfStocksData() {
        ArrayList<Stock> list = new ArrayList<>();
        startThreadLogger(Thread.currentThread());
        List<Stock> oList = queueClient.getCompanyQueue().stream()
                .map(task ->
                        CompletableFuture.supplyAsync(() -> processData(list)).join()).toList();
        System.out.println(oList);
        finishThreadLogger(Thread.currentThread());
        return list;
    }

    private Stock processData(ArrayList<Stock> list) {
        String url = queueClient.takeUrl();
        Optional<StockDto> stockDtoOptional = apiClient.getOneCompanyStock(url);
        StockDto stockDto = stockDtoOptional.orElseThrow();
        Stock stock = stockMapper.stockDtoToStock(stockDto);
        list.add(stock);
        finishThreadLogger(Thread.currentThread());
        return stock;
    }

    private void finishThreadLogger(Thread thread) {
        long finishTime = System.currentTimeMillis();
        String nameThread = Thread.currentThread().getName();
        String state = Thread.currentThread().getState().toString();
        log.info("Thread {} finished at {} and state {}", nameThread, finishTime, state);
    }

    private void startThreadLogger(Thread thread) {
        long startTime = System.currentTimeMillis();
        String nameThread = Thread.currentThread().getName();
        String state = Thread.currentThread().getState().toString();
        log.info("Thread {} started at {} and state {}", nameThread, startTime, state);
    }

    public void saveCompanies(List<Company> companies) {
        companyRepository.saveAll(companies).subscribe();
        log.debug("storing companies was completed");
    }

    public void saveStocks(List<Stock> stocks) {
        stockRepository.saveAll(stocks)
                .subscribe(stock -> log.debug("storing stocks was completed"));
    }
}
