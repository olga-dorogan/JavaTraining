package com.custom;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 15.04.15.
 */
@WebService(endpointInterface = "com.custom.StudentStore", serviceName = "StudentStoreImplService")
public class StudentStoreImpl implements StudentStore {
    private List<Student> students = new ArrayList<Student>();

    public String welcomeMessage(String name) {
        return "hello, " + name;
    }

    public List<String> findSurnames(int age) {
        List<String> resultList = new ArrayList<String>();
        for (Student student : students) {
            if (student.getAge() == age) {
                resultList.add(student.getLastName());
            }
        }
        return resultList;
    }

    public void saveStudent(Student student) {
        students.add(student);
    }
}
