package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Stock;
import reactor.core.Disposable;

public interface CustomRepository {

    Disposable updateOneStock(Stock stock);

    Disposable saveOneStock(Stock stock);
}
