package com.custom.algorithm.help;

import com.custom.data.Group;
import com.custom.data.Student;

import java.util.*;

/**
 * Created by olga on 25.01.15.
 */
class DepartmentBuilder {
    private static final int N_STUDENTS_IN_GROUP = DefaultSettingsForDepartment.N_STUDENTS_IN_GROUP;
    private static final int N_SUBJECTS_FOR_GROUP = DefaultSettingsForDepartment.N_SUBJECTS_FOR_GROUP;

    private int nGroups;

    private List<Map<String, Integer>> listOfRatingsForGroups;
    private Map<String, Integer> dummyRatingForRestGroups;

    private List<List<Student>> listOfStudentsForGroups;
    private List<Student> dummyListOfStudentsForRestGroups;

    private List<String> listOfRequiredGroupNames;

    public DepartmentBuilder setNumberOfGroups(int numberOfGroups) {
        this.nGroups = numberOfGroups;
        return this;
    }

    public DepartmentBuilder setListOfRequiredRatingsForGroups(List<Map<String, Integer>> listOfRatingsForGroups) {
        this.listOfRatingsForGroups = new ArrayList<>(listOfRatingsForGroups);
        return this;
    }

    public DepartmentBuilder setDummyRatingForRestGroups(Map<String, Integer> dummyRatingForRestGroups) {
        this.dummyRatingForRestGroups = new HashMap<>(dummyRatingForRestGroups);
        return this;
    }

    public DepartmentBuilder setAvgRatingForGroupByGroupName(Map<String, Double> avgRatingByNameOfGroup) {
        final int groups = avgRatingByNameOfGroup.keySet().size();
        listOfRatingsForGroups = new ArrayList<>(groups);
        listOfRequiredGroupNames = new ArrayList<>(groups);
        Map<String, Integer> ratingForStudentsInGroup;
        int mark;
        for (String groupName : avgRatingByNameOfGroup.keySet()) {
            mark = (int) ((double) avgRatingByNameOfGroup.get(groupName));
            ratingForStudentsInGroup = InputDataForDepartment.getRating(N_SUBJECTS_FOR_GROUP, mark);
            listOfRatingsForGroups.add(ratingForStudentsInGroup);
            listOfRequiredGroupNames.add(groupName);
        }
        return this;
    }

    public DepartmentBuilder setListOfStudentsForGroupsByGroupNames(Map<String,
            List<Student>> listOfStudentsByGroupName) {
        listOfRequiredGroupNames = new ArrayList<>(listOfStudentsByGroupName.size());
        listOfStudentsForGroups = new ArrayList<>(listOfStudentsByGroupName.size());
        Set<String> groups = listOfStudentsByGroupName.keySet();
        for (String groupName : groups) {
            listOfRequiredGroupNames.add(groupName);
            listOfStudentsForGroups.add(listOfStudentsByGroupName.get(groupName));
        }
        return this;
    }

    public DepartmentBuilder setListOfStudentsForGroups(List<List<Student>> listOfStudentsForGroups) {
        this.listOfStudentsForGroups = new ArrayList<>(listOfStudentsForGroups);
        return this;
    }

    public DepartmentBuilder setDummyListOfStudentsForRestGroups(List<Student> dummyListOfStudentsForRestGroups) {
        this.dummyListOfStudentsForRestGroups = dummyListOfStudentsForRestGroups;
        return this;
    }


    public Set<Group> build() {
        Set<Group> retSet = new TreeSet<>((Group o1, Group o2) ->
                (o1.getName()).compareTo(o2.getName()));
        for (int iGroup = 0; iGroup < nGroups; iGroup++) {
            retSet.add(new Group(getGroupName(iGroup), 1, getListOfStudentsForGroup(iGroup)));
        }
        return retSet;
    }

    private String getGroupName(int iGroup) {
        if (listOfRequiredGroupNames != null && iGroup < listOfRequiredGroupNames.size()) {
            return listOfRequiredGroupNames.get(iGroup);
        } else {
            return "Group default " + iGroup;
        }
    }

    private List<Student> getListOfStudentsForGroup(int iGroup) {
        if (listOfStudentsForGroups != null && iGroup < listOfStudentsForGroups.size()) {
            return listOfStudentsForGroups.get(iGroup);
        } else if (dummyListOfStudentsForRestGroups != null) {
            return dummyListOfStudentsForRestGroups;
        } else {
            List<Student> retList = new ArrayList<>(N_STUDENTS_IN_GROUP);
            Map<String, Integer> rating = getRatingForGroup(iGroup);
            Student student;
            for (int iStudent = 0; iStudent < N_STUDENTS_IN_GROUP; iStudent++) {
                student = new Student();
                student.setRatingOnSubjects(rating);
                retList.add(student);
            }
            return retList;
        }
    }

    private Map<String, Integer> getRatingForGroup(int iGroup) {
        if (listOfRatingsForGroups != null && iGroup < listOfRatingsForGroups.size()) {
            return listOfRatingsForGroups.get((iGroup));
        } else {
            return dummyRatingForRestGroups;
        }
    }

}
