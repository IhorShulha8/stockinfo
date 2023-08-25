package com.ihorshulha.stockinfo.mapper;

import com.ihorshulha.stockinfo.dto.CompanyDTO;
import com.ihorshulha.stockinfo.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company INSTANCE = Mappers.getMapper(Company.class);

    CompanyDTO companyToCompanyDTO(Company company);

    Company companyDtoToCompany(CompanyDTO company);

}
