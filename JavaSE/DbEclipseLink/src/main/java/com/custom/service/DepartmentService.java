package com.custom.service;

import com.custom.entity.Department;
import org.apache.commons.lang.Validate;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by olga on 20.02.15.
 */
public class DepartmentService {
    private EntityManager em;

    public DepartmentService(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Department add(Department department) {
        Validate.notNull(department, "Department must not be null");
        Department departmentFromDB = em.find(Department.class, department.getId());
        if (departmentFromDB == null || !department.equals(departmentFromDB)) {
            em.persist(department);
            return department;
        } else {
            throw new EntityExistsException("Department already exists");
        }
    }

    public Department getById(long id) {
        return em.find(Department.class, id);
    }


    public List<Department> getAll() {
        return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    public Department update(Department department) {
        Validate.notNull(department, "Department must not be null");
        if (em.find(Department.class, department.getId()) == null) {
            throw new EntityNotFoundException("Department was not found in the DB");
        }
        return em.merge(department);
    }

    public void delete(Department department) {
        Validate.notNull(department, "Department must not be null");
        if (em.find(Department.class, department.getId()) == null) {
            throw new EntityNotFoundException("Department was not found in the DB");
        }
        em.remove(department);
    }
}
