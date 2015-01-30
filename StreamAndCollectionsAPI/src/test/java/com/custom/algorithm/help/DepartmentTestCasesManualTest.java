package com.custom.algorithm.help;

import com.custom.data.Group;
import com.custom.data.Student;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by olga on 25.01.15.
 */
@Ignore
public class DepartmentTestCasesManualTest {
    private static final int nSubjects = 3;
    private static final int nStudentsInGroup = 4;

    @Test
    public void testBuildDepartment() {
        final int nBadGroups = 1;
        Set<Group> department = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nBadGroups, nBadGroups * 2);
        System.out.println("############################");
        System.out.println("Department with " + nBadGroups + " bad groups");
        System.out.println(department);
    }

    @Test
    public void testGetDepartmentWithAvgRatingsForGroups() {
        final int nGroups = 2;
        Map<String, Double> avgRatingsForGroups = InputDataForDepartment.getAvgRatingsForGroups(nGroups);
        Set<Group> department = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(avgRatingsForGroups);
        System.out.println("############################");
        System.out.println("Department with fixed average rating for groups");
        System.out.println("Ratings :" + avgRatingsForGroups);
        System.out.println(department);
    }

    @Test
    public void testGetDepartmentWithGroupsWithSuccessfulStudents() {
        final int nSuccessfulGroups = 2;
        final int nGroups = nSuccessfulGroups + 3;
        final int nSuccessfulStudents = 2;
        final int nStudents = nSuccessfulStudents + 3;
        Set<String> namesOfSuccessfulGroups = InputDataForDepartment
                .getNamesForGroupsWithSuccessfulStudents(nSuccessfulGroups);
        Set<Group> department = DepartmentTestCases.Positive
                .getDepartmentWithGroupsWithSuccessfulStudents(nSuccessfulStudents, nStudents,
                        namesOfSuccessfulGroups, nGroups);
        System.out.println("############################");
        System.out.println("Department with " + namesOfSuccessfulGroups + " groups, in which there are at least " +
                nSuccessfulStudents + " successful students.");
        System.out.println(department);
    }

    @Test
    public void testGetDepartmentWithMenGroups() {
        final int nOnlyMenGroups = 3;
        final int nGroups = nOnlyMenGroups + 2;
        Set<String> namesOfOnlMenGroups = InputDataForDepartment.getNamesForOnlyMenGroups(nOnlyMenGroups);
        Set<Group> department = DepartmentTestCases.Positive.getDepartmentWithMenGroups(namesOfOnlMenGroups, nGroups);
        System.out.println("############################");
        System.out.println(("Department with " + nOnlyMenGroups + " groups without women."));
        System.out.println(department);

    }

    @Test
    public void testGetDepartmentFromListOfStudents() {
        final int nStudentsInGroup = 3;
        final int nGroups = 2;
        List<Student> listOfStudents = InputDataForDepartment.getListWithAllStudents(nStudentsInGroup, nGroups);
        Set<Group> department = DepartmentTestCases.Positive.getDepartmentFromListOfStudents(listOfStudents, nGroups);
        System.out.println("############################");
        System.out.println("Department with fixed list of students.");
        System.out.println("Students: " + listOfStudents);
        System.out.println(department);
    }

    @Test
    public void testGetDepartmentWithSpecifiedMilitaryAgeStudents() {
        final int nStudentsInGroup = 3;
        final int nGroups = 4;
        List<Student> listOfMilitaryAgeStudents = InputDataForDepartment
                .getListWithMilitaryAgeStudents(nStudentsInGroup * nGroups);
        Set<Group> department = DepartmentTestCases.Positive
                .getDepartmentWithSpecifiedMilitaryAgeStudents(listOfMilitaryAgeStudents, nGroups);
        System.out.println("############################");
        System.out.println("Department with fixed list of military age students.");
        System.out.println("Military age students: " + listOfMilitaryAgeStudents);
        System.out.println(department);
    }

    @Test
    public void testGetDepartmentWithListOfSubjects() {
        final int nGroupsWithEqualSubjects = 2;
        final int nGroups = 3;
        Map<String, Double> avgRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(4);
        Set<Group> department = DepartmentTestCases.Positive
                .getDepartmentWithListOfSubjects(avgRatingsOnSubjects, nGroupsWithEqualSubjects, nGroups);
        System.out.println("############################");
        System.out.println("Department with fixed list of subjects.");
        System.out.println("Subjects (with avg ratings): " + avgRatingsOnSubjects);
        System.out.println(department);
    }

}
