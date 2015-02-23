package com.custom.entity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 21.02.15.
 */
@Ignore
public class RelationsOnDifferentDBTest {
    private static EntityManagerFactory factoryH2;
    private static EntityManager em;

    @BeforeClass
    public static void setUp() {
        factoryH2 = Persistence.createEntityManagerFactory("department");
        em = factoryH2.createEntityManager();
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        factoryH2.close();
    }

    @Test
    public void testPersist() {
        Department department = new Department("department");
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department);
        em.persist(group);
        em.getTransaction().commit();
        em.getEntityManagerFactory().getCache().evictAll();
        em.clear();

        //em = factoryH2.createEntityManager();
        department = em.find(Department.class, department.getId());
        assertEquals(1, em.createQuery("SELECT d FROM Department d").getResultList().size());
        assertEquals(1, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().size());
        assertEquals(1, department.getGroups().size());
    }


}
