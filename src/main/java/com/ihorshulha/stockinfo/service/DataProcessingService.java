package com.ihorshulha.stockinfo.service;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.dto.StockDto;
import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;
import com.ihorshulha.stockinfo.mapper.CompanyMapper;
import com.ihorshulha.stockinfo.mapper.StockMapper;
import com.ihorshulha.stockinfo.client.ExApiExchangeClientImpl;
import com.ihorshulha.stockinfo.repository.CustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessingService {

    @Value("${service.number-of-companies}")
    private Integer NUMBER_OF_COMPANIES;
    private final List<String> tasks = new CopyOnWriteArrayList<>();

    private final ExApiExchangeClientImpl apiClient;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;
    private final CustomRepository customRepository;


    public Mono<Void> processingCompanyData() {
        tasks.clear();
        return apiClient.callToApi()
                .filter(CompanyDTO::isEnabled)
                .take(NUMBER_OF_COMPANIES)
                .map(companyMapper::map)
                .map(this::addTask)
                .map(customRepository::save)
                .map(Mono::subscribe)
                .then();
    }

    public List<StockDto> getStocksData() {
        List<CompletableFuture<StockDto>> futures = queueClient.getTaskQueue().stream()
                .map(task -> CompletableFuture.supplyAsync(() -> apiClient.getOneCompanyStock(task)))
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

    public List<Stock> mapToEntity(List<StockDto> stockDtos) {
        List<CompletableFuture<Stock>> futures = stockDtos.stream()
                .map(stockDto -> CompletableFuture.supplyAsync(() -> stockMapper.stockDtoToStock(stockDto)))
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList())
                .join();
    }

    public Company addTask(Company company) {
        String uri = apiClient.getUri(company.getSymbol());
        tasks.add(uri);
        return company;
    }

    public String getTask() {
        String task = tasks.get(0);
        tasks.remove(task);
        return task;
    }
}
