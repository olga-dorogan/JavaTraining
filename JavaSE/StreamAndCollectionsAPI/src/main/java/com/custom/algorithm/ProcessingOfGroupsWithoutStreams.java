package com.custom.algorithm;

import com.custom.data.Group;
import com.custom.data.MilitaryAge;
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
        if (!validateDepartment(department)) {
            return 0;
        }
        int result = 0;
        for (Group group : department) {
            if (validateGroup(group) && isAnyOfStudentsPoor(group.getListOfStudents())) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashMap<>();
        }
        Map<String, Double> progressOfGroup = new HashMap<>(department.size());
        double sum, avg;
        int count;
        for (Group group : department) {
            if (validateGroup(group)) {
                sum = 0;
                count = 0;
                List<Student> listOfStudents = group.getListOfStudents();
                for (Student student : listOfStudents) {
                    if (validateStudent(student)) {
                        Collection<Integer> rating = student.getRatingOnSubjects().values();
                        for (Integer mark : rating) {
                            sum += mark;
                            count++;
                        }
                    }
                }
                avg = sum / count;
                if(progressOfGroup.get(group.getDescription())!= null){
                    avg += progressOfGroup.get(group.getDescription());
                    avg /= 2;
                }
                progressOfGroup.put(group.getDescription(), avg);
            }
        }
        return progressOfGroup;
    }

    @Override
    public Set<String> getGroupNamesWithOnlyMen(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashSet<>();
        }
        Set<String> groupsWithoutWomen = new HashSet<>();
        for (Group group : department) {
            if (validateGroup(group) && isAllStudentsMen(group.getListOfStudents())) {
                groupsWithoutWomen.add(group.getDescription());
            }
        }
        return groupsWithoutWomen;
    }

    @Override
    public List<Student> getAllStudentsFromDepartment(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new ArrayList<>();
        }
        List<Student> listOfAllStudentsFromDepartment = new ArrayList<>();
        for (Group group : department) {
            if (validateGroup(group)) {
                listOfAllStudentsFromDepartment.addAll(group.getListOfStudents());
            }
        }
        return listOfAllStudentsFromDepartment;
    }

    @Override
    public Map<String, Double> getAvgRatingForEverySubject(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashMap<>(0);
        }
        Map<String, Double> resultMap = new HashMap<>();
        Set<String> setOfSubjects = new HashSet<>();
        // 1. get set of all subjects for department
        for (Group group : department) {
            if (validateGroup(group)) {
                List<Student> listOfStudents = group.getListOfStudents();
                for (Student student : listOfStudents) {
                    if (validateStudent(student)) {
                        Set<String> subjects = student.getRatingOnSubjects().keySet();
                        setOfSubjects.addAll(subjects);
                    }
                }
            }
        }
        // 2. for every subject get average mark
        for (String subject : setOfSubjects) {
            double sum = 0;
            int count = 0;
            for (Group group : department) {
                if (validateGroup(group)) {
                    List<Student> listOfStudents = group.getListOfStudents();
                    for (Student student : listOfStudents) {
                        if (validateStudent(student)) {
                            Integer markOnSubject = student.getRatingOnSubjects().get(subject);
                            if (markOnSubject != null) {
                                sum += markOnSubject;
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        resultMap.put(subject, 0.0);
                    } else {
                        resultMap.put(subject, sum / count);
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    public List<Student> getAllMilitaryAgeStudents(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new ArrayList<>(0);
        }
        List<Student> resultList = new ArrayList<>();
        for (Group group : department) {
            if (validateGroup(group)) {
                List<Student> listOfStudents = group.getListOfStudents();
                for (Student student : listOfStudents) {
                    if (validateStudentWithoutRating(student) &&
                            (student.getSex() == Sex.MALE) && MilitaryAge.isMilitaryAge(student.getAge())) {
                        resultList.add(student);
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public Set<String> getAllSubjects(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashSet<>(0);
        }
        Set<String> resultSet = new HashSet<>();
        for (Group group : department) {
            if(validateGroup(group)) {
                List<Student> listOfStudents = group.getListOfStudents();
                for (Student student : listOfStudents) {
                    if(validateStudent(student)) {
                        Set<String> subjectOfStudent = student.getRatingOnSubjects().keySet();
                        resultSet.addAll(subjectOfStudent);
                    }
                }
            }
        }
        return resultSet;
    }

    @Override
    public Set<String> getAllGroupsWithMoreThenOneSuccessfulStudent(Set<Group> department) {
        if (!validateDepartment(department)) {
            return new HashSet<>(0);
        }
        final int thresholdOfSuccessfulStudents = 2;
        Set<String> resultSet = new HashSet<>();
        for (Group group : department) {
            if(validateGroup(group)) {
                int nSuccessfulStudents = 0;
                List<Student> listOfStudents = group.getListOfStudents();
                for (Student student : listOfStudents) {
                    if (validateStudent(student) && isStudentSuccessful(student)) {
                        nSuccessfulStudents++;
                    }
                }
                if (nSuccessfulStudents >= thresholdOfSuccessfulStudents) {
                    resultSet.add(group.getName());
                }
            }
        }
        return resultSet;
    }

    private boolean isAnyOfStudentsPoor(List<Student> listOfStudents) {
        for (Student student : listOfStudents) {
            if (validateStudent(student)) {
                Collection<Integer> rating = student.getRatingOnSubjects().values();
                for (Integer mark : rating) {
                    if (ConvertMark.fromPercentToFourPoint(mark) < 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isStudentSuccessful(Student student) {
        Collection<Integer> rating = student.getRatingOnSubjects().values();
        for (Integer mark : rating) {
            if (ConvertMark.fromPercentToFourPoint(mark) != 5) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllStudentsMen(List<Student> listOfStudents) {
        for (Student student : listOfStudents) {
            if (validateStudentWithoutRating(student) && student.getSex() == Sex.FEMALE)
                return false;
        }
        return true;
    }


}
