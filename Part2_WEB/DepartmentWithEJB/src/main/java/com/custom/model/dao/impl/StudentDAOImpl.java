package com.custom.model.dao.impl;

import com.custom.model.dao.StudentDAO;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;
import com.custom.util.Loggable;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 24.03.15.
 */
@Stateless
@Loggable
public class StudentDAOImpl implements StudentDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Student addToGroup(@NotNull DepartmentGroup group, @NotNull @Valid Student student) throws DAOBusinessException {
        DepartmentGroup groupFromDB = em.find(DepartmentGroup.class, group.getId());
        if (groupFromDB == null) {
            throw new DAOBusinessException("DepartmentGroup was not found in the DB", new EntityNotFoundException(""));
        }
        student.setDepartmentGroup(groupFromDB);
        if (em.find(Student.class, student.getId()) == null) {
            em.persist(student);
            em.getEntityManagerFactory().getCache().evict(DepartmentGroup.class, group.getId());
            return student;
        } else {
            throw new DAOBusinessException("The same student already exists", new EntityExistsException(""));
        }
    }

    @Override
    public Student moveToGroup(@NotNull DepartmentGroup group, @NotNull @Valid Student student) throws DAOBusinessException {
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new DAOBusinessException("DepartmentGroup was not found in the DB", new EntityExistsException(""));
        }
        if (em.find(Student.class, student.getId()) == null) {
            throw new DAOBusinessException("Student was not found in the DB", new EntityExistsException(""));
        }
        em.getEntityManagerFactory().getCache().evict(DepartmentGroup.class, student.getDepartmentGroup().getId());
        em.getEntityManagerFactory().getCache().evict(DepartmentGroup.class, group.getId());
        student.setDepartmentGroup(group);
        return em.merge(student);
    }

    @Override
    public Student getById(long id) throws DAOBusinessException {
        Student student = em.find(Student.class, id);
        if (student == null) {
            throw new DAOBusinessException("Student was not found in the DB", new EntityExistsException(""));
        }
        return student;
    }

    @Override
    public List<Student> getAll(@NotNull DepartmentGroup group) {
        return em.createQuery("SELECT st FROM Student st WHERE st.departmentGroup.id = " + group.getId(),
                Student.class).getResultList();
    }

    @Override
    public List<Student> getAllByLastNameOrder(@NotNull DepartmentGroup group) {
        return em.createQuery("SELECT st FROM Student st WHERE st.departmentGroup.id = " + group.getId() +
                " ORDER BY st.lastName", Student.class).getResultList();
    }

    @Override
    public Student update(@NotNull @Valid Student student) throws DAOBusinessException {
        Student studentFromDB = em.find(Student.class, student.getId());
        if (studentFromDB == null) {
            throw new DAOBusinessException("Student was not found in the DB", new EntityExistsException(("")));
        }
        return em.merge(student);
    }

    @Override
    public void delete(@NotNull Student student) throws DAOBusinessException {
        Student studentFromDb = em.find(Student.class, student.getId());
        if (studentFromDb == null) {
            throw new DAOBusinessException("Student was not found in the DB", new EntityExistsException(""));
        }
        em.getEntityManagerFactory().getCache().evict(DepartmentGroup.class, studentFromDb.getDepartmentGroup().getId());
        em.remove(studentFromDb);
    }
}
