package com.ihorshulha.asyncapidatamanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "is_enabled")
    private Boolean isEnabled;
}
