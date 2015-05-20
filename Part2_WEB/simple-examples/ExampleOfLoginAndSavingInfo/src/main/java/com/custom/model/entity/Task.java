package com.custom.model.entity;

import javax.persistence.*;

/**
 * Created by olga on 15.05.15.
 */
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private UserInfo userInfo;

    private Boolean state;


    public Task() {

    }

    public Task(Boolean state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Boolean isState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", state=" + state +
                '}';
    }
}
