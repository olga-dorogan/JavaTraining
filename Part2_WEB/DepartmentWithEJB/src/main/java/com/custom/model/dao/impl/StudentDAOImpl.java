package com.custom.model.dao.impl;

import com.custom.model.dao.StudentDAO;
import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import org.apache.commons.lang.Validate;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by olga on 24.03.15.
 */
@Stateless
public class StudentDAOImpl implements StudentDAO {
    @PersistenceContext(unitName = "departmentDB")
    private EntityManager em;

    @Override
    public Student addToGroup(DepartmentGroup group, Student student) throws DAOBusinessException {
        Validate.notNull(group, "DepartmentGroup must not be null");
        Validate.notNull(student, "Student must not be null");
        DepartmentGroup groupFromDB = em.find(DepartmentGroup.class, group.getId());
        if (groupFromDB == null) {
            throw new DAOBusinessException("DepartmentGroup was not found in the DB", new EntityNotFoundException(""));
        }
        student.setDepartmentGroup(groupFromDB);
        if (em.find(Student.class, student.getId()) == null) {
            em.persist(student);
            group.getStudents().add(student);
            return student;
        } else {
            throw new DAOBusinessException("The same student already exists", new EntityExistsException(""));
        }
    }

    @Override
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

    @Override
    public Student getById(long id) {
        return em.find(Student.class, id);
    }

    @Override
    public List<Student> getAll(DepartmentGroup group) {
        return em.createQuery("SELECT st FROM Student st WHERE st.departmentGroup.id = " + group.getId(),
                Student.class).getResultList();
    }

    @Override
    public Student update(Student student) {
        Validate.notNull(student, "Student must not be null");
        if (em.find(Student.class, student.getId()) == null) {
            throw new EntityExistsException("Student was not found in the DB");
        }
        return em.merge(student);
    }

    @Override
    public void delete(Student student) {
        Validate.notNull(student, "Student must not be null");
        Student studentFromDb = em.find(Student.class, student.getId());
        if (studentFromDb == null) {
            throw new EntityExistsException("Student was not found in the DB");
        }
        em.remove(studentFromDb);
    }
}
