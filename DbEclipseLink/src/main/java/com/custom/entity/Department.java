package com.custom.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by olga on 15.02.15.
 */
@Entity
@Table(name = "department")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "description", nullable = false)
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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Department otherDepartment = (Department) obj;
        if (groups == null) {
            return description.equals(otherDepartment.description);
        }
        return description.equals(otherDepartment.description) &&
                groups.equals(otherDepartment.groups);
    }

    @Override
    public String toString() {
        return "Department: " + description + "[" + id + "]";
    }
}
