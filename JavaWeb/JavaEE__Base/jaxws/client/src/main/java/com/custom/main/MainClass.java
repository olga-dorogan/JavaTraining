package com.custom.main;

import com.custom.client.gen.Student;
import com.custom.client.gen.StudentStore;
import com.custom.client.gen.StudentStoreImplService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by olga on 16.04.15.
 */
public class MainClass {

    public static void main(String[] args) {
        StudentStore studentStore = new StudentStoreImplService().getStudentStoreImplPort();
        String greeting = studentStore.welcomeMessage("olga");
        System.out.println(greeting);
        try {
            /// ---- add students
            Student student;
            String answer;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Do you want add student? [y/n]: ");
            answer = reader.readLine();
            while (answer.startsWith("y")) {
                student = new Student();
                System.out.print("Enter name: ");
                student.setFirstName(reader.readLine());
                System.out.print("Enter surname: ");
                student.setLastName(reader.readLine());
                System.out.print("Enter age: ");
                student.setAge(Integer.parseInt(reader.readLine()));
                System.out.print("Do you want add another student? [y/n]: ");
                answer = reader.readLine();
                studentStore.saveStudent(student);
            }

            /// ---- find all students with the specified age
            System.out.print("Enter filter age: ");
            int age = Integer.parseInt(reader.readLine());
            System.out.println(studentStore.findSurnames(age));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
