package com.custom.service.util;

import com.custom.model.exception.DAOBusinessException;

import java.util.List;

/**
 * Created by olga on 28.04.15.
 */
public interface StudentService {
    List<StudentVO> getAllStudentsFromGroup(String groupDescription, int course) throws DAOBusinessException;
    StudentVO getStudentById(int id) throws DAOBusinessException;
    StudentVO getStudentByOrderNumber(int n, String groupDescription, int course) throws DAOBusinessException;
    StudentVO saveStudent(StudentVO studentVO) throws DAOBusinessException;
    StudentVO updateStudent(int id, StudentVO studentVO) throws DAOBusinessException;
    void deleteStudent(int id) throws DAOBusinessException;
}
