package com.custom;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 30.04.15.
 */
@Entity
public class Unit {
    @Id
    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @OneToMany(mappedBy = "unit", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PartOfUnit> partOfUnits = new ArrayList<>();

    public Unit() {
    }

    public Unit(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (id != null ? !id.equals(unit.id) : unit.id != null) return false;
        return !(name != null ? !name.equals(unit.name) : unit.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public List<PartOfUnit> getPartOfUnits() {
        return partOfUnits;
    }

    public void setPartOfUnits(List<PartOfUnit> partOfUnits) {
        this.partOfUnits = partOfUnits;
    }
}
