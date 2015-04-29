package com.custom.service.ws;

import com.custom.model.exception.DAOBusinessException;
import com.custom.service.util.StudentService;
import com.custom.service.util.StudentVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by olga on 16.04.15.
 */
@WebService(endpointInterface = "com.custom.service.ws.StudentStore", serviceName = "StudentStoreService")
@Stateless(name = "soapStudentStoreBean")
public class StudentStoreBean implements StudentStore {
    @EJB
    private StudentService studentService;

    @Override
    public void saveStudent(StudentVO studentVO) throws DAOBusinessException {
        studentService.saveStudent(studentVO);
    }

    @Override
    public List<StudentVO> getAll(String groupDescr, int course) {
        return studentService.getAllStudentsFromGroup(groupDescr, course);
    }

    @Override
    public StudentVO getByOrderNumber(int id, String groupDescr, int course) {
        return studentService.getStudentByOrderNumber(id, groupDescr, course);
    }

    @Override
    public void deleteStudent(int id) {
        studentService.deleteStudent(id);
    }

    @Override
    public void updateStudent(int id, StudentVO studentVO) throws DAOBusinessException {
        studentService.updateStudent(id, studentVO);
    }


}
