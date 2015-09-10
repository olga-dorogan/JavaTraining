package com.custom.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by olga on 15.02.15.
 */
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
    @ManyToOne
    private DepartmentGroup departmentGroup;

    public Student() {
    }

    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public DepartmentGroup getDepartmentGroup() {
        return departmentGroup;
    }

    public void setDepartmentGroup(DepartmentGroup departmentGroup) {
        this.departmentGroup = departmentGroup;
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
        Student otherStudent = (Student) obj;
        boolean isEqualIgnoreDepartment = (this.age == otherStudent.age) &&
                this.firstName.equals(otherStudent.firstName) && this.lastName.equals(otherStudent.lastName);
        if (this.departmentGroup == null) {
            return isEqualIgnoreDepartment;
        } else {
            return this.departmentGroup.equals(otherStudent.departmentGroup) && isEqualIgnoreDepartment;
        }
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (departmentGroup != null ? departmentGroup.hashCode() : 0);
        return result;
    }
}
