package com.custom.algorithm;

import com.custom.algorithm.help.CheckResults;
import com.custom.algorithm.help.DepartmentTestCases;
import com.custom.algorithm.help.InputDataForDepartment;
import com.custom.data.Group;
import com.custom.data.Student;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by olga on 28.01.15.
 */

@RunWith(JUnitParamsRunner.class)
public class ProcessingOfGroupsTest {
    private static final int N_GROUPS_DEFAULT = 5;
    Set<Group> testedDepartment;


    ProcessingOfGroups testedProcessingOfGroupsWithStreams = new ProcessingOfGroupsWithStreams();
    ProcessingOfGroups testedProcessingOfGroupsWithoutStreams = new ProcessingOfGroupsWithoutStreams();

    private Object[] getTestedProcessingClasses() {
        return $(
                $(testedProcessingOfGroupsWithoutStreams),
                $(testedProcessingOfGroupsWithStreams)
        );
    }

    // getCountOfGroupsWithPoorStudents()
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudents(ProcessingOfGroups testedProcessingOfGroups) {
        final int nPoorGroups = 3;
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nPoorGroups, nPoorGroups * 2);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudentsOnDepartmentWithoutPoorStudents(
            ProcessingOfGroups testedProcessingOfGroups) {
        final int nPoorGroups = 0;
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nPoorGroups, N_GROUPS_DEFAULT);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudentsOnDepartmentWithAllBadGroups(
            ProcessingOfGroups testedProcessingOfGroups) {
        final int nPoorGroups = 3;
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nPoorGroups, nPoorGroups);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudentsOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        testedDepartment = null;
        assertEquals(0, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudentsOnIncorrectDepartment(
            ProcessingOfGroups testedProcessingOfGroups) {
        final int nPoorGroups = 3;
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nPoorGroups, nPoorGroups * 2);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudentsOnDepartmentWithIncorrectGroup(
            ProcessingOfGroups testedProcessingOfGroups) {
        final int nPoorGroups = 3;
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nPoorGroups, nPoorGroups * 2);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetCountOfGroupsWithPoorStudentsOnDepartmentWithIncorrectStudent(
            ProcessingOfGroups testedProcessingOfGroups) {
        final int nPoorGroups = 3;
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithBadGroups(nPoorGroups, nPoorGroups * 2);
        DepartmentTestCases.Negative.addToGroupIncorrectStudent(testedDepartment);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    // getAvgProgressForEveryGroup()

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroupWithDuplicateGroupNames(ProcessingOfGroups testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(expectedResult);
        DepartmentTestCases.Negative.addToDepartmentGroupWithDuplicateName(testedDepartment, expectedResult, false);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroupWithDuplicateGroupNamesAndCourses(
            ProcessingOfGroups testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(expectedResult);
        DepartmentTestCases.Negative.addToDepartmentGroupWithDuplicateName(testedDepartment, expectedResult, true);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroup(ProcessingOfGroups testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(expectedResult);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedResult);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroupOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = new HashMap<>();
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(null));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroupOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(expectedResult);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedResult);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroupOnDepartmentWithIncorrectGroup(ProcessingOfGroups
                                                                                      testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(expectedResult);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedResult);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgProgressForEveryGroupOnDepartmentWithIncorrectStudent(ProcessingOfGroups
                                                                                        testedProcessingOfGroups) {
        final Map<String, Double> expectedResult = InputDataForDepartment.getAvgRatingsForGroups(N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithAvgRatingsForGroups(expectedResult);
        DepartmentTestCases.Negative.addToGroupIncorrectStudent(testedDepartment);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedResult);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }

    // getGroupNamesWithOnlyMen()
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetGroupNamesWithOnlyMen(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMenGroups = 4;
        final Set<String> expectedGroupNames = InputDataForDepartment.getNamesForOnlyMenGroups(nMenGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithMenGroups(expectedGroupNames, nMenGroups * 2);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedGroupNames);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetGroupNamesWithOnlyMenOnDepartmentWithOnlyMenGroups(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMenGroups = 4;
        final Set<String> expectedGroupNames = InputDataForDepartment.getNamesForOnlyMenGroups(nMenGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithMenGroups(expectedGroupNames, nMenGroups);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedGroupNames);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetGroupNamesWithOnlyMenOnDepartmentWithoutMenGroups(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMenGroups = 0;
        final Set<String> expectedGroupNames = InputDataForDepartment.getNamesForOnlyMenGroups(nMenGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithMenGroups(expectedGroupNames, N_GROUPS_DEFAULT);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedGroupNames);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetGroupNamesWithOnlyMenOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        final Set<String> expectedGroupNames = new HashSet<>();
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(null));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetGroupNamesWithOnlyMenOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMenGroups = 4;
        final Set<String> expectedGroupNames = InputDataForDepartment.getNamesForOnlyMenGroups(nMenGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithMenGroups(expectedGroupNames, nMenGroups);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedGroupNames);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetGroupNamesWithOnlyMenOnDepartmentWithIncorrectGroup(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMenGroups = 4;
        final Set<String> expectedGroupNames = InputDataForDepartment.getNamesForOnlyMenGroups(nMenGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithMenGroups(expectedGroupNames, nMenGroups);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        DepartmentTestCases.changeGroupNamesToDescriptions(testedDepartment, expectedGroupNames);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    // getAllStudentsFromDepartment()
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllStudentsFromDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nStudentsInGroup = 5;
        List<Student> expectedListOfStudents = InputDataForDepartment.getListWithAllStudents(nStudentsInGroup,
                N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentFromListOfStudents(expectedListOfStudents,
                N_GROUPS_DEFAULT);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllStudentsFromDepartmentOnDepartmentWithoutStudents(ProcessingOfGroups testedProcessingOfGroups) {
        List<Student> expectedListOfStudents = new ArrayList<>();
        testedDepartment = DepartmentTestCases.Positive.getDepartmentFromListOfStudents(expectedListOfStudents,
                N_GROUPS_DEFAULT);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllStudentsFromDepartmentOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        List<Student> expectedListOfStudents = new ArrayList<>();
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(null)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllStudentsFromDepartmentOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nStudentsInGroup = 5;
        List<Student> expectedListOfStudents = InputDataForDepartment.getListWithAllStudents(nStudentsInGroup,
                N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentFromListOfStudents(expectedListOfStudents,
                N_GROUPS_DEFAULT);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllStudentsFromDepartmentOnDepartmentWithIncorrectGroup(ProcessingOfGroups
                                                                                       testedProcessingOfGroups) {
        final int nStudentsInGroup = 5;
        List<Student> expectedListOfStudents = InputDataForDepartment.getListWithAllStudents(nStudentsInGroup,
                N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentFromListOfStudents(expectedListOfStudents,
                N_GROUPS_DEFAULT);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllStudentsFromDepartmentOnDepartmentWithIncorrectStudent(ProcessingOfGroups
                                                                                         testedProcessingOfGroups) {
        final int nStudentsInGroup = 5;
        List<Student> expectedListOfStudents = InputDataForDepartment.getListWithAllStudents(nStudentsInGroup,
                N_GROUPS_DEFAULT);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentFromListOfStudents(expectedListOfStudents,
                N_GROUPS_DEFAULT);
        expectedListOfStudents.add(null);
        DepartmentTestCases.Negative.addToGroupStudentAsNull(testedDepartment);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }

    // getAvgRatingForEverySubject
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgRatingForEverySubject(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 12;
        final int nGroups = 3;
        final int nGroupsWithEqualSubjects = 2;
        Map<String, Double> expectedAvgRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(expectedAvgRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        assertEquals(expectedAvgRatingsOnSubjects,
                testedProcessingOfGroups.getAvgRatingForEverySubject(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgRatingForEverySubjectOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        Map<String, Double> expectedAvgRatingsOnSubjects = new HashMap<>();
        assertEquals(expectedAvgRatingsOnSubjects, testedProcessingOfGroups.getAvgRatingForEverySubject(null));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgRatingForEverySubjectOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 12;
        final int nGroups = 3;
        final int nGroupsWithEqualSubjects = 2;
        Map<String, Double> expectedAvgRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(expectedAvgRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        assertEquals(expectedAvgRatingsOnSubjects,
                testedProcessingOfGroups.getAvgRatingForEverySubject(testedDepartment));

    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgRatingForEverySubjectOnDepartmentWithIncorrectGroup(ProcessingOfGroups
                                                                                      testedProcessingOfGroups) {
        final int nSubjects = 12;
        final int nGroups = 3;
        final int nGroupsWithEqualSubjects = 2;
        Map<String, Double> expectedAvgRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(expectedAvgRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        assertEquals(expectedAvgRatingsOnSubjects,
                testedProcessingOfGroups.getAvgRatingForEverySubject(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAvgRatingForEverySubjectOnDepartmentWithIncorrectStudent(ProcessingOfGroups
                                                                                        testedProcessingOfGroups) {
        final int nSubjects = 12;
        final int nGroups = 3;
        final int nGroupsWithEqualSubjects = 2;
        Map<String, Double> expectedAvgRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(expectedAvgRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        DepartmentTestCases.Negative.addToGroupIncorrectStudent(testedDepartment);
        assertEquals(expectedAvgRatingsOnSubjects,
                testedProcessingOfGroups.getAvgRatingForEverySubject(testedDepartment));
    }

    // testGetAllMilitaryAgeStudents
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllMilitaryAgeStudents(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMilitaryAgeStudents = 7;
        List<Student> expectedListOfStudents = InputDataForDepartment
                .getListWithMilitaryAgeStudents(nMilitaryAgeStudents);
        testedDepartment = DepartmentTestCases.Positive
                .getDepartmentWithSpecifiedMilitaryAgeStudents(expectedListOfStudents, N_GROUPS_DEFAULT);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllMilitaryAgeStudents(testedDepartment)));
    }


    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllMilitaryAgeStudentsOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        List<Student> expectedListOfStudents = new ArrayList<>();
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllMilitaryAgeStudents(null)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllMilitaryAgeStudentsOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nMilitaryAgeStudents = 7;
        List<Student> expectedListOfStudents = InputDataForDepartment
                .getListWithMilitaryAgeStudents(nMilitaryAgeStudents);
        testedDepartment = DepartmentTestCases.Positive
                .getDepartmentWithSpecifiedMilitaryAgeStudents(expectedListOfStudents, N_GROUPS_DEFAULT);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllMilitaryAgeStudents(testedDepartment)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllMilitaryAgeStudentsOnDepartmentWithIncorrectGroup(ProcessingOfGroups
                                                                                    testedProcessingOfGroups) {
        final int nMilitaryAgeStudents = 7;
        List<Student> expectedListOfStudents = InputDataForDepartment
                .getListWithMilitaryAgeStudents(nMilitaryAgeStudents);
        testedDepartment = DepartmentTestCases.Positive
                .getDepartmentWithSpecifiedMilitaryAgeStudents(expectedListOfStudents, N_GROUPS_DEFAULT);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllMilitaryAgeStudents(testedDepartment)));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllMilitaryAgeStudentsOnDepartmentWithIncorrectStudent(ProcessingOfGroups
                                                                                      testedProcessingOfGroups) {
        final int nMilitaryAgeStudents = 7;
        List<Student> expectedListOfStudents = InputDataForDepartment
                .getListWithMilitaryAgeStudents(nMilitaryAgeStudents);
        testedDepartment = DepartmentTestCases.Positive
                .getDepartmentWithSpecifiedMilitaryAgeStudents(expectedListOfStudents, N_GROUPS_DEFAULT);
        DepartmentTestCases.Negative.addToGroupIncorrectStudent(testedDepartment);
        assertTrue(CheckResults.compareListsToEquality(expectedListOfStudents,
                testedProcessingOfGroups.getAllMilitaryAgeStudents(testedDepartment)));
    }

    // testGetAllSubjects
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllSubjects(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 7;
        final int nGroupsWithEqualSubjects = 4;
        final int nGroups = nGroupsWithEqualSubjects + 2;
        Map<String, Double> inputRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(inputRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        Set<String> expectedListOfSubjects = inputRatingsOnSubjects.keySet();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllSubjectsOnDepartmentWithoutSubjects(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 0;
        final int nGroupsWithEqualSubjects = 4;
        final int nGroups = nGroupsWithEqualSubjects + 2;
        Map<String, Double> inputRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(inputRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        Set<String> expectedListOfSubjects = inputRatingsOnSubjects.keySet();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllSubjectsOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        Set<String> expectedListOfSubjects = new HashSet<>();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(null));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllSubjectsOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 7;
        final int nGroupsWithEqualSubjects = 4;
        final int nGroups = nGroupsWithEqualSubjects + 2;
        Map<String, Double> inputRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(inputRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        Set<String> expectedListOfSubjects = inputRatingsOnSubjects.keySet();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllSubjectsOnDepartmentWithIncorrectGroup(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 7;
        final int nGroupsWithEqualSubjects = 4;
        final int nGroups = nGroupsWithEqualSubjects + 2;
        Map<String, Double> inputRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(inputRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        Set<String> expectedListOfSubjects = inputRatingsOnSubjects.keySet();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllSubjectsOnDepartmentWithIncorrectStudent(ProcessingOfGroups testedProcessingOfGroups) {
        final int nSubjects = 7;
        final int nGroupsWithEqualSubjects = 4;
        final int nGroups = nGroupsWithEqualSubjects + 2;
        Map<String, Double> inputRatingsOnSubjects = InputDataForDepartment.getAvgRatingsForSubjects(nSubjects);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithListOfSubjects(inputRatingsOnSubjects,
                nGroupsWithEqualSubjects, nGroups);
        DepartmentTestCases.Negative.addToGroupIncorrectStudent(testedDepartment);
        Set<String> expectedListOfSubjects = inputRatingsOnSubjects.keySet();
        assertEquals(expectedListOfSubjects, testedProcessingOfGroups.getAllSubjects(testedDepartment));
    }

    // getAllGroupsWithMoreThenOneSuccessfulStudent
    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllGroupsWithMoreThenOneSuccessfulStudent(ProcessingOfGroups testedProcessingOfGroups) {
        final int nGroups = 5;
        final int nSuccessfulStudentsInGroup = 2;
        final int nOfAllStudents = 10;
        Set<String> expectedSetOfGroupNames = InputDataForDepartment.getNamesForGroupsWithSuccessfulStudents(nGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithGroupsWithSuccessfulStudents(
                nSuccessfulStudentsInGroup, nOfAllStudents, expectedSetOfGroupNames, nGroups);
        assertEquals(expectedSetOfGroupNames,
                testedProcessingOfGroups.getAllGroupsWithMoreThenOneSuccessfulStudent(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllGroupsWithMoreThenOneSuccessfulStudentOnNullAsParameter(ProcessingOfGroups testedProcessingOfGroups) {
        Set<String> expectedSetOfGroupNames = new HashSet<>();
        assertEquals(expectedSetOfGroupNames,
                testedProcessingOfGroups.getAllGroupsWithMoreThenOneSuccessfulStudent(null));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllGroupsWithMoreThenOneSuccessfulStudentOnIncorrectDepartment(ProcessingOfGroups testedProcessingOfGroups) {
        final int nGroups = 5;
        final int nSuccessfulStudentsInGroup = 2;
        final int nOfAllStudents = 10;
        Set<String> expectedSetOfGroupNames = InputDataForDepartment.getNamesForGroupsWithSuccessfulStudents(nGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithGroupsWithSuccessfulStudents(
                nSuccessfulStudentsInGroup, nOfAllStudents, expectedSetOfGroupNames, nGroups);
        DepartmentTestCases.Negative.addToDepartmentGroupAsNull(testedDepartment);
        assertEquals(expectedSetOfGroupNames,
                testedProcessingOfGroups.getAllGroupsWithMoreThenOneSuccessfulStudent(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllGroupsWithMoreThenOneSuccessfulStudentOnDepartmentWithIncorrectGroup(ProcessingOfGroups testedProcessingOfGroups) {
        final int nGroups = 5;
        final int nSuccessfulStudentsInGroup = 2;
        final int nOfAllStudents = 10;
        Set<String> expectedSetOfGroupNames = InputDataForDepartment.getNamesForGroupsWithSuccessfulStudents(nGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithGroupsWithSuccessfulStudents(
                nSuccessfulStudentsInGroup, nOfAllStudents, expectedSetOfGroupNames, nGroups);
        DepartmentTestCases.Negative.addToDepartmentIncorrectGroup(testedDepartment);
        assertEquals(expectedSetOfGroupNames,
                testedProcessingOfGroups.getAllGroupsWithMoreThenOneSuccessfulStudent(testedDepartment));
    }

    @Test
    @Parameters(method = "getTestedProcessingClasses")
    public void testGetAllGroupsWithMoreThenOneSuccessfulStudentOnDepartmentWithIncorrectStudent(ProcessingOfGroups testedProcessingOfGroups) {
        final int nGroups = 5;
        final int nSuccessfulStudentsInGroup = 2;
        final int nOfAllStudents = 10;
        Set<String> expectedSetOfGroupNames = InputDataForDepartment.getNamesForGroupsWithSuccessfulStudents(nGroups);
        testedDepartment = DepartmentTestCases.Positive.getDepartmentWithGroupsWithSuccessfulStudents(
                nSuccessfulStudentsInGroup, nOfAllStudents, expectedSetOfGroupNames, nGroups);
        DepartmentTestCases.Negative.addToGroupIncorrectStudent(testedDepartment);
        assertEquals(expectedSetOfGroupNames,
                testedProcessingOfGroups.getAllGroupsWithMoreThenOneSuccessfulStudent(testedDepartment));
    }

}
