package com.ihorshulha.stockinfo.mapper;

import com.ihorshulha.stockinfo.dto.StockDto;
import com.ihorshulha.stockinfo.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {

    Stock INSTANCE = Mappers.getMapper(Stock.class);

    StockDto stockStockDTO(Stock stock);

    Stock stockDtoToStock(StockDto stockDto);

}
