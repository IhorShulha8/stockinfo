package com.ihorshulha.stockinfo.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorshulha.stockinfo.dto.StockDto;
import com.ihorshulha.stockinfo.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    StockDto stockStockDTO(Stock stock);

    Stock stockDtoToStock(StockDto stockDto);

    default StockDto inputStreamToStockDto(InputStream inputStream) throws IOException {
        String resultString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(resultString, StockDto[].class)[0];
    }
}
