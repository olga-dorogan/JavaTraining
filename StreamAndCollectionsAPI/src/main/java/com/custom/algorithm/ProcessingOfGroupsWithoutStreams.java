package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.Sex;
import com.custom.data.Student;
import com.custom.utility.ConvertMark;

import java.util.*;

/**
 * Created by olga on 22.01.15.
 */
public class ProcessingOfGroupsWithoutStreams implements ProcessingOfGroups {
    @Override
    public int getCountOfGroupsWithPoorStudents(Set<Group> department) {
        if (department == null) {
            return 0;
        }
        int result = 0;
        for (Group group : department) {
            if (isAnyOfStudentsPoor(group.getListOfStudents())) {
                result++;
            }
        }
        return result;
    }

    private boolean isAnyOfStudentsPoor(List<Student> listOfStudents) {
        for (Student student : listOfStudents) {
            Collection<Integer> rating = student.getRatingOnSubjects().values();
            for (Integer mark : rating) {
                if (ConvertMark.fromPercentToFourPoint(mark) < 3) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department) {
        if (department == null) {
            return new HashMap<>();
        }
        Map<String, Double> progressOfGroup = new HashMap<>(department.size());
        double sum;
        int count;
        for (Group group : department) {
            sum = 0;
            count = 0;
            List<Student> listOfStudents = group.getListOfStudents();
            for (Student student : listOfStudents) {
                Collection<Integer> rating = student.getRatingOnSubjects().values();
                for (Integer mark : rating) {
                    sum += mark;
                    count++;
                }
            }
            progressOfGroup.put(group.getName(), sum / count);
        }
        return progressOfGroup;
    }

    @Override
    public Set<String> getGroupNamesWithOnlyMen(Set<Group> department) {
        if (department == null) {
            return new HashSet<>();
        }
        Set<String> groupsWithoutWomen = new HashSet<>();
        for (Group group : department) {
            if (isAllStudentsMen(group.getListOfStudents())) {
                groupsWithoutWomen.add(group.getName());
            }
        }
        return groupsWithoutWomen;
    }

    @Override
    public List<Student> getAllStudentsFromDepartment(Set<Group> department) {
        List<Student> listOfAllStudentsFromDepartment = new ArrayList<>();
        for (Group group : department) {
            listOfAllStudentsFromDepartment.addAll(group.getListOfStudents());
        }
        return listOfAllStudentsFromDepartment;
    }

    private boolean isAllStudentsMen(List<Student> listOfStudents) {
        for (Student student : listOfStudents) {
            if (student.getSex() == Sex.Female)
                return false;
        }
        return true;
    }


}
