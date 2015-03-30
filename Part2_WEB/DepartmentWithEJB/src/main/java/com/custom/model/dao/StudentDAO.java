package com.custom.model.dao;

import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by olga on 24.03.15.
 */
@Local
public interface StudentDAO {
    public Student addToGroup(DepartmentGroup group, Student student) throws DAOBusinessException;

    public Student moveToGroup(DepartmentGroup group, Student student);

    public Student getById(long id);

    public List<Student> getAll(DepartmentGroup group);

    public Student update(Student student);

    public void delete(Student student);
}
