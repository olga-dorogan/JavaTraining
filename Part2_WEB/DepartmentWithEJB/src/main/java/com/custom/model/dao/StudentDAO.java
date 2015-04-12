package com.custom.model.dao;

import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 24.03.15.
 */
@Local
public interface StudentDAO {
    public Student addToGroup(@NotNull DepartmentGroup group, @NotNull @Valid Student student) throws DAOBusinessException;

    public Student moveToGroup(@NotNull DepartmentGroup group, @NotNull @Valid Student student) throws DAOBusinessException;

    public Student getById(long id);

    public List<Student> getAll(@NotNull DepartmentGroup group);

    public Student update(@NotNull @Valid Student student) throws DAOBusinessException;

    public void delete(@NotNull Student student);
}
