package com.custom.service;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by olga on 16.04.15.
 */
@XmlType(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentVO implements Serializable{
    @XmlElement(name = "systemId", required = false)
    protected int id;
    @XmlElement(name = "orderId", required = false)
    protected int idByOrder;
    @XmlElement(name = "group", required = true)
    protected String group;
    @XmlElement(name = "course", required = true)
    protected int course;
    @XmlElement(name = "name", required = true)
    protected String firstName;
    @XmlElement(name = "surname", required = true)
    protected String lasName;
    @XmlElement(name = "age", required = true)
    protected int age;

    public int getIdByOrder() {
        return idByOrder;
    }

    public void setIdByOrder(int idByOrder) {
        this.idByOrder = idByOrder;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasName() {
        return lasName;
    }

    public void setLasName(String lasName) {
        this.lasName = lasName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
