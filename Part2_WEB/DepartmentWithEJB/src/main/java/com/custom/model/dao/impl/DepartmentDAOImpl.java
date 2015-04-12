package com.custom.model.dao.impl;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.entity.Department;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 18.03.15.
 */
@Stateless
public class DepartmentDAOImpl implements DepartmentDAO {
    private static final String CONSTRAINT_DEPT_MSG = "Department must not be null";

    @PersistenceContext(unitName = "departmentDB")
    private EntityManager em;

    @Override
    public Department add(@NotNull @Valid Department department) throws DAOBusinessException {
        Department departmentFromDB = em.find(Department.class, department.getId());
        TypedQuery<String> queryGetDepartmentByDescr = em.createNamedQuery("getDepartmentByDescription", String.class);
        queryGetDepartmentByDescr.setParameter("description", department.getDescription());
        List<String> equalDepartments = queryGetDepartmentByDescr.getResultList();
        if (departmentFromDB == null && equalDepartments.isEmpty()) {
            em.persist(department);
            return department;
        } else {
            throw new DAOBusinessException("Department already exists", new EntityExistsException());
        }
    }


    @Override
    public Department getById(long id) {
        Department department = em.find(Department.class, id);
        return department;
    }

    @Override
    public List<Department> getAll() {
        return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    @Override
    public Department update(@NotNull @Valid Department department) throws DAOBusinessException {
        TypedQuery<Department> queryGetDepartmentByDescr = em.createNamedQuery("getDepartmentByDescription",
                Department.class);
        queryGetDepartmentByDescr.setParameter("description", department.getDescription());
        List<Department> departmentResultSet = queryGetDepartmentByDescr.getResultList();
        Department oldDepartment = em.find(Department.class, department.getId());
        if (oldDepartment == null) {
            throw new DAOBusinessException("Department was not found in the DB", new EntityNotFoundException(""));
        } else if (!departmentResultSet.isEmpty()) {
            throw new DAOBusinessException("Department with description '" + oldDepartment.getDescription() +
                    "' already exists.");
        }
        return em.merge(department);
    }

    @Override
    public void delete(@NotNull Department department) throws DAOBusinessException {
        Department searchedDepartment = em.find(Department.class, department.getId());
        if (searchedDepartment == null) {
            throw new DAOBusinessException("Department was not found in the DB", new EntityNotFoundException(""));
        }
        em.remove(searchedDepartment);
    }

}
