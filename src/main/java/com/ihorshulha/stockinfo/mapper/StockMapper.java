package com.ihorshulha.stockinfo.mapper;

import com.ihorshulha.stockinfo.dto.StockDto;
import com.ihorshulha.stockinfo.entity.Stock;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockDto mapToStock(Stock stock);

    @InheritInverseConfiguration
    Stock mapToStockDto(StockDto stockDto);
}
