package com.custom.service;

import com.custom.entity.Department;
import com.custom.entity.DepartmentGroup;
import org.apache.commons.lang.Validate;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by olga on 20.02.15.
 */
public class DepartmentGroupService {
    private EntityManager em;

    public DepartmentGroupService(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public DepartmentGroup addToDepartment(Department department, DepartmentGroup group) {
        Validate.notNull(department, "Department must not be null");
        Validate.notNull(group, "DepartmentGroup must not be null");
        group.setDepartment(department);
        DepartmentGroup groupFromDB = em.find(DepartmentGroup.class, group.getId());
        if (groupFromDB == null || !group.equals(groupFromDB)) {
            em.persist(group);
            return group;
        } else {
            throw new EntityExistsException("DepartmentGroup already exists");
        }
    }

    public DepartmentGroup moveToDepartment(Department newDepartment, DepartmentGroup group) {
        Validate.notNull(newDepartment, "Department must not be null");
        Validate.notNull(group, "DepartmentGroup must not be null");
        if (em.find(Department.class, newDepartment.getId()) == null) {
            throw new EntityNotFoundException("Department was not found in the DB");
        }
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new EntityNotFoundException("DepartmentGroup was not found in the DB");
        }
        group.setDepartment(newDepartment);
        return em.merge(group);
    }

    public DepartmentGroup getById(long id) {
        return em.find(DepartmentGroup.class, id);
    }

    public List<DepartmentGroup> getAll() {
        return em.createQuery("SELECT g FROM DepartmentGroup g", DepartmentGroup.class).getResultList();
    }

    public DepartmentGroup update(DepartmentGroup group) {
        Validate.notNull(group, "DepartmentGroup must not be null");
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new EntityNotFoundException("DepartmentGroup was not found in the DB");
        }
        return em.merge(group);
    }

    public void delete(DepartmentGroup group) {
        Validate.notNull(group, "DepartmentGroup must not be null");
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new EntityNotFoundException("DepartmentGroup was not found in the DB");
        }
        em.remove(group);
    }
}
