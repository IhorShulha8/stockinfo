package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CompanyRepository extends R2dbcRepository<Company, Long> {
}
