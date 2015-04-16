package com.custom.service;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.dao.StudentDAO;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 16.04.15.
 */
@WebService(endpointInterface = "com.custom.service.StudentStore", serviceName = "StudentStoreService")
@Stateless
public class StudentStoreBean implements StudentStore {
    @EJB
    private DepartmentGroupDAO departmentGroupDAO;
    @EJB
    private StudentDAO studentDAO;


    @Override
    public void saveStudent(StudentVO studentVO) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLasName(), studentVO.getAge());
        DepartmentGroup group = departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse());
        studentDAO.addToGroup(group, studentEntity);
    }

    @Override
    public List<StudentVO> getAll(String groupDescr, int course) {
        List<Student> studentEntities = studentDAO.getAllByLastNameOrder(departmentGroupDAO.getByDescrAndCourse(groupDescr, course));
        List<StudentVO> studentVOs = new ArrayList<>(studentEntities.size());
        StudentVO studentVO;
        int i = 0;
        for (Student student : studentEntities) {
            studentVO = new StudentVO();
            studentVO.setGroup(groupDescr);
            studentVO.setCourse(course);
            studentVO.setIdByOrder(++i);
            studentVO.setId((int)student.getId());
            studentVO.setFirstName(student.getFirstName());
            studentVO.setLasName(student.getLastName());
            studentVO.setAge(student.getAge());
            studentVOs.add(studentVO);
        }
        return studentVOs;
    }

    @Override
    public StudentVO getByOrderNumber(int id, String groupDescr, int course) {
        List<Student> studentEntities = studentDAO.getAllByLastNameOrder(departmentGroupDAO.getByDescrAndCourse(groupDescr, course));
        if (studentEntities.size() < id) {
            return null;
        }
        Student student = studentEntities.get(id);
        StudentVO studentVO = new StudentVO();
        studentVO.setGroup(groupDescr);
        studentVO.setCourse(course);
        studentVO.setIdByOrder(id);
        studentVO.setFirstName(student.getFirstName());
        studentVO.setLasName(student.getLastName());
        studentVO.setAge(student.getAge());
        return studentVO;
    }

    @Override
    public void deleteStudent(int id) {
        studentDAO.delete(studentDAO.getById(id-1));
    }

    @Override
    public void updateStudent(int id, StudentVO studentVO) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLasName(), studentVO.getAge());
        studentEntity.setId(id);
        studentDAO.update(studentEntity);
    }


}
