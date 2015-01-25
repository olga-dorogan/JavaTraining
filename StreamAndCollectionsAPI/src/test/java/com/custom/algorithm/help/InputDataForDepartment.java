package com.custom.algorithm.help;

import com.custom.data.MilitaryAge;
import com.custom.data.Sex;
import com.custom.data.Student;

import java.util.*;

/**
 * Created by olga on 25.01.15.
 */
public class InputDataForDepartment {

    public static Map<String, Integer> getRating(int nSubjects, int mark) {
        Map<String, Integer> retMap = new HashMap<>(nSubjects);
        for (int i = 0; i < nSubjects; i++) {
            retMap.put("Subject " + i, mark);
        }
        return retMap;
    }

    public static Map<String, Double> getAvgRatingsForGroups(int nGroups) {
        final Map<String, Double> result = new HashMap<>(nGroups);
        for (int i = 0; i < nGroups; i++) {
            result.put("Group " + i, 100.0 - i);
        }
        return result;
    }

    public static Set<String> getNamesForOnlyMenGroups(int nGroups) {
       Set<String> namesOfOnlMenGroups = new HashSet<>(nGroups);
        for (int i = 0; i < nGroups; i++) {
            namesOfOnlMenGroups.add("Group with only men " + i);
        }
        return namesOfOnlMenGroups;
    }

    public static Set<String> getNamesForGroupsWithSuccessfulStudents(int nGroups) {
        Set<String> namesOfOnlMenGroups = new HashSet<>(nGroups);
        for (int i = 0; i < nGroups; i++) {
            namesOfOnlMenGroups.add("Group with successful students " + i);
        }
        return namesOfOnlMenGroups;
    }

    public static List<Student> getListWithAllStudents(int nStudentsInGroup, int nGroups) {
        List<Student> listOfStudents = new ArrayList<>(nStudentsInGroup * nGroups);
        for (int iGroup = 0; iGroup < nGroups; iGroup++) {
            for (int iStudent = 0; iStudent < nStudentsInGroup; iStudent++) {
                listOfStudents.add(new Student("Student " + iStudent + ": " + iGroup));
            }
        }
        return listOfStudents;
    }

    public static List<Student> getListWithMilitaryAgeStudents(int nStudents) {
        List<Student> listOfStudents = new ArrayList<>(nStudents);
        for (int iStudent = 0; iStudent < nStudents; iStudent++) {
            listOfStudents.add(new Student("Student " + iStudent, Sex.Male, MilitaryAge.getRandomMilitaryAge()));
        }
        return listOfStudents;
    }

    public static Map<String, Double> getAvgRatingsForSubjects(int nSubjects) {
        Map<String, Double> avgRatingsForSubjects = new HashMap<>(nSubjects);
        for (int i = 0; i < nSubjects; i++) {
            avgRatingsForSubjects.put("Subject " + i, 100.0 - i);
        }
        return avgRatingsForSubjects;
    }
}
