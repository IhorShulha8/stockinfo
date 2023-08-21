package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import com.ihorshulha.asyncapidatamanager.entity.Stock;

import java.util.List;

public interface CustomRepository {

    void saveCompanies(List<Company> companies);

    void saveStocks(List<Stock> stocks);
}
