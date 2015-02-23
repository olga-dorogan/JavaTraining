package com.custom.service;

import com.custom.entity.Department;
import config.DBUnitConfig;
import org.dbunit.Assertion;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ExcludeTableFilter;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.List;

/**
 * Created by olga on 20.02.15.
 */
@Ignore
public class DepartmentServiceWithDBUnitTest extends DBUnitConfig {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("department");
    private static EntityManager em = factory.createEntityManager();

    private DepartmentService service = new DepartmentService(em);

    private static final ITableFilter tableFilter = new ExcludeTableFilter(new String[]{"sequence"});
    private static final String PATH_PREFIX = "src/test/resources/com/custom/entity/department/";

    public DepartmentServiceWithDBUnitTest(String name) {
        super(name);
    }

    @AfterClass
    public static void closeEntityManager() {
        em.close();
        factory.close();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                new File(PATH_PREFIX + "department-data.xml"));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    @After
    public void clearWorkspace() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        clearTables();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Department> departments = service.getAll();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                new File(PATH_PREFIX + "department-data.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();

        Assertion.assertEquals(expectedData, new FilteredDataSet(tableFilter, actualData));
        assertEquals(expectedData.getTable("department").getRowCount(), departments.size());
    }

    @Test
    public void testAdd() throws Exception {
        Department department = new Department("department 3");
        department.setId(3l);
        em.getTransaction().begin();
        service.add(department);
        em.getTransaction().commit();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                new File(PATH_PREFIX + "department-data-add.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEquals(expectedData, new FilteredDataSet(tableFilter, actualData));
    }

    @Test
    public void testUpdate() throws Exception {
        Department department = em.find(Department.class, 2l);
        if (department == null) {
            fail("Department wasn't found.");
        }
        department.setDescription("department 2 updated");
        em.getTransaction().begin();
        service.update(department);
        em.getTransaction().commit();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                new File(PATH_PREFIX + "department-data-update.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEquals(expectedData, new FilteredDataSet(tableFilter, actualData));
    }

    @Test
    public void testDelete() throws Exception {
        Department department = em.find(Department.class, 2l);
        if (department == null) {
            fail("Department wasn't found.");
        }
        department.setDescription("department 2 updated");
        em.getTransaction().begin();
        service.delete(department);
        em.getTransaction().commit();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                new File(PATH_PREFIX + "department-data-delete.xml"));
        IDataSet actualData = tester.getConnection().createDataSet();
        Assertion.assertEquals(expectedData, new FilteredDataSet(tableFilter, actualData));
    }

    private void clearTables() {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM student;").executeUpdate();
        em.createNativeQuery("DELETE FROM department_group;").executeUpdate();
        em.createNativeQuery("DELETE FROM department;").executeUpdate();
        em.getTransaction().commit();
    }

}
