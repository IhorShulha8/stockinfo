package com.ihorshulha.asyncapidatamanager.entity;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company implements Persistable<Integer>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Integer id;

    @Column("symbol")
    private String symbol;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!id.equals(company.id)) return false;
        return symbol.equals(company.symbol);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
