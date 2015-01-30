package com.custom.algorithm.help;

import com.custom.data.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 25.01.15.
 */
public class CheckResults {
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
    public static void setGroupDescriptorsAsNameAndCourse(){

    }
}
