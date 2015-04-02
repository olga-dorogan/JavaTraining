package com.custom.model.service;

import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by olga on 29.03.15.
 */
@Stateless
public class UniversityService {
    @PersistenceContext
    private EntityManager em;

    public List<Department> getDepartments() {
        return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    public List<DepartmentGroup> getGroupsFromDepartments(Collection<Long> departmentIds) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentGroup> query = cb.createQuery(DepartmentGroup.class);
        Root<DepartmentGroup> group = query.from(DepartmentGroup.class);
        query.select(group)
                .where(group.get("department").get("id").in(departmentIds));
        return em.createQuery(query).getResultList();
    }

    public List<Pair<String, Long>> getCntGroupsInDepartments(Collection<Long> departmentIds) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Department> departmentRoot = query.from(Department.class);
        Join<Department, DepartmentGroup> group = departmentRoot.join("groups");
        query.multiselect(departmentRoot, cb.count(group)).where(departmentRoot.get("id").in(departmentIds))
                .groupBy(departmentRoot);
        List<Object[]> queryResult = em.createQuery(query).getResultList();
        List<Pair<String, Long>> result = new ArrayList<>(queryResult.size());
        for (Object[] queryPair : queryResult) {
            result.add(new Pair<>(((Department)queryPair[0]).getDescription(), (Long)queryPair[1]));
        }
        return result;
    }

    public List<Pair<String, Long>> getCntStudentsInDepartments(Collection<Long> departmentIds) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Department> departmentRoot = query.from(Department.class);
        Join<Department, Student> student = departmentRoot.join("groups").join("students");
        query.multiselect(departmentRoot, cb.count(student)).where(departmentRoot.get("id").in(departmentIds))
                .groupBy(departmentRoot);
        List<Object[]> queryResult = em.createQuery(query).getResultList();
        List<Pair<String, Long>> result = new ArrayList<>(queryResult.size());
        for (Object[] queryPair : queryResult) {
            result.add(new Pair<>(((Department)queryPair[0]).getDescription(), (Long)queryPair[1]));
        }
        return result;
    }

    public static class Pair<T, U> {
        public final T firstValue;
        public final U secondValue;
        public Pair(T firstValue, U secondValue) {
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }

        public T getFirstValue() {
            return firstValue;
        }
        public U getSecondValue() {
            return secondValue;
        }
    }

}










