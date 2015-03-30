package com.custom.model.dao;

import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.Department;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by olga on 18.03.15.
 */
@Local
public interface DepartmentDAO {
    public Department add(Department department) throws DAOBusinessException;
    public Department getById(long id);
    public List<Department> getAll();
    public Department update(Department department) throws DAOBusinessException;
    public void delete(Department department) throws DAOBusinessException;

    public Department refresh(Department department);
}