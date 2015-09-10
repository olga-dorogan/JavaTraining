package com.custom;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by olga on 15.04.15.
 */
@WebService
public interface StudentStore {
    @WebMethod
    public String welcomeMessage(String name);

    @WebMethod
    public List<String> findSurnames(int age);

    @WebMethod
    public void saveStudent(Student student);
}
