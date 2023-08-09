package com.ihorshulha.asyncapidatamanager.mapper;

import com.ihorshulha.asyncapidatamanager.dto.StockDto;
import com.ihorshulha.asyncapidatamanager.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface StockMapper {

    Stock INSTANCE = Mappers.getMapper(Stock.class);

    StockDto stockStockDTO(Stock company);

    Stock StockDtoToStock(StockDto company);

}
