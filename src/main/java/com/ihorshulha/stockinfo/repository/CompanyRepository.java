package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CompanyRepository extends R2dbcRepository<Company, Integer> {
}
