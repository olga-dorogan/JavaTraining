package com.custom.algorithm.help;

import com.custom.data.Group;
import com.custom.data.MilitaryAge;
import com.custom.data.Sex;
import com.custom.data.Student;
import com.custom.utility.ConvertMark;

import java.util.*;

/**
 * Created by olga on 25.01.15.
 */
public class DepartmentTestCases {
    private static int N_SUBJECTS = DefaultSettingsForDepartment.N_SUBJECTS_FOR_GROUP;
    private static int N_STUDENTS_IN_GROUP = DefaultSettingsForDepartment.N_STUDENTS_IN_GROUP;

    public static class Positive {
        public static Set<Group> getDepartmentWithBadGroups(int nBadGroups, int nAllGroups) {
            Map<String, Integer> badRating = InputDataForDepartment.getRating(N_SUBJECTS,
                    ConvertMark.fromFourPointToRandPercent(2));
            Map<String, Integer> goodRating = InputDataForDepartment.getRating(N_SUBJECTS,
                    ConvertMark.fromFourPointToRandPercent(5));
            List<Map<String, Integer>> listOfBadRatings = Collections.nCopies(nBadGroups, badRating);
            return new DepartmentBuilder()
                    .setNumberOfGroups(nAllGroups)
                    .setListOfRequiredRatingsForGroups(listOfBadRatings)
                    .setDummyRatingForRestGroups(goodRating)
                    .build();
        }

        public static Set<Group> getDepartmentWithAvgRatingsForGroups(Map<String, Double> avgRatingForNameOfGroup) {
            return new DepartmentBuilder()
                    .setNumberOfGroups(avgRatingForNameOfGroup.size())
                    .setAvgRatingForGroupByGroupName(avgRatingForNameOfGroup)
                    .build();
        }

        public static Set<Group> getDepartmentWithMenGroups(Set<String> namesOfOnlyMenGroups, int nGroups) {
            int nOnlyMenGroups = namesOfOnlyMenGroups.size();
            Student studentMen = new Student();
            studentMen.setSex(Sex.MALE);
            List<Student> listOfOnlyMen = Collections.nCopies(N_STUDENTS_IN_GROUP, studentMen);
            Map<String, List<Student>> groupNameAndListOfStudents = new HashMap<>(nOnlyMenGroups);
            for (String groupName : namesOfOnlyMenGroups) {
                groupNameAndListOfStudents.put(groupName, listOfOnlyMen);
            }
            List<Student> dummyListOfStudents = new ArrayList<>(N_STUDENTS_IN_GROUP);
            Student studentWomen = new Student();
            studentWomen.setSex(Sex.FEMALE);
            for (int i = 0; i < N_STUDENTS_IN_GROUP; i++) {
                if (i % 2 == 0) {
                    dummyListOfStudents.add(studentWomen);
                } else {
                    dummyListOfStudents.add(studentMen);
                }
            }
            return new DepartmentBuilder()
                    .setNumberOfGroups(nGroups)
                    .setListOfStudentsForGroupsByGroupNames(groupNameAndListOfStudents)
                    .setDummyListOfStudentsForRestGroups(dummyListOfStudents)
                    .build();
        }

        public static Set<Group> getDepartmentFromListOfStudents(List<Student> listOfStudents, int nGroups) {
            List<List<Student>> listOfStudentsForGroups = new ArrayList<>(nGroups);
            final int nStudentsInGroup = listOfStudents.size() / nGroups;
            for (int i = 0; i < nGroups; i++) {
                listOfStudentsForGroups.add(listOfStudents.subList(i * nStudentsInGroup, (i + 1) * nStudentsInGroup));
            }
            return new DepartmentBuilder()
                    .setNumberOfGroups(nGroups)
                    .setListOfStudentsForGroups(listOfStudentsForGroups)
                    .build();
        }

