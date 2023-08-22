package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.dto.StockDto;
import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import com.ihorshulha.asyncapidatamanager.mapper.CompanyMapper;
import com.ihorshulha.asyncapidatamanager.mapper.StockMapper;
import com.ihorshulha.asyncapidatamanager.client.ExApiExchangeClient;
import com.ihorshulha.asyncapidatamanager.client.QueueClient;
import com.ihorshulha.asyncapidatamanager.util.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessingService {

    @Value("${service.number-of-companies}")
    private Integer NUMBER_OF_COMPANIES;

    private final ExApiExchangeClient apiClient;
    private final QueueClient queueClient;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;


    @TrackExecutionTime
    public List<Company> getCompaniesData() {
        queueClient.getCompanyQueue().clear();
        log.debug("Queue was cleared");
        return apiClient.getCompanies().stream()
                .filter(CompanyDTO::isEnabled)
                .limit(NUMBER_OF_COMPANIES)
                .map(companyDTO -> {
                    String url = apiClient.getStocksUrl(companyDTO.symbol());
                    queueClient.putToQueue(url);
                    return companyMapper.companyDtoToCompany(companyDTO);
                })
                .toList();
    }

    @TrackExecutionTime
    public List<Stock> getStocksData() {
        List<CompletableFuture<Stock>> futures = queueClient.getCompanyQueue().stream()
                .map(task -> CompletableFuture.supplyAsync(() -> {
                    StockDto stockDto = apiClient.getOneCompanyStock(task);
                    log.debug("StockDto was received {} by task {}",stockDto, task);
                    return stockMapper.stockDtoToStock(stockDto);
                }))
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList())
                .join();
    }
}
