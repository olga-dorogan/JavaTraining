package com.custom.entity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.*;

import static org.junit.Assert.*;

/**
 * Created by olga on 18.02.15.
 */
@Ignore
public class DepartmentLifecycleTest {
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

    @Test
    public void testPersistShouldChangeManagedEntityByCallingSetters() {
        final String initialDescription = "Tested department", changedDescription = "Changed department";
        Department department = new Department(initialDescription);
        em = factory.createEntityManager();
        em.getTransaction().begin();
        assertEquals(initialDescription, department.getDescription());
        em.persist(department);
        department.setDescription(changedDescription);
        em.getTransaction().commit();

        Department departmentFromDb = em.find(Department.class, department.getId());
        assertEquals(changedDescription, departmentFromDb.getDescription());
        em.close();

    }

    @Test
    public void testPersistOnEqualDepartmentsInTheSameTransaction() {
        Department department = new Department("Tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.persist(department);
        em.getTransaction().commit();
        assertEquals(1, em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size());
        em.clear();
    }

    @Test
    public void testPersistOnEqualDepartmentsInDifferentTransactionsButInTheSamePersistentContext() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        assertEquals(1, em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size());
        em.close();
    }

    @Test(expected = Exception.class)
    public void testPersistOnEqualDepartmentsInDifferentTransactionsAfterClearingPersistentContext() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.close();
    }

    @Test(expected = Exception.class)
    public void testPersistOnDetachedEntityShouldThrowEntityExistsException() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        assertTrue(em.contains(department));
        em.getTransaction().begin();
        em.detach(department);
        em.getTransaction().commit();
        assertFalse(em.contains(department));
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // find(),createQuery() methods -- if entity isn't found in the PersistentContext
    // they make query to the DB
    @Test
    public void testPersistAndFindOnEqualDepartmentsAfterClearingPersistentContext() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.clear();

        assertNotEquals(null, em.find(Department.class, department.getId()));
        em.close();
    }

    @Test
    public void testPersistAndQueryOnEqualDepartmentsAfterClearingPersistentContext() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.clear();

        assertNotEquals(0, em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size());
        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Detach

    @Test
    public void testDetach() {
        final String initDescription = "init", changedDescription = "changed";
        Department department = new Department(initDescription);
        em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(department);
        assertTrue(em.contains(department));
        Query getDepartmentsQuery = em.createQuery(QUERY_GET_ALL_DEPS);
        assertEquals(initDescription, ((Department) getDepartmentsQuery.getResultList().get(0))
                .getDescription());
        em.getTransaction().commit();

        em.getTransaction().begin();
        // ?????????????????????????????????????????????????????
        // if description is changed before detaching, it's work
        // but if after - isn't work
        department.setDescription(changedDescription);
        em.detach(department);
        assertFalse(em.contains(department));
        em.getTransaction().commit();

        assertNotEquals(changedDescription, ((Department) em.createQuery(QUERY_GET_ALL_DEPS).getResultList().get(0))
                .getDescription());
        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Remove

    @Test
    public void testRemove() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        assertTrue(em.contains(department));
        assertEquals(1, em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size());
        em.remove(department);
        assertFalse(em.contains(department));
        assertEquals(0, em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size());
        em.getTransaction().commit();
        assertEquals(0, em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size());
        em.close();
    }

    // ????????????????????????????
    // why it must be in different transactions?
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveOnDetachedEntityShouldThrowIllegalArgumentException() {
        Department department = new Department("tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.detach(department);
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(department);
        em.getTransaction().commit();
        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Merge
    @Test
    public void testMergeOnDetachedEntity() {
        Department department = new Department("Tested department");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.detach(department);
        em.getTransaction().commit();
        assertFalse(em.contains(department));

        em.getTransaction().begin();
        department = em.merge(department);
        em.getTransaction().commit();
        assertTrue(em.contains(department));
        em.close();
    }

    @Test
    public void testMergeManagingEntityAfterMerge() {
        final String initDescription = "init", changedDescription = "changed";
        Department department = new Department(initDescription);
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.clear();
        department.setDescription(changedDescription);
        department = em.find(Department.class, department.getId());
        assertNotEquals(changedDescription, department.getDescription());
        em.clear();
        em.getTransaction().begin();
        department = em.merge(department);
        department.setDescription(changedDescription);
        em.getTransaction().commit();
        assertEquals(changedDescription, department.getDescription());

        em.close();
    }

    @Test
    public void testMerge() {
        final String initDescription = "init", changedDescription = "changed";
        Department department = new Department(initDescription);
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        em.clear();
        department.setDescription(changedDescription);
        em.getTransaction().begin();
        department = em.merge(department);
        em.getTransaction().commit();
        assertEquals(changedDescription, department.getDescription());

        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Flush
    /// entityManager can be in one of two flush modes:
    ///     AUTO -- flushing before queries
    ///     COMMIT -- flushing after commit() or flush() calling

    @Test
    public void testFlushModes() {
        Department department1 = new Department("Tested department 1");
        Department department2 = new Department("Tested department 2");
        em = factory.createEntityManager();
        em.setFlushMode(FlushModeType.COMMIT);
        em.getTransaction().begin();
        em.persist(department1);
        System.out.println("flush() -- mode: " + em.getFlushMode());
        em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size();
        System.out.println("flush(): before commit()");
        em.getTransaction().commit();
        System.out.println("flush(): after commit()");

        em.setFlushMode(FlushModeType.AUTO);
        em.getTransaction().begin();
        em.persist(department2);
        System.out.println("flush() -- mode: " + em.getFlushMode());
        em.createQuery(QUERY_GET_ALL_DEPS).getResultList().size();
        System.out.println("flush(): before commit()");
        em.getTransaction().commit();
        System.out.println("flush(): after commit()");

        em.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Transaction management
    @Test (expected = Exception.class)
    public void testTransaction() throws Exception {
        em = factory.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(null);
            em.getTransaction().commit();
        } catch (Exception e) {
            assertTrue(em.getTransaction().isActive());
            em.getTransaction().rollback();
            assertFalse(em.getTransaction().isActive());
            throw e;
        } finally {
            em.close();
        }
    }
}
