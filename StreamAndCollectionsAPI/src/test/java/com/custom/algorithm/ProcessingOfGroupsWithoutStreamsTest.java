package com.custom.algorithm;

import com.custom.algorithm.help.CheckResults;
import com.custom.algorithm.help.DepartmentTestCases;
import com.custom.algorithm.help.InputDataForDepartment;
import com.custom.data.Group;
import com.custom.data.Student;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProcessingOfGroupsWithoutStreamsTest {
    ProcessingOfGroups testedProcessingOfGroups = new ProcessingOfGroupsWithoutStreams();
    Set<Group> testedDepartment;

    @Test
    public void testGetCountOfGroupsWithPoorStudents() throws Exception {
        final int nPoorGroups = 3;
        testedDepartment = DepartmentTestCases.getDepartmentWithBadGroups(nPoorGroups, nPoorGroups * 2);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    public void testGetCountOfGroupsWithPoorStudentsWhenArgumentIsNull() throws Exception {
        testedDepartment = null;
        assertEquals(0, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    public void testGetAvgProgressForEveryGroup() throws Exception {
        final int nGroups = 5;
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(nGroups);
        testedDepartment = DepartmentTestCases.getDepartmentWithAvgRatingsForGroups(expectedResult);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    @Test
    public void testGetGroupNamesWithOnlyMen() throws Exception {
        final int nMenGroups = 4;
        final Set<String> expectedGroupNames = InputDataForDepartment.getNamesForOnlyMenGroups(nMenGroups);
        testedDepartment = DepartmentTestCases.getDepartmentWithMenGroups(expectedGroupNames, nMenGroups * 2);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    @Test
    public void testGetAllStudentsFromDepartment() throws Exception {
        final int nStudentsInGroup = 5;
        final int nGroups = 5;
        List<Student> expectedListOfStudents = InputDataForDepartment.getListWithAllStudents(nStudentsInGroup, nGroups);
        testedDepartment = DepartmentTestCases.getDepartmentFromListOfStudents(expectedListOfStudents, nGroups);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }

    @Test
    public void testGetAvgRatingForEverySubject() throws Exception {
        final int nSubjects = 12;
        final int nGroups = 3;
        final int nGroupsWithEqualSubjects = 2;
        Map<String, Double> expectedAvgRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.getDepartmentWithListOfSubjects(expectedAvgRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        assertEquals(expectedAvgRatingsOnSubjects,
                testedProcessingOfGroups.getAvgRatingForEverySubject(testedDepartment));
    }

    @Test
    public void testGetAllMilitaryAgeStudents() throws Exception {
        final int nMilitaryAgeStudents = 7;
        final int nGroups = 3;
        List<Student> expectedListOfStudents = InputDataForDepartment
                .getListWithMilitaryAgeStudents(nMilitaryAgeStudents);
        testedDepartment = DepartmentTestCases
                .getDepartmentWithSpecifiedMilitaryAgeStudents(expectedListOfStudents, nGroups);
        assertEquals(expectedListOfStudents, testedProcessingOfGroups.getAllMilitaryAgeStudents(testedDepartment));
    }

    @Test
    public void testGetAllSubjects() throws Exception {
        final int nSubjects = 7;
        final int nGroupsWithEqualSubjects = 4;
        final int nGroups = nGroupsWithEqualSubjects + 2;
        Map<String, Double> inputRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.getDepartmentWithListOfSubjects(inputRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        Set<String> expectedListOfSubjects = inputRatingsOnSubjects.keySet();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(testedDepartment));
    }

    @Test
    public void testGetAllGroupsWithMoreThenOneSuccessfulStudent() throws Exception {
        final int nGroups = 5;
        final int nSuccessfulStudentsInGroup = 2;
        final int nOfAllStudents = 10;
        Set<String> expectedSetOfGroupNames = InputDataForDepartment.getNamesForGroupsWithSuccessfulStudents(nGroups);
        testedDepartment = DepartmentTestCases.getDepartmentWithGroupsWithSuccessfulStudents(nSuccessfulStudentsInGroup,
                nOfAllStudents, expectedSetOfGroupNames, nGroups);
        assertEquals(expectedSetOfGroupNames,
                testedProcessingOfGroups.getAllGroupsWithMoreThenOneSuccessfulStudent(testedDepartment));
    }
}