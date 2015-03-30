package com.custom.model.dao.impl;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import org.apache.commons.lang.Validate;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

/**
 * Created by olga on 20.03.15.
 */
@Stateless
//@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class DepartmentGroupDAOImpl implements DepartmentGroupDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public DepartmentGroup addToDepartment(Department department, DepartmentGroup group) throws DAOBusinessException {
        Validate.notNull(group, "DepartmentGroup must not be null");

        Department departmentFromDb = em.find(Department.class, department.getId());
        if (departmentFromDb == null) {
            throw new DAOBusinessException("Department was not found in the DB", new EntityNotFoundException(""));
        }
        DepartmentGroup groupFromDB = em.find(DepartmentGroup.class, group.getId());
        if (groupFromDB == null && !checkGroupForEqualityWithPresentGroups(departmentFromDb, group)) {
            group.setDepartment(departmentFromDb);
            departmentFromDb.getGroups().add(group);
            em.persist(group);
            return group;
        } else {
            throw new DAOBusinessException("The same departmentGroup already exists", new EntityExistsException(""));
        }
    }


    @Override
    public DepartmentGroup moveToDepartment(Department newDepartment, DepartmentGroup group) throws DAOBusinessException {
        Validate.notNull(newDepartment, "Department must not be null");
        Validate.notNull(group, "DepartmentGroup must not be null");

        if (em.find(Department.class, newDepartment.getId()) == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("Department was not found in the DB"));
        }
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("DepartmentGroup was not found in the DB"));
        }
        group.setDepartment(newDepartment);
        return em.merge(group);
    }

    @Override
    public DepartmentGroup getById(long id) {
        return em.find(DepartmentGroup.class, id);
    }

    @Override
    public List<DepartmentGroup> getAll() {
        return em.createQuery("SELECT g FROM DepartmentGroup g", DepartmentGroup.class).getResultList();
    }

    @Override
    public DepartmentGroup update(DepartmentGroup group) throws DAOBusinessException {
        Validate.notNull(group, "DepartmentGroup must not be null");

        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("DepartmentGroup was not found in the DB"));
        } else if (checkGroupForEqualityWithPresentGroups(group.getDepartment(), group)) {
            throw new DAOBusinessException("", new EntityExistsException("The same department group already exists"));
        }
        return em.merge(group);
    }

    private boolean checkGroupForEqualityWithPresentGroups(Department department, DepartmentGroup group) {
        TypedQuery<DepartmentGroup> query = em.createQuery("SELECT d FROM DepartmentGroup d WHERE d.department.id = ?1",
                DepartmentGroup.class);
        query.setParameter(1, department.getId());
        List<DepartmentGroup> groupList = query.getResultList();
        for (DepartmentGroup groupFromDepartment : groupList) {
            if (group.equals(groupFromDepartment)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(DepartmentGroup group) throws DAOBusinessException {
        Validate.notNull(group, "DepartmentGroup must not be null");
        DepartmentGroup searchedGroup = em.find(DepartmentGroup.class, group.getId());
        if (searchedGroup == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("DepartmentGroup was not found in the DB"));
        }
        em.remove(searchedGroup);
    }
}
