package com.custom.entity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import static org.junit.Assert.*;

/**
 * Created by olga on 19.02.15.
 */
@Ignore
public class DepartmentCascadeOperationsTest {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("department");
    private EntityManager em;
    private static final String QUERY_GET_ALL_DEPS = "SELECT d FROM Department d";

    @Before
    public void setUp() {
        em = factory.createEntityManager();
        em.getTransaction().begin();
        Query dropQuery = em.createNativeQuery("DELETE FROM department_group;");
        dropQuery.executeUpdate();
        dropQuery = em.createNativeQuery("DELETE FROM department;");
        dropQuery.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Persist
    @Test
    public void testPersistWithOneToManyRelationWithCascadePersistInitialPersist() {
        Department department = new Department("tested department");
        DepartmentGroup group = new DepartmentGroup("tested group", 1);
        group.setDepartment(department);

        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(group);
        em.getTransaction().commit();
        em.refresh(department);

        assertTrue(em.contains(department));
        assertTrue(em.contains(group));

        em.close();
    }

    @Test
    public void testPersistWithOneToManyRelationWithCascadePersistChangingManagedEntity() {
        Department department = new Department("tested department");
        DepartmentGroup group = new DepartmentGroup("tested group", 1);

        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        department = em.find(Department.class, department.getId());
        group.setDepartment(department);
        department.getGroups().add(group);
        em.getTransaction().commit();

        assertTrue(em.contains(department));
        assertTrue(em.contains(group));

        em.close();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    // Remove

    @Test
    public void testRemoveWithOneToManyRelationWithCascadeRemove() {
        Department department = new Department("tested department");
        DepartmentGroup group = new DepartmentGroup("tested group", 1);
        group.setDepartment(department);
        em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(group);
        em.getTransaction().commit();
        em.refresh(department);
        em.refresh(group);
        assertTrue(em.contains(department));
        assertTrue(em.contains(group));

        em.getTransaction().begin();
        assertTrue(department.getGroups().remove(group));
        em.getTransaction().commit();
        assertFalse(department.getGroups().contains(group));
        assertTrue(em.contains(group));


        em.getTransaction().begin();
        assertTrue(department.getGroups().add(group));
        em.getTransaction().commit();
        assertTrue(department.getGroups().contains(group));
        assertTrue(em.contains(group));


        em.getTransaction().begin();
        em.remove(department);
        em.getTransaction().commit();
        assertFalse(em.contains(department));
        assertFalse(em.contains(group));

        em.close();
    }


    //////////////////////////////////////////////////////////////////////////////////////
    // Merge
    @Test
    public void testMergeWithOneToManyRelationWithCascadeMerge() {
        Department department = new Department("tested department");
        DepartmentGroup group = new DepartmentGroup("tested group", 1);
        group.setDepartment(department);

        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(group);
        em.getTransaction().commit();
        em.refresh(department);
        assertEquals(1, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().size());
        assertEquals(group, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().get(0));
        group.setName("changed name of tested group");
        em.getTransaction().begin();
        department = em.merge(department);
        em.getTransaction().commit();
        assertEquals(1, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().size());
        assertEquals(group, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().get(0));
        em.close();
    }
}
