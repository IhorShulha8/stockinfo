package com.ihorshulha.asyncapidatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("stock")
public class Stock implements Persistable<Long>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

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
    public Long getId() {
        return id;
    }

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!Objects.equals(id, stock.id)) return false;
        return symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
