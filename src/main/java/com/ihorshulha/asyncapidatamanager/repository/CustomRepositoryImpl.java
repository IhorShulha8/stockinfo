package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import com.ihorshulha.asyncapidatamanager.util.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final CompanyRepository companyRepository;

    @Override
    @TrackExecutionTime
    public void saveCompanies(List<Company> companies) {
        companyRepository.saveAll(companies).subscribe();
//        companies.forEach(company -> r2dbcEntityTemplate.insert(company).subscribe());
        log.debug("{} companies were saved", companies.size());
    }

    @TrackExecutionTime
    @Override
    public void saveStocks(List<Stock> stocks) {
        stocks.stream()
                .filter(Objects::nonNull)
                .forEach(stock -> r2dbcEntityTemplate.insert(stock)
                        .doOnError(throwable -> log.error("Requesting stock: {} caused an error: {}",stock, throwable.getMessage()))
                        .subscribe());
        log.debug("{} stocks were saved", stocks.size());
    }
}