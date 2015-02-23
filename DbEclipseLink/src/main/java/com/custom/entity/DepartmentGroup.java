package com.custom.entity;

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
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "course", nullable = false)
    private int course;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "department_id", nullable = false)
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
        DepartmentGroup otherGroup = (DepartmentGroup) obj;
        if (this.students == null && otherGroup.students != null) {
            return false;
        }
        boolean isEqualsIgnoreDepartmentAndStudents = name.equals(otherGroup.name) && (course==otherGroup.course);
        if (this.students == null) {
            if (this.department == null) {
                return isEqualsIgnoreDepartmentAndStudents;
            } else {
                return isEqualsIgnoreDepartmentAndStudents && department.equals(otherGroup.department);
            }
        } else {
            boolean isEqualIgnoreDepartment = isEqualsIgnoreDepartmentAndStudents && students.equals(otherGroup.students);
            if (this.department == null) {
                return isEqualIgnoreDepartment;
            } else {
                return isEqualIgnoreDepartment && department.equals(otherGroup.department);
            }
        }
    }

    @Override
    public String toString() {
        return "Group: " + name + " [" + course + "]";
    }
}
