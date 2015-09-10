package com.custom;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by olga on 30.04.15.
 */
@Entity
public class PartOfUnit {
    @Id
    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @ManyToOne
    private Unit unit;

    public PartOfUnit() {

    }

    public PartOfUnit(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public PartOfUnit(Integer id, String name, Unit unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartOfUnit that = (PartOfUnit) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(unit != null ? !unit.equals(that.unit) : that.unit != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PartOfUnit{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
