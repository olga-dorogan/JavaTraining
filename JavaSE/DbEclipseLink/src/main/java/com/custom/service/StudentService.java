package com.custom.service;

import com.custom.entity.DepartmentGroup;
import com.custom.entity.Student;
import org.apache.commons.lang.Validate;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by olga on 20.02.15.
 */
public class StudentService {
    private EntityManager em;

    public StudentService(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Student addToGroup(DepartmentGroup group, Student student) {
        Validate.notNull(group, "DepartmentGroup must not be null");
        Validate.notNull(student, "Student must not be null");
        student.setDepartmentGroup(group);
        if (em.find(Student.class, student.getId()) == null) {
            em.persist(student);
            return student;
        } else {
            throw new EntityExistsException("Student already exists");
        }
    }

    public Student moveToGroup(DepartmentGroup group, Student student) {
        Validate.notNull(group, "DepartmentGroup must not be null");
        Validate.notNull(student, "Student must not be null");
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new EntityExistsException("DepartmentGroup was not found in the DB");
        }
        if (em.find(Student.class, student.getId()) == null) {
            throw new EntityExistsException("Student was not found in the DB");
        }
        student.setDepartmentGroup(group);
        student = em.merge(student);
        return student;
    }

    public Student getById(long id) {
        return em.find(Student.class, id);
    }


    public List<Student> getAll() {
        return em.createQuery("SELECT g FROM Student g", Student.class).getResultList();
    }

    public Student update(Student student) {
        Validate.notNull(student, "Student must not be null");
        if (em.find(Student.class, student.getId()) == null) {
            throw new EntityExistsException("Student was not found in the DB");
        }
        return em.merge(student);
    }

    public void delete(Student student) {
        Validate.notNull(student, "Student must not be null");
        if (em.find(Student.class, student.getId()) == null) {
            throw new EntityExistsException("Student was not found in the DB");
        }
        em.remove(student);
    }
}
