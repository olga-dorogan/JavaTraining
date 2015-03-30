package com.custom.model.dao.impl;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.Department;
import org.apache.commons.lang.Validate;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

/**
 * Created by olga on 18.03.15.
 */
@Stateless
//@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class DepartmentDAOImpl implements DepartmentDAO {
    @PersistenceContext(unitName = "departmentDB")
    private EntityManager em;

    @Override
    public Department add(Department department) throws DAOBusinessException {
        Validate.notNull(department, "Department must not be null");

        Department departmentFromDB = em.find(Department.class, department.getId());
        TypedQuery<Department> queryGetDepartmentByDescr = em.createNamedQuery("getDepartmentByDescription",
                Department.class);
        queryGetDepartmentByDescr.setParameter("description", department.getDescription());
        List<Department> equalDepartments = queryGetDepartmentByDescr.getResultList();
        if (departmentFromDB == null && equalDepartments.isEmpty()) {
            em.persist(department);
            return department;
        } else {
            throw new DAOBusinessException("", new EntityExistsException("Department already exists"));
        }
    }


    @Override
    public Department getById(long id) {
        Department department = em.find(Department.class, id);
        if (department != null) {
            em.refresh(department);
        }
        return department;
    }

    @Override
    public List<Department> getAll() {
        return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    @Override
    public Department update(Department department) throws DAOBusinessException {
        Validate.notNull(department, "Department must not be null");

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
    public void delete(Department department) throws DAOBusinessException {
        Validate.notNull(department, "Department must not be null");
        Department searchedDepartment = em.find(Department.class, department.getId());
        if (searchedDepartment == null) {
            throw new DAOBusinessException("Department was not found in the DB", new EntityNotFoundException(""));
        }
        em.remove(searchedDepartment);
    }

    @Override
    public Department refresh(Department department) {
        em.refresh(department);
        return department;
    }
}
