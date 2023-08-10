package com.ihorshulha.asyncapidatamanager.repository;

import com.ihorshulha.asyncapidatamanager.entity.Company;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface CompanyRepository extends R2dbcRepository<Company, String> {

//    @Query("select * from company where symbol = $1")
//    Mono<Company> findCompanyBySymbol(String symbol);

    //    @Modifying
//    @Query("UPDATE user_task_finish SET current_value=current_value+:addValue WHERE user_id=:userId AND task_id=:taskId")
//    Mono<Integer> updateAddValue(@Param("userId") Integer userId, @Param("taskId") Integer taskId, @Param("addValue") Integer addValue);
//
//
//    @Modifying
//    @Query("INSERT INTO user_task_finish(user_id, task_id,current_value) VALUES (:userId, :taskId, :addValue)")
//    Mono<Integer> saveValue(@Param("userId") Integer userId, @Param("taskId") Integer taskId, @Param("addValue") Integer addValue);
//    @Query("UPDATE company SET symbol=symbol WHERE symbol=:symbol")
//    Mono<Company> updateCompany(@Param("symbol") String symbol);
}
