package com.custom.people;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15.02.15.
 */
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    private Family family;
    private List<Job> jobList = new ArrayList<Job>();

    @ManyToOne
    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;

    }

    @OneToMany
    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
