package com.custom.service;

import com.custom.entity.Department;
import com.custom.entity.DepartmentGroup;
import org.junit.*;

import javax.persistence.*;

import static org.junit.Assert.*;

public class DepartmentGroupServiceTest {
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static DepartmentGroupService service;

    private static final int DEPARTMENT_CNT = 2;
    private Department[] department = new Department[DEPARTMENT_CNT];

    @BeforeClass
    public static void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("department");
        em = factory.createEntityManager();
        service = new DepartmentGroupService(em);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        em.close();
        factory.close();
    }

    @Before
    public void addDepartments() {
        department[0] = new Department("department 0");
        department[1] = new Department("department 1");
        em.getTransaction().begin();
        em.persist(department[0]);
        em.persist(department[1]);
        em.getTransaction().commit();
    }

    @After
    public void clearWorkspace() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        clearTables();
    }

    @Test
    public void testAddToDepartment() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        service.addToDepartment(department[0], group);
        em.getTransaction().commit();
        em.refresh(department[0]);
        assertEquals(1, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().size());
        assertEquals(department[0], group.getDepartment());
        assertEquals(1, department[0].getGroups().size());
        assertEquals(group, department[0].getGroups().get(0));
    }

    @Test
    public void testAddToNonExistsDepartmentShouldPersistDepartment() throws Exception {
        Department nonExistDepartment = new Department("nont exist department");
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        service.addToDepartment(nonExistDepartment, group);
        em.getTransaction().commit();
        em.refresh(nonExistDepartment);
        assertTrue(em.createQuery("SELECT g FROM Department g").getResultList().contains(nonExistDepartment));
    }

    @Test(expected = EntityExistsException.class)
    public void testAddOnDuplicateGroupsAndTheSameDepartmentShouldThrowEntityExistsException() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        service.addToDepartment(department[0], group);
        service.addToDepartment(department[0], group);
        em.getTransaction().commit();
    }

    @Test(expected = EntityExistsException.class)
    public void testAddOnDuplicateGroupsAndDifferentDepartmentsShouldThrowEntityExistsException() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        service.addToDepartment(department[0], group);
        service.addToDepartment(department[1], group);
        em.getTransaction().commit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOnNullDepartmentShouldThrowIllegalArgumentException() {
        em.getTransaction().begin();
        service.addToDepartment(null, new DepartmentGroup("group", 1));
        em.getTransaction().commit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOnNullGroupShouldThrowIllegalArgumentException() {
        em.getTransaction().begin();
        service.addToDepartment(department[0], null);
        em.getTransaction().commit();
    }

    @Test
    public void testMoveToDepartment() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();
        em.refresh(department[0]);
        assertEquals(1, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().size());
        assertEquals(group, em.createQuery("SELECT g FROM DepartmentGroup g").getSingleResult());
        assertEquals(1, department[0].getGroups().size());
        assertEquals(group, department[0].getGroups().get(0));
        assertNotEquals(1, department[1].getGroups().size());

        em.getTransaction().begin();
        service.moveToDepartment(department[1], group);
        em.getTransaction().commit();
        em.refresh(department[0]);
        em.refresh(department[1]);
        assertNotEquals(1, department[0].getGroups().size());
        assertEquals(1, department[1].getGroups().size());
        assertEquals(group, department[1].getGroups().get(0));
    }


    @Test(expected = EntityNotFoundException.class)
    public void testMoveToDepartmentOnNonExistDepartmentShouldThrowEntityNotFoundException() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();
        em.refresh(department[0]);
        em.getTransaction().begin();
        service.moveToDepartment(new Department("non-exist department"), group);
        em.getTransaction().commit();
    }

    @Test(expected = EntityNotFoundException.class)
    public void testMoveToDepartmentOnNonExistGroupShouldThrowEntityNotFoundException() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        service.moveToDepartment(department[0], group);
        em.getTransaction().commit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveToDepartmentOnNullDepartmentShouldThrowIllegalArgumentException() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();
        em.refresh(department[0]);
        em.getTransaction().begin();
        service.moveToDepartment(null, group);
        em.getTransaction().commit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveToDepartmentOnNullGroupShouldThrowIllegalArgumentException() throws Exception {
        em.getTransaction().begin();
        service.moveToDepartment(department[0], null);
        em.getTransaction().commit();
    }

    private void clearTables() {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM student;").executeUpdate();
        em.createNativeQuery("DELETE FROM department_group;").executeUpdate();
        em.createNativeQuery("DELETE FROM department;").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(0, service.getAll().size());
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();

        assertEquals(1, service.getAll().size());
        assertTrue(service.getAll().contains(group));
    }

    @Test
    public void testUpdate() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();

        group.setName("update group name");
        em.getTransaction().begin();
        DepartmentGroup groupUpdated = service.update(group);
        em.getTransaction().commit();

        assertEquals(group, groupUpdated);
    }

    @Test
    public void testUpdateOnDepartmentFieldUpdating() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();

        group.setDepartment(department[1]);
        em.getTransaction().begin();
        DepartmentGroup groupUpdated = service.update(group);
        em.getTransaction().commit();
        em.refresh(department[0]);
        em.refresh(department[1]);

        assertEquals(group, groupUpdated);
        assertNotEquals(1, department[0].getGroups().size());
        assertEquals(1, department[1].getGroups().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateOnNonExistGroupShouldThrowEntityNotFoundException() throws Exception {
        em.getTransaction().begin();
        service.update(new DepartmentGroup("non-exist group", 1));
        em.getTransaction().commit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateOnNullShouldThrowIllegalArgumentException() throws Exception {
        em.getTransaction().begin();
        service.update(null);
        em.getTransaction().commit();
    }

    @Test
    public void testDelete() throws Exception {
        DepartmentGroup group = new DepartmentGroup("group", 1);
        em.getTransaction().begin();
        group.setDepartment(department[0]);
        em.persist(group);
        em.getTransaction().commit();

        em.getTransaction().begin();
        service.delete(group);
        em.getTransaction().commit();
        em.refresh(department[0]);

        assertEquals(0, em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().size());
        assertEquals(DEPARTMENT_CNT, em.createQuery("SELECT d FROM Department d").getResultList().size());
        assertEquals(0, department[0].getGroups().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void  testDeleteOnNullShouldThrowIllegalArgumentException() {
        em.getTransaction().begin();
        service.delete(null);
        em.getTransaction().commit();
    }

    @Test (expected = EntityNotFoundException.class)
    public void testDeleteOnNonExistGroupShouldThrowEntityNotFoundException() {
        em.getTransaction().begin();
        service.delete(new DepartmentGroup("non-exist group", 1));
        em.getTransaction().commit();
    }
}