        public static Set<Group> getDepartmentWithListOfSubjects(Map<String, Double> avgRatingsOnSubjects,
                                                                 int nGroupsWithEqualSubjects, int nGroups) {
            List<Map<String, Integer>> ratingsForTwoDiffKindsOfGroups = getListsOfRatingsForGroups(avgRatingsOnSubjects);
            List<Map<String, Integer>> ratingsForForEqualSubjectGroups =
                    Collections.nCopies(nGroupsWithEqualSubjects, ratingsForTwoDiffKindsOfGroups.get(0));
            return new DepartmentBuilder()
                    .setNumberOfGroups(nGroups)
                    .setListOfRequiredRatingsForGroups(ratingsForForEqualSubjectGroups)
                    .setDummyRatingForRestGroups(ratingsForTwoDiffKindsOfGroups.get(1))
                    .build();
        }


        public static Set<Group> getDepartmentWithSpecifiedMilitaryAgeStudents(List<Student> listOfMilitaryAgeStudents,
                                                                               int nGroups) {
            final int nMilitaryAgeStudentsInGroup = listOfMilitaryAgeStudents.size() / nGroups;
            final int restMilitaryAgeStudents = listOfMilitaryAgeStudents.size() % nGroups;
            final int nNonMilitaryAgeStudentsInGroup = N_STUDENTS_IN_GROUP - 1;
            List<List<Student>> listOfStudentsForGroups = new ArrayList<>();
            List<Student> listOfStudentsForCurrentGroup;

            List<Student> listOfNonMilitaryAgeStudents = new ArrayList<>(nNonMilitaryAgeStudentsInGroup);
            Student nonMilitaryAgeMan = new Student("", Sex.MALE, MilitaryAge.getNotMilitaryAge());
            Student woman = new Student("", Sex.FEMALE, MilitaryAge.getRandomMilitaryAge());
            for (int i = 0; i < nNonMilitaryAgeStudentsInGroup; i++) {
                if (i % 2 == 0) {
                    listOfNonMilitaryAgeStudents.add(nonMilitaryAgeMan);
                } else {
                    listOfNonMilitaryAgeStudents.add(woman);
                }
            }

            for (int iGroup = 0; iGroup < nGroups; iGroup++) {
                listOfStudentsForCurrentGroup = new ArrayList<>();
                listOfStudentsForCurrentGroup.addAll(listOfMilitaryAgeStudents.subList(
                        iGroup * nMilitaryAgeStudentsInGroup,
                        (iGroup + 1) * nMilitaryAgeStudentsInGroup));
                if ((iGroup + 1) == nGroups) {
                    listOfStudentsForCurrentGroup.addAll(listOfMilitaryAgeStudents.subList(
                            listOfMilitaryAgeStudents.size() - restMilitaryAgeStudents,
                            listOfMilitaryAgeStudents.size()
                    ));
                }
                listOfStudentsForCurrentGroup.addAll(listOfNonMilitaryAgeStudents);
                listOfStudentsForGroups.add(listOfStudentsForCurrentGroup);
            }

            return new DepartmentBuilder()
                    .setNumberOfGroups(nGroups)
                    .setListOfStudentsForGroups(listOfStudentsForGroups)
                    .build();
        }

        public static Set<Group> getDepartmentWithGroupsWithSuccessfulStudents(
                int nSuccessfulStudentsInGroup, int nAllStudentsInGroup,
                Set<String> listOfSuccessfullyGroupNames, int nGroups) {
            // 1. get successful student
            Map<String, Integer> badRating = InputDataForDepartment.getRating(N_SUBJECTS,
                    ConvertMark.fromFourPointToRandPercent(2));
            Map<String, Integer> goodRating = InputDataForDepartment.getRating(N_SUBJECTS,
                    ConvertMark.fromFourPointToRandPercent(5));

            List<Student> listOfStudentsForSuccessfulGroup = new ArrayList<>(nAllStudentsInGroup);
            Student student;
            for (int iStudent = 0; iStudent < nSuccessfulStudentsInGroup; iStudent++) {
                student = new Student();
                student.setRatingOnSubjects(goodRating);
                listOfStudentsForSuccessfulGroup.add(student);
            }
            for (int iStudent = nSuccessfulStudentsInGroup; iStudent < nAllStudentsInGroup; iStudent++) {
                student = new Student();
                student.setRatingOnSubjects(badRating);
                listOfStudentsForSuccessfulGroup.add(student);
            }
            // 2. get map with name of group as key
            //    and list of successful students as value
            Map<String, List<Student>> studentsForGroups = new HashMap<>(listOfSuccessfullyGroupNames.size());
            for (String groupName : listOfSuccessfullyGroupNames) {
                studentsForGroups.put(groupName, listOfStudentsForSuccessfulGroup);
            }
            // 3. build department
            return new DepartmentBuilder()
                    .setNumberOfGroups(nGroups)
                    .setListOfStudentsForGroupsByGroupNames(studentsForGroups)
                    .setDummyRatingForRestGroups(badRating)
                    .build();
        }

    }

