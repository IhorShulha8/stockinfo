package com.ihorshulha.asyncapidatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("stock")
public class Stock implements Persistable<String>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("cik")
    private String cik;

    @Column("symbol")
    private String symbol;

    @Column("latest_price")
    private BigDecimal latestPrice;

    @Column("delta_price")
    private BigDecimal deltaPrice;

    @Column("change")
    private BigDecimal change;

    @Column("previous_volume")
    private Integer previousVolume;

    @Column("volume")
    private Integer volume;

    @Column("company_name")
    private String companyName;

    @Override
    public String getId() {
        return this.cik;
    }

    @Override
    public boolean isNew() {
        return !cik.isBlank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!cik.equals(stock.cik)) return false;
        return symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        int result = cik.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
