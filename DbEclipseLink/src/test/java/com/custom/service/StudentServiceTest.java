package com.custom.service;

import com.custom.entity.Department;
import com.custom.entity.DepartmentGroup;
import com.custom.entity.Student;
import org.junit.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class StudentServiceTest {

    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static StudentService service;

    private static final int GROUPS_CNT = 2;
    private DepartmentGroup[] groups = new DepartmentGroup[GROUPS_CNT];

    @BeforeClass
    public static void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("department");
        em = factory.createEntityManager();
        service = new StudentService(em);
    }


    @AfterClass
    public static void tearDown() throws Exception {
        em.close();
        factory.close();
    }

    @Before
    public void persistGroups() {
        Department department = new Department("department");
        em.getTransaction().begin();
        for (int i = 0; i < GROUPS_CNT; i++) {
            groups[i] = new DepartmentGroup("group " + i, 1);
            groups[i].setDepartment(department);
            em.persist(groups[i]);
        }
        em.getTransaction().commit();
        em.refresh(department);
    }

    @After
    public void clearWorkspace() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        clearTables();
    }


    @Test
    public void testAddToGroup() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        assertEquals(0, em.createQuery("SELECT s FROM Student s").getResultList().size());
        em.getTransaction().begin();
        service.addToGroup(groups[0], student);
        em.getTransaction().commit();
        em.refresh(groups[0]);
        assertEquals(1, em.createQuery("SELECT s FROM Student s").getResultList().size());
        assertEquals(1, groups[0].getStudents().size());
        assertEquals(student, groups[0].getStudents().get(0));
    }

    @Test
    public void testAddToGroupOnNonExistGroupShouldPersistGroup() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        DepartmentGroup group = new DepartmentGroup("non-exist group", 1);
        group.setDepartment(groups[0].getDepartment());
        em.getTransaction().begin();
        service.addToGroup(group, student);
        em.getTransaction().commit();
        assertTrue(em.createQuery("SELECT g FROM DepartmentGroup g").getResultList().contains(group));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddToGroupOnNullAsStudentShouldThrowIllegalArgumentException() throws Exception {
        DepartmentGroup group = new DepartmentGroup("non-exist group", 1);
        group.setDepartment(groups[0].getDepartment());
        em.getTransaction().begin();
        service.addToGroup(group, null);
        em.getTransaction().commit();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddToGroupOnNullAsGroupShouldThrowIllegalArgumentException() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        em.getTransaction().begin();
        service.addToGroup(null, student);
        em.getTransaction().commit();
    }

    @Test (expected = EntityExistsException.class)
    public void testAddToGroupOnDuplicateStudentsShouldThrowEntityExistsException() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        DepartmentGroup group = new DepartmentGroup("non-exist group", 1);
        group.setDepartment(groups[0].getDepartment());
        em.getTransaction().begin();
        service.addToGroup(group, student);
        service.addToGroup(group, student);
        em.getTransaction().commit();
    }

    @Test (expected = EntityExistsException.class)
    public void testAddToGroupOnStudentAttachedToAnotherGroupShouldThrowEntityExistsException() {
        Student student = new Student("Name", "Surname", 18);
        em.getTransaction().begin();
        student.setDepartmentGroup(groups[0]);
        em.persist(student);
        em.getTransaction().commit();
        em.refresh(groups[0]);
        assertEquals(1, groups[0].getStudents().size());
        assertEquals(student, groups[0].getStudents().get(0));
        assertNotEquals(1, groups[1].getStudents().size());

        em.getTransaction().begin();
        service.addToGroup(groups[1], student);
        em.getTransaction().commit();
        em.refresh(groups[0]);
        em.refresh(groups[1]);

        assertEquals(1, groups[1].getStudents().size());
        assertEquals(student, groups[1].getStudents().get(0));
        assertNotEquals(1, groups[0].getStudents().size());

    }

    @Test
    public void testMoveToGroup() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        em.getTransaction().begin();
        student.setDepartmentGroup(groups[0]);
        em.persist(student);
        em.getTransaction().commit();
        em.refresh(groups[0]);
        assertEquals(1, groups[0].getStudents().size());
        assertEquals(student, groups[0].getStudents().get(0));
        assertNotEquals(1, groups[1].getStudents().size());

        em.getTransaction().begin();
        service.moveToGroup(groups[1], student);
        em.getTransaction().commit();
        em.refresh(groups[0]);
        em.refresh(groups[1]);

        assertEquals(1, groups[1].getStudents().size());
        assertEquals(student, groups[1].getStudents().get(0));
        assertNotEquals(1, groups[0].getStudents().size());
    }

    @Test
    public void testGetAll() throws Exception {
        final int STUDENTS_CNT = 3;
        Student[] students = new Student[STUDENTS_CNT];
        em.getTransaction().begin();
        for (int i = 0; i < STUDENTS_CNT; i++) {
            students[i] = new Student("Student " + i, "", 18);
            students[i].setDepartmentGroup(groups[0]);
            em.persist(students[i]);
        }
        em.getTransaction().commit();
        em.refresh(groups[0]);
        assertEquals(STUDENTS_CNT, service.getAll().size());
        for (int i = 0; i < STUDENTS_CNT; i++) {
            assertTrue(service.getAll().contains(students[i]));
        }
    }

    @Test
    public void testUpdate() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        em.getTransaction().begin();
        student.setDepartmentGroup(groups[0]);
        em.persist(student);
        em.getTransaction().commit();

        student.setFirstName("Updated name");
        em.getTransaction().begin();
        Student studentFromDB = service.update(student);
        em.getTransaction().commit();

        assertEquals(student, studentFromDB);
        assertEquals(1, em.createQuery("SELECT s FROM Student s").getResultList().size());
        assertEquals(student, em.createQuery("SELECT s FROM Student s").getResultList().get(0));
    }

    @Test
    public void testDelete() throws Exception {
        Student student = new Student("Name", "Surname", 18);
        em.getTransaction().begin();
        student.setDepartmentGroup(groups[0]);
        em.persist(student);
        em.getTransaction().commit();
        assertEquals(1, em.createQuery("SELECT s FROM Student s").getResultList().size());

        em.getTransaction().begin();
        service.delete(student);
        em.getTransaction().commit();
        assertEquals(0, em.createQuery("SELECT s FROM Student s").getResultList().size());
    }


    private void clearTables() {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM student;").executeUpdate();
        em.createNativeQuery("DELETE FROM department_group;").executeUpdate();
        em.createNativeQuery("DELETE FROM department;").executeUpdate();
        em.getTransaction().commit();
    }
}