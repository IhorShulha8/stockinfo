package com.ihorshulha.asyncapidatamanager.dto;

import java.math.BigDecimal;

public record StockDto(String companyName, Integer volume, Integer previousVolume, BigDecimal latestPrice, BigDecimal change) {
}
