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
        if (department == null) {
            return 0;
        }
        int result = 0;
        for (Group group : department) {
            if (isAnyOfStudentsPoor(group.getListOfStudents())) {
                result++;
            }
        }
        return result;
    }

    private boolean isAnyOfStudentsPoor(List<Student> listOfStudents) {
        for (Student student : listOfStudents) {
            Collection<Integer> rating = student.getRatingOnSubjects().values();
            for (Integer mark : rating) {
                if (ConvertMark.fromPercentToFourPoint(mark) < 3) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Double> getAvgProgressForEveryGroup(Set<Group> department) {
        if (department == null) {
            return new HashMap<>();
        }
        Map<String, Double> progressOfGroup = new HashMap<>(department.size());
        double sum;
        int count;
        for (Group group : department) {
            sum = 0;
            count = 0;
            List<Student> listOfStudents = group.getListOfStudents();
            for (Student student : listOfStudents) {
                Collection<Integer> rating = student.getRatingOnSubjects().values();
                for (Integer mark : rating) {
                    sum += mark;
                    count++;
                }
            }
            progressOfGroup.put(group.getName(), sum / count);
        }
        return progressOfGroup;
    }

    @Override
    public Set<String> getGroupNamesWithOnlyMen(Set<Group> department) {
        if (department == null) {
            return new HashSet<>();
        }
        Set<String> groupsWithoutWomen = new HashSet<>();
        for (Group group : department) {
            if (isAllStudentsMen(group.getListOfStudents())) {
                groupsWithoutWomen.add(group.getName());
            }
        }
        return groupsWithoutWomen;
    }

    @Override
    public List<Student> getAllStudentsFromDepartment(Set<Group> department) {
        List<Student> listOfAllStudentsFromDepartment = new ArrayList<>();
        for (Group group : department) {
            listOfAllStudentsFromDepartment.addAll(group.getListOfStudents());
        }
        return listOfAllStudentsFromDepartment;
    }

    @Override
    public Map<String, Double> getAvgRatingForEverySubject(Set<Group> department) {
        if (department == null) {
            return new HashMap<>(0);
        }
        Map<String, Double> resultMap = new HashMap<>();
        Set<String> setOfSubjects = new HashSet<>();
        // 1. get set of all subjects for department
        for (Group group : department) {
            List<Student> listOfStudents = group.getListOfStudents();
            for (Student student : listOfStudents) {
                Set<String> subjects = student.getRatingOnSubjects().keySet();
                setOfSubjects.addAll(subjects);
            }
        }
        // 2. for every subject get average mark
        for (String subject : setOfSubjects) {
            double sum = 0;
            int count = 0;
            for (Group group : department) {
                List<Student> listOfStudents = group.getListOfStudents();
                for (Student student : listOfStudents) {
                    Integer markOnSubject = student.getRatingOnSubjects().get(subject);
                    if (markOnSubject != null) {
                        sum += markOnSubject;
                        count++;
                    }
                }
                if (count == 0) {
                    resultMap.put(subject, 0.0);
                } else {
                    resultMap.put(subject, sum / count);
                }
            }
        }
        return resultMap;
    }

    @Override
    public List<Student> getAllMilitaryAgeStudents(Set<Group> department) {
        if (department == null) {
            return new ArrayList<>(0);
        }
        List<Student> resultList = new ArrayList<>();
        for (Group group : department) {
            List<Student> listOfStudents = group.getListOfStudents();
            for (Student student : listOfStudents) {
                if ((student.getSex() == Sex.Male) && MilitaryAge.isMilitaryAge(student.getAge())) {
                    resultList.add(student);
                }
            }
        }
        return resultList;
    }

    @Override
    public Set<String> getAllSubjects(Set<Group> department) {
        if (department == null) {
            return new HashSet<>(0);
        }
        Set<String> resultSet = new HashSet<>();
        for (Group group : department) {
            List<Student> listOfStudents = group.getListOfStudents();
            for (Student student : listOfStudents) {
                Set<String> subjectOfStudent = student.getRatingOnSubjects().keySet();
                resultSet.addAll(subjectOfStudent);
            }
        }
        return resultSet;
    }

    @Override
    public Set<Group> getAllGroupsWithMoreThenOneSuccessfulStudent(Set<Group> department) {
        if (department == null) {
            return new HashSet<>(0);
        }
        final int thresholdOfSuccessfulStudents = 2;
        Set<Group> resultSet = new HashSet<>();
        for (Group group : department) {
            int nSuccessfulStudents = 0;
            List<Student> listOfStudents = group.getListOfStudents();
            for (Student student : listOfStudents) {
                if (isStudentSuccessful(student)) {
                    nSuccessfulStudents++;
                }
            }
            if (nSuccessfulStudents >= thresholdOfSuccessfulStudents) {
                resultSet.add(group);
            }
        }
        return resultSet;
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
            if (student.getSex() == Sex.Female)
                return false;
        }
        return true;
    }


}
