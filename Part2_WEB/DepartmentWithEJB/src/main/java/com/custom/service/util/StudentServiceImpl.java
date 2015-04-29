package com.custom.service.util;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.dao.StudentDAO;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 28.04.15.
 */
@Stateless
public class StudentServiceImpl implements StudentService {

    @EJB
    private StudentDAO studentDAO;
    @EJB
    private DepartmentGroupDAO departmentGroupDAO;

    @Override
    public List<StudentVO> getAllStudentsFromGroup(String groupDescription, int course) throws DAOBusinessException {
        List<Student> studentEntities = studentDAO
                .getAllByLastNameOrder(departmentGroupDAO.getByDescrAndCourse(groupDescription, course));
        List<StudentVO> listStudentVO = new ArrayList<>(studentEntities.size());
        StudentVO studentVO;
        int i = 0;
        for (Student student : studentEntities) {
            studentVO = new StudentVO(student);
            studentVO.setIdByOrder(++i);
            listStudentVO.add(studentVO);
        }
        return listStudentVO;
    }

    @Override
    public StudentVO getStudentById(int id) throws DAOBusinessException {
        return new StudentVO(studentDAO.getById((long) id));
    }

    @Override
    public StudentVO getStudentByOrderNumber(int n, String groupDescription, int course) throws DAOBusinessException {
        List<Student> studentEntities = studentDAO.getAllByLastNameOrder(
                departmentGroupDAO.getByDescrAndCourse(groupDescription, course));
        if (studentEntities.size() < n) {
            return null;
        }
        Student student = studentEntities.get(n);
        StudentVO studentVO = new StudentVO(student);
        studentVO.setIdByOrder(n);
        return studentVO;
    }

    @Override
    public StudentVO saveStudent(StudentVO studentVO) throws DAOBusinessException{
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLastName(), studentVO.getAge());
        DepartmentGroup group = departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse());
        Student createdStudentEntity = studentDAO.addToGroup(group, studentEntity);
        return new StudentVO(createdStudentEntity);
    }

    @Override
    public StudentVO updateStudent(int id, StudentVO studentVO) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLastName(), studentVO.getAge());
        studentEntity.setDepartmentGroup(departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse()));
        studentEntity.setId(id);
        Student updatedStudentEntity = studentDAO.update(studentEntity);
        return new StudentVO(updatedStudentEntity);
    }

    @Override
    public void deleteStudent(int id) throws DAOBusinessException {
        studentDAO.delete(studentDAO.getById(id));
    }
}
