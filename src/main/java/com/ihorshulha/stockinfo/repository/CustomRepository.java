package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import com.ihorshulha.stockinfo.entity.Stock;

import java.util.List;

public interface CustomRepository {

    void saveCompanies(List<Company> companies);

    void saveStocks(List<Stock> stocks);
}
