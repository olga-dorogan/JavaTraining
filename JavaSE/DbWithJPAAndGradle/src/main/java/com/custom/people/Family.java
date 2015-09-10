package com.custom.people;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15.02.15.
 */
@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToMany(mappedBy = "family")
    private List<Person> members = new ArrayList<Person>();

    public List<Person> getMembers() {
        return this.members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
