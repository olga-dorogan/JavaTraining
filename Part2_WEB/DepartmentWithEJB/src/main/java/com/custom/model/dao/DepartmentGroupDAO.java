package com.custom.model.dao;

import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 20.03.15.
 */
@Local
public interface DepartmentGroupDAO {

    DepartmentGroup addToDepartment(@NotNull Department department, @NotNull @Valid DepartmentGroup group) throws DAOBusinessException;

    public DepartmentGroup moveToDepartment(@NotNull Department newDepartment, @NotNull @Valid DepartmentGroup group) throws DAOBusinessException;

    public DepartmentGroup getById(long id);

    public DepartmentGroup getByDescrAndCourse(String descr, int course);

    public List<DepartmentGroup> getAll();

    public DepartmentGroup update(@NotNull @Valid DepartmentGroup group) throws DAOBusinessException;

    public void delete(@NotNull DepartmentGroup group) throws DAOBusinessException;
}