    public static class Negative {
        public static void addToDepartmentGroupAsNull(Set<Group> department) {
            department.add(null);
        }

        public static void addToDepartmentIncorrectGroup(Set<Group> department) {
            Group incorrectGroup = new Group("Incorrect group", 1);
            incorrectGroup.setListOfStudents(null);
            department.add(incorrectGroup);
        }

        public static void addToGroupIncorrectStudent(Set<Group> department) {
            Iterator<Group> iterator = department.iterator();
            if (iterator.hasNext()) {
                Group group = iterator.next();
                Student incorrectStudent = new Student("Incorrect student", Sex.MALE, 18);
                incorrectStudent.setRatingOnSubjects(null);
                group.addStudent(incorrectStudent);
            }
        }

        public static void addToGroupStudentAsNull(Set<Group> department) {
            Iterator<Group> iterator = department.iterator();
            if (iterator.hasNext()) {
                Group group = iterator.next();
                group.addStudent(null);
            }
        }

        public static void addToDepartmentGroupWithDuplicateNameAndCourse(Set<Group> department) {
            Iterator<Group> iterator = department.iterator();
            if (iterator.hasNext()) {
                Group group = iterator.next();
                Group duplicateGroup = new Group(group.getName(), group.getCourse(), group.getListOfStudents());
                department.add(duplicateGroup);
            }
        }

        public static void addToDepartmentGroupWithDuplicateName(Set<Group> department, Map<String, Double> result,
                                                                 boolean isCoursesEqual) {
            changeGroupNamesToDescriptions(department, result);
            Iterator<Group> iterator = department.iterator();
            if (iterator.hasNext()) {
                Group group = iterator.next();
                Group duplicateGroup = new Group(
                        group.getName(),
                        isCoursesEqual ? group.getCourse() : group.getCourse() + 1,
                        group.getListOfStudents());
                department.add(duplicateGroup);
                result.put(duplicateGroup.getDescription(), result.get(group.getDescription()));
            }
        }


    }


    private static List<Map<String, Integer>> getListsOfRatingsForGroups(Map<String, Double> avgRatingsOnSubjects) {
        List<Map<String, Integer>> listWithRatingsForGroups = new ArrayList<>(2);
        int sizeOfFirstSubList = avgRatingsOnSubjects.size() / 2;
        Map<String, Integer> ratingsOnSubjectsFirst = new HashMap<>(sizeOfFirstSubList);
        Map<String, Integer> ratingsOnSubjectsSecond = new HashMap<>(avgRatingsOnSubjects.size() - sizeOfFirstSubList);
        int cnt = 0;
        for (String subjectName : avgRatingsOnSubjects.keySet()) {
            if (cnt < sizeOfFirstSubList) {
                ratingsOnSubjectsFirst.put(subjectName, (int) ((double) avgRatingsOnSubjects.get(subjectName)));
            } else {
                ratingsOnSubjectsSecond.put(subjectName, (int) ((double) avgRatingsOnSubjects.get(subjectName)));
            }
            cnt++;
        }
        listWithRatingsForGroups.add(ratingsOnSubjectsFirst);
        listWithRatingsForGroups.add(ratingsOnSubjectsSecond);
        return listWithRatingsForGroups;
    }

    public static void changeGroupNamesToDescriptions(Set<Group> department, Map<String, Double> result) {
        Double value;
        for (Group group : department) {
            if(group != null) {
                value = result.get(group.getName());
                if (value != null) {
                    result.remove(group.getName());
                    result.put(group.getDescription(), value);
                }
            }
        }
    }

    public static void changeGroupNamesToDescriptions(Set<Group> department, Set<String> result) {
        for (Group group : department) {
            if (group != null && result.contains(group.getName())) {
                result.remove(group.getName());
                result.add(group.getDescription());
            }
        }
    }
}
