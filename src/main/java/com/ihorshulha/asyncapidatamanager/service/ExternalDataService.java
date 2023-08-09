package com.ihorshulha.asyncapidatamanager.service;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.mapper.CompanyMapper;
import com.ihorshulha.asyncapidatamanager.repository.CompanyRepository;
import com.ihorshulha.asyncapidatamanager.util.ExternalApiClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class ExternalDataService {

    Logger logger = LoggerFactory.getLogger(ExternalDataService.class);

    private final ExternalApiClient apiClient;
    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;

    protected static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void processingOfCompanyData() {
        List<Company> companyEntities = new ArrayList<>();

        queue.clear();
        List<Company> list = apiClient.getCompanies().stream()
                .filter(CompanyDTO::isEnabled)
                .limit(200)
                .map(companyDTO -> {
                    Company company = mapper.companyDtoToCompany(companyDTO);
                    companyEntities.add(company);
                    queue.add(apiClient.getRefDataUrl());
                    return company;
                }).toList();

        Flux.from(companyRepository.saveAll(list))
                .collectList()
                .blockOptional();
    }

    //    public void processingOfStocksData(List<com.example.entity.Company> companyEntities) {
//        List<Stock> stockEntities = new ArrayList<>();
//        companyEntities.forEach(company ->
//                CompletableFuture.runAsync(() ->
//                                apiClient.getStockPrices(company.getSymbol())
//                                        .ifPresent(stockEntities::add))
//                        .join());
////        stockRepository.saveAll(stockEntities).flatMap(mapper.putDtoToEntity());
//        logger.info("save all stock entities to DB");
//    }

    @Scheduled(fixedDelay = 1000 * 360, initialDelay = 1000)
    public void onStartup() {
        companyRepository.deleteAll().blockOptional();
        processingOfCompanyData();
    }
}
