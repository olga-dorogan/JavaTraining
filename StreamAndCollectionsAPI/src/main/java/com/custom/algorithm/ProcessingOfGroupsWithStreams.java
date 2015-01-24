package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.MilitaryAge;
import com.custom.data.Sex;
import com.custom.data.Student;
import com.custom.utility.ConvertMark;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by olga on 22.01.15.
 */
public class ProcessingOfGroupsWithStreams implements ProcessingOfGroups {
    @Override
    public int getCountOfGroupsWithPoorStudents(Set<Group> department) {
        if (department == null) {
            return 0;
        }
        return (int) department.stream()
                .map(group -> group.getListOfStudents())
                .filter(students ->
                        students.stream()
                                .map(student -> student.getRatingOnSubjects().values())
                                .flatMap(marks -> marks.stream())
                                .filter(mark -> ConvertMark.fromPercentToFourPoint(mark) < 3)
                                .count() > 0)
                .count();
    }

    @Override
    public Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department) {
        if (department == null) {
            return new HashMap<>();
        }
        Map<String, Double> resultMap = new HashMap<>(department.size());
        for (Group group : department) {
            double avg = group.getListOfStudents().stream()
                    .map(st -> st.getRatingOnSubjects().values())
                    .flatMap(m -> m.stream())
                    .mapToInt(x -> x)
                    .average().getAsDouble();
            resultMap.put(group.getName(), avg);
        }
        return resultMap;
    }

    @Override
    public Set<String> getGroupNamesWithOnlyMen(Set<Group> department) {
        if (department == null) {
            return new HashSet<>();
        }
        return department.stream()
                .filter(group -> group.getListOfStudents().stream()
                        .allMatch(student -> student.getSex() == Sex.Male))
                .map(group -> group.getName()).collect(Collectors.toSet());
    }

    @Override
    public List<Student> getAllStudentsFromDepartment(Set<Group> department) {
        return department.stream()
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getAvgRatingForEverySubject(Set<Group> department) {
        if (department == null) {
            return new HashMap<>(0);
        }
        return department.stream()
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream())
                .map(student -> student.getRatingOnSubjects().entrySet())
                .flatMap(entries -> entries.stream())
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey(),
                        Collectors.averagingDouble(entry -> entry.getValue())));
    }

    @Override
    public List<Student> getAllMilitaryAgeStudents(Set<Group> department) {
        if (department == null) {
            return new ArrayList<>(0);
        }
        return department.stream()
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream())
                .filter(student -> student.getSex() == Sex.Male)
                .filter(student -> MilitaryAge.isMilitaryAge(student.getAge()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllSubjects(Set<Group> department) {
        if (department == null) {
            return new HashSet<>(0);
        }
        return department.stream()
                .map(group -> group.getListOfStudents())
                .flatMap(students -> students.stream())
                .map(student -> student.getRatingOnSubjects().keySet())
                .flatMap(subjects -> subjects.stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Group> getAllGroupsWithMoreThenOneSuccessfulStudent(Set<Group> department) {
        if (department == null) {
            return new HashSet<>(0);
        }
        final int thresholdSuccessfulStudents = 2;
        return department.stream()
                .filter(group ->
                        group.getListOfStudents().stream()
                                .filter(student ->
                                        student.getRatingOnSubjects().values().stream()
                                                .allMatch(mark ->
                                                        ConvertMark.fromPercentToFourPoint(mark) == 5))
                                .count() >= thresholdSuccessfulStudents)
                .collect(Collectors.toSet());
    }
}
