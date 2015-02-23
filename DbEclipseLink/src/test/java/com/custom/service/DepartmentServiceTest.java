package com.custom.service;

import com.custom.entity.Department;
import com.custom.entity.DepartmentGroup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by olga on 20.02.15.
 */
public class DepartmentServiceTest {

    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static DepartmentService service;

    @BeforeClass
    public static void setUp() {
        factory = Persistence.createEntityManagerFactory("department");
        em = factory.createEntityManager();
        service = new DepartmentService(em);
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        factory.close();
    }

    @After
    public void clearWorkspace() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        clearTables();
    }

    @Test
    public void testGetAll() {
        Department department_1 = new Department("department 1");
        Department department_2 = new Department("department 2");
        em.getTransaction().begin();
        em.persist(department_1);
        em.persist(department_2);
        em.getTransaction().commit();
        List<Department> departments = service.getAll();
        assertEquals(2, departments.size());
        assertTrue(departments.contains(department_1));
        assertTrue(departments.contains(department_2));
    }

    @Test
    public void testAdd() {
        Department department_1 = new Department("department 1");
        em.getTransaction().begin();
        service.add(department_1);
        em.getTransaction().commit();
        assertNotEquals(null, em.find(Department.class, department_1.getId()));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddOnNullShouldThrowIllegalArgumentException() {
        em.getTransaction().begin();
        service.add(null);
        em.getTransaction().commit();
    }

    @Test (expected = EntityExistsException.class)
    public void testAddOnDuplicateDepartmentsShouldThrowEntityExistsException() {
        Department department = new Department("department");
        em.getTransaction().begin();
        service.add(department);
        service.add(department);
        em.getTransaction().commit();
    }

    @Test
    public void testUpdate() {
        final String updatedDescription = "updated department 1";
        Department department_1 = new Department("department 1");
        em.getTransaction().begin();
        em.persist(department_1);
        em.getTransaction().commit();

        department_1.setDescription(updatedDescription);
        em.getTransaction().begin();
        service.update(department_1);
        em.getTransaction().commit();

        assertEquals("updated department 1", em.find(Department.class, department_1.getId()).getDescription());
    }
    @Test (expected = IllegalArgumentException.class)
    public void testUpdateOnNullShouldThrowIllegalArgumentException() {
        em.getTransaction().begin();
        service.update(null);
        em.getTransaction().commit();
    }

    @Test (expected = EntityNotFoundException.class)
    public void testUpdateOnNonExistsDepartmentShouldThrowEntityNotFoundException() {
        em.getTransaction().begin();
        service.update(new Department("department"));
        em.getTransaction().commit();
    }

    @Test
    public void testDelete() {
        Department department_1 = new Department("department 1");
        Department department_2 = new Department("department 2");
        em.getTransaction().begin();
        em.persist(department_1);
        em.persist(department_2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        service.delete(department_1);
        em.getTransaction().commit();

        List<Department> departments = em.createQuery("SELECT  d FROM Department d", Department.class).getResultList();
        assertEquals(1, departments.size());
        assertFalse(departments.contains(department_1));
        assertTrue(departments.contains(department_2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteOnNullShouldThrowIllegalArgumentException() {
        em.getTransaction().begin();
        service.delete(null);
        em.getTransaction().commit();
    }

    @Test (expected = EntityNotFoundException.class)
    public void testDeleteOnNonExistsDepartment() {
        em.getTransaction().begin();
        service.delete(new Department("department"));
        em.getTransaction().commit();
    }

    @Test
    public void testDeleteOnDepartmentWithGroups() {
        Department department_1 = new Department("department 1");
        Department department_2 = new Department("department 2");
        DepartmentGroup group_1 = new DepartmentGroup("group 1", 1);
        DepartmentGroup group_2 = new DepartmentGroup("group 2", 1);

        em.getTransaction().begin();
        group_1.setDepartment(department_1);
        group_2.setDepartment(department_2);
        em.persist(group_1);
        em.persist(group_2);
        em.getTransaction().commit();
        em.refresh(department_1);
        em.refresh(department_2);

        assertEquals(2, em.createQuery("SELECT d FROM Department d").getResultList().size());
        assertEquals(2, em.createQuery("SELECT d FROM DepartmentGroup d").getResultList().size());
        assertEquals(1, department_1.getGroups().size());
        assertEquals(1, department_2.getGroups().size());

        em.getTransaction().begin();
        service.delete(department_1);
        em.getTransaction().commit();

        assertEquals(1, em.createQuery("SELECT d FROM Department d").getResultList().size());
        assertEquals(1, em.createQuery("SELECT d FROM DepartmentGroup d").getResultList().size());
        assertEquals(1, department_2.getGroups().size());
    }

    private void clearTables() {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM student;").executeUpdate();
        em.createNativeQuery("DELETE FROM department_group;").executeUpdate();
        em.createNativeQuery("DELETE FROM department;").executeUpdate();
        em.getTransaction().commit();
    }
}
