package com.custom;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by olga on 21.04.15.
 */
@Entity
@Table(name = "CHUNK_CSV_DATABASE")
@NamedQueries({
        @NamedQuery(name = "Person.findAll", query = "SELECT c FROM Person c"),
        @NamedQuery(name = "Person.findByName", query = "SELECT c FROM Person c WHERE c.name = :name"),
        @NamedQuery(name = "Person.findByHiredate", query = "SELECT c FROM Person c WHERE c.hiredate = :hiredate") })
public class Person implements Serializable {

    @Id
    private int id;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "hiredate")
    private String hiredate;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String hiredate) {
        this.name = name;
        this.hiredate = hiredate;
    }

    public Person(int id, String name, String hiredate) {
        this.id = id;
        this.name = name;
        this.hiredate = hiredate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        return !((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name)));
    }

    @Override
    public String toString() {
        return name + id;
    }

}

