package com.custom.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olga on 22.01.15.
 */
public class Student {
    private String name;
    private Sex sex;
    private int age;
    private Map<String, Integer> ratingOnSubjects;

    public Student(String name, Sex sex, int age, Map<String, Integer> ratingOnSubjects) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        if (ratingOnSubjects != null) {
            this.ratingOnSubjects = new HashMap<>(ratingOnSubjects);
        } else {
            this.ratingOnSubjects = new HashMap<>();
        }
    }

    public Student(String name, Sex sex, int age) {
        this(name, sex, age, null);
    }

    public Student() {
        this("", Sex.Female, 0, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Integer> getRatingOnSubjects() {
        Map<String, Integer> copyOfRatingOnSubjects = new HashMap<String, Integer>(ratingOnSubjects);
        return copyOfRatingOnSubjects;
    }

    public void setRatingOnSubjects(Map<String, Integer> ratingOnSubjects) {
        this.ratingOnSubjects = new HashMap<>(ratingOnSubjects);
    }

    public void addRatingOnSubject(String subject, Integer rating) {
        ratingOnSubjects.put(subject, rating);
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
        Student other = (Student) obj;
        return this.name.equals(other.name) && (this.age == other.age) && (this.sex == other.sex);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + ((Integer) age).hashCode() + sex.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nName: ").append(name).append("\t, sex: ").append(sex).append("\t, age: ").append(age)
                .append("\n\tRating: ").append(ratingOnSubjects);
        return sb.toString();
    }
}
