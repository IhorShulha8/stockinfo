package com.ihorshulha.stockinfo.repository;

import com.ihorshulha.stockinfo.entity.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, Integer> {
}
