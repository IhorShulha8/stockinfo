package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

    private final CompanyRepository companyRepository;
    private final StockRepository stockRepository;
    private final DatabaseClient databaseClient;

    @Override
    public void saveCompanies(List<Company> companies) {
        companyRepository.saveAll(companies).subscribe();
    }

//    @Override
//    public Flux<String> getCompanies() {
//        return databaseClient.sql("SELECT DISTINCT symbol FROM company RETURNING symbol")
//                .filter((statement, executeFunction) -> statement.fetchSize(10).execute())
//                .map((row, rowMetadata) -> row.get("symbol", String.class))
//                .all();
////        return companyRepository.findAllSymbols().collectList();
//    }

    @Override
    public void saveStocks(List<Stock> stocks) {
        stocks.forEach(stock ->
                saveOneStock(stock)
                        .subscribe());
    }

    @Override
    public Flux<String> getCompanies() {
        return null;
    }


    protected Flux<Long> saveOneStock(Stock stock) {
        return databaseClient.sql("INSERT INTO stock (symbol, latest_price, change, previous_volume, volume, company_name) " +
                        "VALUES ($1, $2, $3, $4, $5, $6) RETURNING id")
                .bind("$1", stock.getSymbol())
                .bind("$2", stock.getLatestPrice())
                .bind("$3", stock.getChange())
                .bind("$4", stock.getPreviousVolume())
                .bind("$5", stock.getVolume())
                .bind("$6", stock.getCompanyName())
                .map((row, rowMetadata) -> row.get("id", Long.class))
                .all();
    }

    public Flux<Company> findCompanies() {
        return this.databaseClient
                .sql("SELECT * FROM posts")
                .filter((statement, executeFunction) -> statement.fetchSize(10).execute())
                .map((row, rowMetadata) -> row.get("company", Company.class))
                .all();
    }
}