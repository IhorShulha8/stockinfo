package com.ihorshulha.asyncapidatamanager.dto;


import org.springframework.data.annotation.Id;

public record CompanyDTO(@Id String cik, String symbol, Boolean isEnabled) {
}
