package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
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

    @Override
    public void saveCompanies(List<Company> companies) {
        companies.forEach(company -> r2dbcEntityTemplate.insert(company)
                .subscribe());
    }

    @Override
    public void saveStocks(List<Stock> stocks) {
        stocks.stream()
                .filter(Objects::nonNull)
                .forEach(stock -> r2dbcEntityTemplate.insert(stock)
                        .subscribe());
    }
}