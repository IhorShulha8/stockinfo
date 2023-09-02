package com.ihorshulha.stockinfo.service;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.dto.StockDto;
import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;
import com.ihorshulha.stockinfo.mapper.CompanyMapper;
import com.ihorshulha.stockinfo.mapper.StockMapper;
import com.ihorshulha.stockinfo.client.ExApiExchangeClient;
import com.ihorshulha.stockinfo.client.QueueClient;
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


    public List<Company> getCompaniesData() {
        return apiClient.getCompanies().stream()
                .filter(CompanyDTO::isEnabled)
                .limit(NUMBER_OF_COMPANIES)
                .map(companyDTO -> {
                    putUrlToQueue(companyDTO);
                    return companyMapper.companyDtoToCompany(companyDTO);
                })
                .toList();
    }

    public List<Stock> getStocksData() {
        List<CompletableFuture<Stock>> futures = queueClient.getTaskQueue().stream()
                .map(task -> CompletableFuture.supplyAsync(() -> {
                    StockDto stockDto = apiClient.getOneCompanyStock(task);
                    log.debug("StockDto was received {} by task {}", stockDto, task);
                    return stockMapper.stockDtoToStock(stockDto);
                }))
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList())
                .join();
    }

    private void putUrlToQueue(CompanyDTO companyDTO) {
        String url = apiClient.getStocksUrl(companyDTO.symbol());
        queueClient.putToQueue(url);
    }

    public void clearQueue() {
        queueClient.getTaskQueue().clear();
        log.debug("Queue was cleared");
    }
}
