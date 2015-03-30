package com.custom.model.service;

import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

/**
 * Created by olga on 29.03.15.
 */
@Stateless
public class UniversityService {
    @PersistenceContext
    private EntityManager em;

    public List<DepartmentGroup> getGroupsFromDepartment(long departmentId, boolean isAllDeps) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentGroup> cq = cb.createQuery(DepartmentGroup.class);
        Metamodel m = em.getMetamodel();
        EntityType<DepartmentGroup> DepartmentGroup_ = m.entity(DepartmentGroup.class);
        Root<DepartmentGroup> departmentGroupRoot = cq.from(DepartmentGroup_);
        if (!isAllDeps) {
            Join<DepartmentGroup, Department> departmentJoin;
            departmentJoin = departmentGroupRoot.join("department");
        }
        cq.select(departmentGroupRoot).distinct(true);
        TypedQuery<DepartmentGroup> groupTypedQuery = em.createQuery(cq);
        return groupTypedQuery.getResultList();
    }
}
