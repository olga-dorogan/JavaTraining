package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.Student;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by olga on 22.01.15.
 */
// TODO: test  'getAvgRatingForEverySubject()' for both realizations
// TODO: test  'getAllMilitaryAgeStudents()' for both realizations
// TODO: test  'getAllSubjects()' for both realizations
// TODO: test  'getAllGroupsWithMoreThenOneSuccessfulStudent()' for both realizations
public interface ProcessingOfGroups {
    int getCountOfGroupsWithPoorStudents(@NotNull Set<Group> department);

    Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department);

    Set<String> getGroupNamesWithOnlyMen(Set<Group> department);

    List<Student> getAllStudentsFromDepartment(Set<Group> department);

    Map<String, Double> getAvgRatingForEverySubject(Set<Group> department);

    List<Student> getAllMilitaryAgeStudents(Set<Group> department);

    Set<String> getAllSubjects(Set<Group> department);

    Set<Group> getAllGroupsWithMoreThenOneSuccessfulStudent(Set<Group> department);
}
