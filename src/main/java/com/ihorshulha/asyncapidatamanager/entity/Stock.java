package com.ihorshulha.asyncapidatamanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock {

    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "latest_price", nullable = false)
    private BigDecimal latestPrice;

    @Column(name = "change", nullable = false)
    private BigDecimal change;

    @Column(name = "previous_volume", nullable = false)
    private Integer previousVolume;

    @Column(name = "volume")
    private Integer volume;

    @NaturalId
    @Column(name = "company_name", nullable = false)
    private String companyName;
}
