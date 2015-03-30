package com.custom.model.dao;

import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;

import java.util.List;

/**
 * Created by olga on 20.03.15.
 */
public interface DepartmentGroupDAO {

    DepartmentGroup addToDepartment(Department department, DepartmentGroup group) throws DAOBusinessException;

    public DepartmentGroup moveToDepartment(Department newDepartment, DepartmentGroup group) throws DAOBusinessException;

    public DepartmentGroup getById(long id);

    public List<DepartmentGroup> getAll();

    public DepartmentGroup update(DepartmentGroup group) throws DAOBusinessException;

    public void delete(DepartmentGroup group) throws DAOBusinessException;
}
