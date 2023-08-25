package com.ihorshulha.stockinfo.dto;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record StockDto(@Id Long id, String symbol, BigDecimal latestPrice, BigDecimal change, Integer previousVolume, Integer volume, String companyName) {
}
