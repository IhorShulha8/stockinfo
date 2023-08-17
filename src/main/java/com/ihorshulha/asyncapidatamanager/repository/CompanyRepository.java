package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, Integer> {
}
