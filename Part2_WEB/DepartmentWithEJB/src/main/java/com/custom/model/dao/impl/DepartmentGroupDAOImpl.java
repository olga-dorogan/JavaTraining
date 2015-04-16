package com.custom.model.dao.impl;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 20.03.15.
 */
@Stateless
public class DepartmentGroupDAOImpl implements DepartmentGroupDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public DepartmentGroup addToDepartment(@NotNull Department department, @NotNull @Valid DepartmentGroup group) throws DAOBusinessException {
        Department departmentFromDb = em.find(Department.class, department.getId());
        if (departmentFromDb == null) {
            throw new DAOBusinessException("Department was not found in the DB", new EntityNotFoundException(""));
        }
        DepartmentGroup groupFromDB = em.find(DepartmentGroup.class, group.getId());
        if (groupFromDB == null && !checkGroupForEqualityWithPresentGroups(departmentFromDb, group)) {
            group.setDepartment(departmentFromDb);
            em.persist(group);
            em.getEntityManagerFactory().getCache().evict(Department.class, departmentFromDb.getId());
            return group;
        } else {
            throw new DAOBusinessException("The same departmentGroup already exists", new EntityExistsException(""));
        }
    }


    @Override
    public DepartmentGroup moveToDepartment(@NotNull Department newDepartment, @NotNull @Valid DepartmentGroup group) throws DAOBusinessException {
        if (em.find(Department.class, newDepartment.getId()) == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("Department was not found in the DB"));
        }
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("DepartmentGroup was not found in the DB"));
        }
        em.getEntityManagerFactory().getCache().evict(Department.class, newDepartment.getId());
        em.getEntityManagerFactory().getCache().evict(Department.class, group.getDepartment().getId());
        group.setDepartment(newDepartment);
        return em.merge(group);
    }

    @Override
    public DepartmentGroup getById(long id) {
        return em.find(DepartmentGroup.class, id);
    }

    @Override
    public DepartmentGroup getByDescrAndCourse(String descr, int course) {
        List<DepartmentGroup> result = em.createQuery("SELECT g FROM DepartmentGroup g " +
                "WHERE g.name LIKE ?1 AND g.course = ?2", DepartmentGroup.class)
                .setParameter(1, descr).setParameter(2, course).getResultList();
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<DepartmentGroup> getAll() {
        return em.createQuery("SELECT g FROM DepartmentGroup g", DepartmentGroup.class).getResultList();
    }

    @Override
    public DepartmentGroup update(@NotNull @Valid DepartmentGroup group) throws DAOBusinessException {
        if (em.find(DepartmentGroup.class, group.getId()) == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("DepartmentGroup was not found in the DB"));
        } else if (checkGroupForEqualityWithPresentGroups(group.getDepartment(), group)) {
            throw new DAOBusinessException("", new EntityExistsException("The same department group already exists"));
        }
        return em.merge(group);
    }

    @Override
    public void delete(@NotNull DepartmentGroup group) throws DAOBusinessException {
        DepartmentGroup searchedGroup = em.find(DepartmentGroup.class, group.getId());
        if (searchedGroup == null) {
            throw new DAOBusinessException("", new EntityNotFoundException("DepartmentGroup was not found in the DB"));
        }
        em.remove(searchedGroup);
        em.getEntityManagerFactory().getCache().evict(Department.class, searchedGroup.getDepartment().getId());
    }


    private boolean checkGroupForEqualityWithPresentGroups(@NotNull Department department, @NotNull DepartmentGroup group) {
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

}
