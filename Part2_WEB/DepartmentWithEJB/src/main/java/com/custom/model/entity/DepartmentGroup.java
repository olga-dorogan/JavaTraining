package com.custom.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 15.02.15.
 */
@Entity
@Table(name = "department_group")
public class DepartmentGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "course")
    private int course;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @OneToMany(mappedBy = "departmentGroup", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<Student>();

    public DepartmentGroup() {
    }

    public DepartmentGroup(String name, int course) {
        this.name = name;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Student> getStudents() {
        return students;
    }


    @Override
    public String toString() {
        return "Group: " + name + " [" + course + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentGroup that = (DepartmentGroup) o;

        if (course != that.course) return false;
        if (!name.equals(that.name)) return false;
        if (department == null) return true;
        if (department != that.department) return false;
        if (department != null && !department.equals(that.department)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + course;
        result = 31 * result + department.hashCode();
        return result;
    }
}
