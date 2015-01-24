package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.Sex;
import com.custom.data.Student;
import com.custom.utility.ConvertMark;

import java.util.*;

/**
 * Created by olga on 22.01.15.
 */
public class HelperClass {
    private static final int avgNStudents = 5;
    private static final int avgNSubjects = 7;

    private static final int dummyAge = 18;
    private static final Sex dummySex = Sex.Female;
    private static final int dummyCourse = 1;

    public static Set<Group> getDepartmentWithPoorGroups(int nPoorGroups) {

        int nGroups = nPoorGroups * (int) (10 * (Math.random() + 0.1));
        Set<Group> department = new TreeSet<>((Group o1, Group o2) ->
                (o1.getName()).compareTo(o2.getName()));

        for (int curGroup = 0; curGroup < nGroups; curGroup++) {
            List<String> listOfSubjects = getListOfSubjects(avgNSubjects + (int) ((Math.random() - 0.5) * avgNSubjects));
            int nStudents = avgNStudents + (int) ((Math.random() - 0.5) * avgNStudents - avgNStudents / 2);
            List<Student> listOfStudents = new ArrayList<>(nStudents);
            for (int j = 0; j < nStudents; j++) {
                Map<String, Integer> ratingOnSubjects = new HashMap<>(listOfSubjects.size());
                for (int k = 0; k < listOfSubjects.size(); k++) {
                    if (curGroup < nPoorGroups) {
                        ratingOnSubjects.put(listOfSubjects.get(k), ConvertMark.fromFourPointToRandPercent(2));
                    } else {
                        ratingOnSubjects.put(listOfSubjects.get(k), ConvertMark.fromFourPointToRandPercent(5));
                    }
                }
                listOfStudents.add(new Student("Student " + curGroup + "_" + j, dummySex, dummyAge, ratingOnSubjects));
            }
            department.add(new Group("Group " + curGroup, dummyCourse, listOfStudents));
        }
        return department;
    }

    public static Set<Group> getDepartmentWithAvgProgressForEveryGroup(final Map<String, Double> mapWithAvgProgress) {
        int nGroups = mapWithAvgProgress.size();
        Set<Group> department = new HashSet<>(nGroups);
        Set<String> groupNames = mapWithAvgProgress.keySet();

        for (String groupName : groupNames) {
            List<String> listOfSubjects = getListOfSubjects(avgNSubjects);//+ (int) ((Math.random() - 0.5) * avgNSubjects));
            int nStudents = avgNStudents;// + (int) ((Math.random() - 0.5) * avgNStudents - avgNStudents / 2);
            List<Student> listOfStudents = new ArrayList<>(nStudents);
            for (int curStudent = 0; curStudent < nStudents; curStudent++) {
                Map<String, Integer> ratingOnSubjects = new HashMap<>(listOfSubjects.size());
                for (int k = 0; k < listOfSubjects.size(); k++) {
                    double mark = mapWithAvgProgress.get(groupName);
                    ratingOnSubjects.put(listOfSubjects.get(k), (int) mark);
                }
                listOfStudents.add(new Student("Student " + groupName + "_" + curStudent,
                        dummySex, dummyAge, ratingOnSubjects));
            }
            department.add(new Group(groupName, dummyCourse, listOfStudents));
        }
        return department;
    }

    public static Set<Group> getDepartmentWithOnlyMenGroups(Set<String> namesOfMenGroups) {
        Set<Group> department = new HashSet<>();
        int nAdditionalGroups = namesOfMenGroups.size();
        int nStudents;
        List<Student> listOfStudents;
        for (String groupWithOnlyMenName : namesOfMenGroups) {
            nStudents = avgNStudents * (1 + (int) (Math.random() - 0.5));
            listOfStudents = new ArrayList<>(nStudents);
            for (int curStudent = 0; curStudent < nStudents; curStudent++) {
                listOfStudents.add(new Student("Student " + curStudent, Sex.Male, dummyAge));
            }
            department.add(new Group(groupWithOnlyMenName, dummyCourse, listOfStudents));
        }
        for (int iAdditionalGroup = 0; iAdditionalGroup < nAdditionalGroups; iAdditionalGroup++) {
            nStudents = avgNStudents * (1 + (int) (Math.random() - 0.5));
            listOfStudents = new ArrayList<>(nStudents);
            for (int curStudent = 0; curStudent < nStudents; curStudent++) {
                listOfStudents.add(new Student("Student " + curStudent,
                        curStudent % 2 == 0 ? Sex.Female : Sex.Male, dummyAge));
            }
            department.add(new Group("Group " + iAdditionalGroup, dummyCourse, listOfStudents));
        }
        return department;
    }

    public static Set<Group> getDepartmentWithStudents(List<Student> listOfAllStudents, int nStudentsInGroup) {
        int nGroups = listOfAllStudents.size() / nStudentsInGroup;
        Set<Group> department = new HashSet<>(nGroups);
        for (int iGroup = 0; iGroup < nGroups; iGroup++) {
            department.add(new Group("Group " + iGroup, dummyCourse,
                    listOfAllStudents.subList(nStudentsInGroup * iGroup, nStudentsInGroup * (iGroup + 1))));
        }
        return department;
    }

    public static List<Student> getListOfStudentsForAllDepartment(int nStudentsInGroup, int nGroups) {
        List<Student> listOfStudents = new ArrayList<>(nStudentsInGroup * nGroups);
        StringBuilder sb = new StringBuilder();
        for (int iGroup = 0; iGroup < nGroups; iGroup++) {
            for (int iStudent = 0; iStudent < nStudentsInGroup; iStudent++) {
                sb.setLength(0);
                sb.append("Student ").append(iStudent).append('(').append(iGroup).append((')'));
                listOfStudents.add(new Student(sb.toString(), Sex.Female, dummyAge));
            }
        }
        return listOfStudents;
    }

    public static boolean compareListsToEquality(List<Student> first, List<Student> second) {
        if ((first == null) && (second == null)) {
            return true;
        }
        if ((first == null) || (second == null)) {
            return false;
        }
        if (first.size() != second.size()) {
            return false;
        }
        if (!first.containsAll(second) || !second.containsAll(first)) {
            return false;
        }
        List<Student> copyOfFirst = new ArrayList<>(first);
        List<Student> copyOfSecond = new ArrayList<>(second);
        if (copyOfFirst.retainAll(second) || copyOfSecond.retainAll(first)) {
            return false;
        }
        return true;
    }


    private static List<String> getListOfSubjects(int nSubjects) {
        List<String> list = new ArrayList<>(nSubjects);
        for (int i = 0; i < nSubjects; i++) {
            String str = "Subject " + i;
            str.intern();
            list.add(str);
        }
        return list;
    }
}
