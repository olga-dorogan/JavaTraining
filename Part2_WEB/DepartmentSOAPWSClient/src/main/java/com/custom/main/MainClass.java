package com.custom.main;

import com.custom.client.gen.DAOBusinessException_Exception;
import com.custom.client.gen.Student;
import com.custom.client.gen.StudentStore;
import com.custom.client.gen.StudentStoreService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by olga on 17.04.15.
 */
public class MainClass {
    private static StudentStore studentStore;
    public static void main(String[] args) throws IOException, DAOBusinessException_Exception {
        studentStore = new StudentStoreService().getStudentStoreBeanPort();
        String choice;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Choose operation: g[etAll], d[elete], s[ave], u[pdate] or e[xit]: ");
        choice = reader.readLine();
        while (!choice.startsWith("e")) {
            if (choice.startsWith("g")) {
                getAll(reader);
            } else if (choice.startsWith("s")) {
                saveStudent(reader);
            } else if (choice.startsWith("d")) {
                deleteStudent(reader);
            } else if (choice.startsWith("u")) {
                updateStudent(reader);
            }
            System.out.print("Choose next operation: g[etAll], d[elete], s[ave], u[pdate] or e[xit]: ");
            choice = reader.readLine();
        }
    }

    private static void updateStudent(BufferedReader reader) throws IOException, DAOBusinessException_Exception {
        System.out.print("Enter id of student you want to update: ");
        int id = Integer.parseInt(reader.readLine());
        studentStore.updateStudent(id, getStudentInfo(reader));
    }

    private static void deleteStudent(BufferedReader reader) throws IOException {
        System.out.print("Enter students id: ");
        studentStore.deleteStudent(Integer.parseInt(reader.readLine()));
    }

    private static void getAll(BufferedReader reader) throws IOException {
        System.out.print("Enter group: ");
        String group = reader.readLine();
        System.out.print("Enter course: ");
        int course = Integer.parseInt(reader.readLine());
        List<Student> students = studentStore.getAll(group, course);
        System.out.println("Students: ");
        for (Student student : students) {
            printStudentInfo(student);
        }
    }

    private static void saveStudent(BufferedReader reader) throws IOException, DAOBusinessException_Exception {
        studentStore.saveStudent(getStudentInfo(reader));
    }

    private static Student getStudentInfo(BufferedReader reader) throws IOException {
        System.out.println("--- Enter info about student ---");
        Student student = new Student();
        System.out.print("Enter group: ");
        student.setGroup(reader.readLine());
        System.out.print("Enter course: ");
        student.setCourse(Integer.parseInt(reader.readLine()));
        System.out.print("Enter surname: ");
        student.setSurname(reader.readLine());
        System.out.print("Enter name: ");
        student.setName(reader.readLine());
        System.out.print("Enter age: ");
        student.setAge(Integer.parseInt(reader.readLine()));
        return student;
    }

    private static void printStudentInfo(Student student) {
        System.out.println(student.getOrderId() + ". " + student.getSurname() + " " + student.getName() +
                " (sysId = "+student.getSystemId()+")");
    }
}
