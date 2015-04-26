package com.custom.service;

import com.custom.model.entity.Student;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by olga on 16.04.15.
 */
@XmlRootElement(name = "student")
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
    protected String lastName;
    @XmlElement(name = "age", required = true)
    protected int age;

    public StudentVO() {

    }

    public StudentVO(Student student) {
        this.group = student.getDepartmentGroup().getName();
        this.course = student.getDepartmentGroup().getCourse();
        this.id = (int) student.getId();
        this.idByOrder = 0;
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.age = student.getAge();
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
