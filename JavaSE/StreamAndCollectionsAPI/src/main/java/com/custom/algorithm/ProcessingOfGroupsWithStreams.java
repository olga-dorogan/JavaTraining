package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.MilitaryAge;
import com.custom.data.Sex;
import com.custom.data.Student;
import com.custom.utility.ConvertMark;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by olga on 22.01.15.
 */
public class ProcessingOfGroupsWithStreams implements ProcessingOfGroups {
    @Override
    public int getCountOfGroupsWithPoorStudents(Set<Group> department) {
        if (!validateDepartment(department)) {
            return 0;
        }
        return (int) department.stream()
                .filter(group -> validateGroup(group))
                .filter(group -> group.getListOfStudents().stream()
                        .filter(student -> validateStudent(student))
                        .map(student -> student.getRatingOnSubjects().values()).flatMap(marks -> marks.stream())
                        .filter(mark -> ConvertMark.fromPercentToFourPoint(mark) < 3)
                        .count() > 0)
                .count();
    }

    @Override
    public Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashMap<>();
        }
        Function<Group, Double> funcToGetAvgProgress = group -> group.getListOfStudents().stream()
                .filter(student -> validateStudent(student))
                .map(student -> student.getRatingOnSubjects().values()).flatMap(values -> values.stream())
                .mapToInt(mark -> mark).average().getAsDouble();

        return department.stream().filter(group -> validateGroup(group))
                .collect(Collectors.toMap(group -> group.getDescription(),
                        funcToGetAvgProgress, (mark1, mark2) -> (mark1 + mark2) / 2));
    }

    @Override
    public Set<String> getGroupNamesWithOnlyMen(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashSet<>();
        }
        return department.stream().filter(group -> validateGroup(group))
                .filter(group -> group.getListOfStudents().stream()
                        .filter(student -> validateStudentWithoutRating(student))
                        .allMatch(student -> student.getSex() == Sex.MALE))
                .map(group -> group.getDescription()).collect(Collectors.toSet());
    }

    @Override
    public List<Student> getAllStudentsFromDepartment(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new ArrayList<>();
        }
        return department.stream().filter(group -> validateGroup(group))
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getAvgRatingForEverySubject(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashMap<>();
        }
        return department.stream().filter(group -> validateGroup(group))
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream()).filter(student -> validateStudent(student))
                .map(student -> student.getRatingOnSubjects().entrySet()).flatMap(entries -> entries.stream())
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey(),
                        Collectors.averagingDouble(entry -> entry.getValue())));
    }

    @Override
    public List<Student> getAllMilitaryAgeStudents(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new ArrayList<>(0);
        }
        return department.stream().filter(group -> validateGroup(group))
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream()).filter(student -> validateStudentWithoutRating(student))
                .filter(student -> student.getSex() == Sex.MALE)
                .filter(student -> MilitaryAge.isMilitaryAge(student.getAge()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllSubjects(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashSet<>();
        }
        return department.stream().filter(group -> validateGroup(group))
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream()).filter(student -> validateStudent(student))
                .map(student -> student.getRatingOnSubjects().keySet())
                .flatMap(subjects -> subjects.stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllGroupsWithMoreThenOneSuccessfulStudent(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashSet<>();
        }
        final int thresholdSuccessfulStudents = 2;
        return department.stream().filter(group -> validateGroup(group))
                .filter(group -> group.getListOfStudents().stream().filter(student -> validateStudent(student))
                        .filter(student -> student.getRatingOnSubjects().values().stream()
                                .allMatch(mark -> ConvertMark.fromPercentToFourPoint(mark) == 5))
                        .count() >= thresholdSuccessfulStudents)
                .map(group -> group.getName())
                .collect(Collectors.toSet());
    }
}
