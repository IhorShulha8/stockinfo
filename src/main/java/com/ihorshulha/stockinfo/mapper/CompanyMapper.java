package com.ihorshulha.stockinfo.mapper;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.entity.Company;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDTO mapToCompany(Company company);

    @InheritInverseConfiguration
    Company mapToCompanyDto(CompanyDTO companyDTO);

}
