package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Stock;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Component
public class CustomRepositoryImpl implements CustomRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Disposable updateOneStock(Stock stock) {
        return Mono.from(connectionFactory.create())
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
    }

    @Override
    public Disposable saveOneStock(Stock stock) {
        return Mono.from(connectionFactory.create())
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
    }
}