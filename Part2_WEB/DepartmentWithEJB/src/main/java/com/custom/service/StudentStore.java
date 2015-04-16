package com.custom.service;

import com.custom.model.exception.DAOBusinessException;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by olga on 16.04.15.
 */
@WebService
@Local
public interface StudentStore {
    @WebMethod
    public void saveStudent(@XmlElement(required = true) @WebParam(name = "student") StudentVO student) throws DAOBusinessException;

    @WebMethod
    List<StudentVO> getAll(@WebParam(name = "group") String groupDescr,
                           @WebParam(name = "course") int course);

    @WebMethod
    StudentVO getByOrderNumber(@WebParam(name = "id") int id,
                               @WebParam(name = "group") String groupDescr,
                               @WebParam(name = "course") int course);

    @WebMethod
    void deleteStudent(@WebParam(name = "studentId") int id);

    @WebMethod
    void updateStudent(@WebParam(name = "studentId") int id,
                       @XmlElement(required = true) @WebParam(name = "updatedInfo") StudentVO studentVO) throws DAOBusinessException;
}
