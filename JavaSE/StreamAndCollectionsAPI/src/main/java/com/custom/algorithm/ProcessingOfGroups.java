package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by olga on 22.01.15.
 */

public interface ProcessingOfGroups {
    int getCountOfGroupsWithPoorStudents(Set<Group> department);

    Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department);

    Set<String> getGroupNamesWithOnlyMen(Set<Group> department);

    List<Student> getAllStudentsFromDepartment(Set<Group> department);

    Map<String, Double> getAvgRatingForEverySubject(Set<Group> department);

    List<Student> getAllMilitaryAgeStudents(Set<Group> department);

    Set<String> getAllSubjects(Set<Group> department);

    Set<String> getAllGroupsWithMoreThenOneSuccessfulStudent(Set<Group> department);

    default boolean validateDepartment(Set<Group> department) {
        return (department != null);
    }

    default boolean validateGroup(Group group) {
        return (group != null && group.getListOfStudents() != null);
    }

    default boolean validateStudent(Student student) {
        return (student != null && student.getRatingOnSubjects() != null);
    }

    default boolean validateStudentWithoutRating(Student student) {
        return (student != null);
    }
}
