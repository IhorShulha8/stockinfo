package com.ihorshulha.asyncapidatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("stock")
public class Stock implements Persistable<Integer>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column("symbol")
    private String symbol;

    @Column("latest_price")
    private BigDecimal latestPrice;

    @Column("delta_price")
    private BigDecimal deltaPrice = BigDecimal.valueOf(0.00);

    @Column("change")
    private BigDecimal change;

    @Column("previous_volume")
    private Integer previousVolume;

    @Column("volume")
    private Integer volume;

    @Column("company_name")
    private String companyName;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!id.equals(stock.id)) return false;
        return symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
