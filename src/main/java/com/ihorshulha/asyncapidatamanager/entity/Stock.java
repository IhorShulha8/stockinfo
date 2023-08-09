package com.ihorshulha.asyncapidatamanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_sequence")
    @SequenceGenerator(name = "stock_sequence", allocationSize = 1)
    private long id;

    @Column(name = "latest_price", nullable = false)
    private BigDecimal latestPrice;

    @Column(name = "change", nullable = false)
    private BigDecimal change;

    @Column(name = "previous_volume", nullable = false)
    private Integer previousVolume;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;
}
