package com.ihorshulha.asyncapidatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.io.Serial;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company implements Persistable<String>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("cik")
    private String cik;

    @Column("symbol")
    private String symbol;

//    @Column("is_enabled")
//    private Boolean isEnabled;

    @Override
    public String getId() {
        return this.cik;
    }

    @Override
    public boolean isNew() {
        return !cik.isBlank();
    }

//    @Transient
//    private boolean newSymbol;

//    @Transient
//    public Company setAsNew() {
//        this.newSymbol = true;
//        return this;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!cik.equals(company.cik)) return false;
        return symbol.equals(company.symbol);
    }

    @Override
    public int hashCode() {
        int result = cik.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
