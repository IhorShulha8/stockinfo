package com.ihorshulha.asyncapidatamanager.repository;


import com.ihorshulha.asyncapidatamanager.entity.Stock;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface StockRepository extends R2dbcRepository<Stock, Long> {
}
