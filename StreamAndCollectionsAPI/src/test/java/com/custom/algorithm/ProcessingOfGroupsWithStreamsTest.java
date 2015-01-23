package com.custom.algorithm;

import com.custom.data.Group;
import static org.junit.Assert.*;

import com.custom.data.Student;
import org.junit.Test;

import java.util.*;

public class ProcessingOfGroupsWithStreamsTest  {
    ProcessingOfGroups testedProcessingOfGroups = new ProcessingOfGroupsWithStreams();
    Set<Group> testedDepartment;

    @Test
    public void testGetAvgProgressForEveryGroup() throws Exception {
        final int nGroups = 5;
        final Map<String, Double> expectedResult = new HashMap<>(nGroups);
        for (int i = 0; i < nGroups; i++) {
            expectedResult.put("Group " + i, 100.0 - i);
        }
        testedDepartment = HelperClass.getDepartmentWithAvgProgressForEveryGroup(expectedResult);
        assertEquals(expectedResult, testedProcessingOfGroups.getAvgProgressForEveryGroup(testedDepartment));
    }
    @Test
    public void testGetCountOfGroupsWithPoorStudents() throws Exception {
        final int nPoorGroups = 3;
        testedDepartment = HelperClass.getDepartmentWithPoorGroups(nPoorGroups);
        assertEquals(nPoorGroups, testedProcessingOfGroups.getCountOfGroupsWithPoorStudents(testedDepartment));
    }

    @Test
    public void testGetGroupNamesWithOnlyMen() throws Exception {
        final int nMenGroups = 4;
        final Set<String> expectedGroupNames = new HashSet<>(nMenGroups);
        for (int i = 0; i < nMenGroups; i++) {
            expectedGroupNames.add("Group_with_only_men" + i);
        }
        testedDepartment = HelperClass.getDepartmentWithOnlyMenGroups(expectedGroupNames);
        assertEquals(expectedGroupNames, testedProcessingOfGroups.getGroupNamesWithOnlyMen(testedDepartment));
    }

    @Test
    public void testGetAllStudentsFromDepartment() throws Exception {
        final int nStudentsInGroup = 5;
        final int nGroups = 5;
        List<Student> expectedListOfStudents = HelperClass.getListOfStudentsForAllDepartment(nStudentsInGroup, nGroups);
        testedDepartment = HelperClass.getDepartmentWithStudents(expectedListOfStudents, nStudentsInGroup);
        assertTrue(HelperClass
                .compareListsToEquality(
                        expectedListOfStudents,
                        testedProcessingOfGroups.getAllStudentsFromDepartment(testedDepartment)));
    }
}