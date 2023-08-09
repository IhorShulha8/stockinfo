package com.ihorshulha.asyncapidatamanager.dto;


import jakarta.validation.constraints.NotEmpty;

public record CompanyDTO(@NotEmpty Long id, String symbol, boolean isEnabled) {
}
