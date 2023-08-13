package com.ihorshulha.asyncapidatamanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.domain.Persistable;
import reactor.util.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock implements Persistable<Integer>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String symbol;

    private BigDecimal latestPrice;

    private BigDecimal change;

    private Integer previousVolume;

    private Integer volume;

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
