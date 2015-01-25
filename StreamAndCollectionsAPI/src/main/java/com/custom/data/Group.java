package com.custom.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 22.01.15.
 */
public class Group {
    private String name;
    private int course;
    List<Student> listOfStudents;

    public Group(String name, int course, List<Student> listOfStudents) {
        this.name = name;
        this.course = course;
        if (listOfStudents != null) {
            this.listOfStudents = new ArrayList<Student>(listOfStudents);
        } else {
            this.listOfStudents = new ArrayList<Student>();
        }
    }

    public Group(String name, int course) {
        this(name, course, null);
    }

    public Group(String name) {
        this(name, 0, null);
    }

    public Group() {
        this("", 0, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Group other = (Group) obj;
        return this.name.equals(other.name) && (this.course == other.course) &&
                this.listOfStudents.equals(other.listOfStudents);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + ((Integer) course).hashCode() + listOfStudents.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nGroup: ").append(name).append("\t, course: ").append(course).append("\t, list of students:\n\t")
                .append(listOfStudents);
        return sb.toString();
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

    public List<Student> getListOfStudents() {
        List<Student> copyOfListOfStudents = new ArrayList<Student>(listOfStudents);
        return copyOfListOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = new ArrayList<Student>(listOfStudents);
    }

    public void addStudent(Student student) {
        listOfStudents.add(student);
    }

    public void removeStudent(Student student) {
        listOfStudents.remove(student);
    }
}
