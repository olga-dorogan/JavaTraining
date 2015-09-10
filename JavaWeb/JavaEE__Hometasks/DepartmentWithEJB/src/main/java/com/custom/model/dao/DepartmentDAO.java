package com.custom.model.dao;

import com.custom.model.entity.Department;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 18.03.15.
 */
@Local
public interface DepartmentDAO {
    public Department add(@NotNull @Valid Department department) throws DAOBusinessException;
    public Department getById(long id);
    public List<Department> getAll();
    public Department update(@NotNull @Valid Department department) throws DAOBusinessException;
    public void delete(@NotNull Department department) throws DAOBusinessException;
}