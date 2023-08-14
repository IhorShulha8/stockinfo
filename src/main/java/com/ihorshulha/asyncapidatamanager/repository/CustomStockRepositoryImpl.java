package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Stock;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomStockRepositoryImpl implements CustomStockRepository {

    private final ConnectionFactory connectionFactory;

    @Override
    public Mono<Void> updateOneStock(Stock stock) {
        Mono.from(connectionFactory.create())
                .flatMapMany(connection -> connection
                        .createStatement("UPDATE stock\n" +
                                "SET volume = $2, change = $3, latest_price = $4, previous_volume = $5\n" +
                                "WHERE symbol = $1;")
                        .bind("$1", stock.getSymbol())
                        .bind("$2", stock.getVolume())
                        .bind("$3", stock.getChange())
                        .bind("$4", stock.getLatestPrice())
                        .bind("$5", stock.getPreviousVolume())
                        .execute())
                .subscribe();
        return null;
    }

    @Override
    public Mono<Void> saveOneStock(Stock stock) {
        Mono.from(connectionFactory.create())
                .flatMapMany(connection -> connection
                        .createStatement("INSERT INTO stock (symbol, volume, change, latest_price, previous_volume, company_name)" +
                                "VALUES($1, $2, $3, $4, $5, $6);")
                        .bind("$1", stock.getSymbol())
                        .bind("$2", stock.getVolume())
                        .bind("$3", stock.getChange())
                        .bind("$4", stock.getLatestPrice())
                        .bind("$5", stock.getPreviousVolume())
                        .bind("$6", stock.getCompanyName())
                        .execute())
                .subscribe();
        return Mono.empty();
    }

    @Override
    public Mono<Stock> getOne(String symbol) {
        return null;
    }

}