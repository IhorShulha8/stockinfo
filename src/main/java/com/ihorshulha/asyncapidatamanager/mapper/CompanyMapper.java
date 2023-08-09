package com.ihorshulha.asyncapidatamanager.mapper;

import com.ihorshulha.asyncapidatamanager.dto.CompanyDTO;
import com.ihorshulha.asyncapidatamanager.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company INSTANCE = Mappers.getMapper(Company.class);
    CompanyDTO companyToCompanyDTO(Company company);
    Company companyDtoToCompany(CompanyDTO company);

}
