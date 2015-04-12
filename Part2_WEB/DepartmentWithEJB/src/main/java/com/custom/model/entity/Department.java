package com.custom.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by olga on 15.02.15.
 */
@Entity
@Table(name = "department")
@NamedQuery(name = "getDepartmentByDescription", query = "SELECT d.description FROM Department d " +
        " WHERE d.description LIKE :description")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @NotNull
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<DepartmentGroup> groups;

    public Department() {

    }

    public Department(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DepartmentGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<DepartmentGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Department: " + description + "[" + id + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

}
