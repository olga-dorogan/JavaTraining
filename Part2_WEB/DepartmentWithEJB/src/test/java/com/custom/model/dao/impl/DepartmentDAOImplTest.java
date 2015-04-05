package com.custom.model.dao.impl;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;
import org.apache.commons.lang.Validate;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityExistsException;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 05.04.15.
 */
//@Ignore
@RunWith(Arquillian.class)
public class DepartmentDAOImplTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Department.class, DepartmentGroup.class, Student.class)
                .addClasses(DepartmentDAO.class, DepartmentDAOImpl.class)
                .addPackage(DAOBusinessException.class.getPackage())
                .addPackage(Validate.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private DepartmentDAO departmentDAO;

    private Department testedDepartment = new Department("tested department");


    @Test
    @InSequence(1)
    public void testGetAll() throws Exception {
        assertEquals(0, departmentDAO.getAll().size());
    }

    @Test
    @InSequence(2)
    public void testAdd() throws Exception {
        departmentDAO.add(testedDepartment);
        assertEquals(1, departmentDAO.getAll().size());
    }

    @Test(expected = DAOBusinessException.class)
    @InSequence(3)
    public void testAddDuplicateDepartment() throws Exception {
        try {
            departmentDAO.add(testedDepartment);
        } catch (DAOBusinessException e) {
            assertEquals(EntityExistsException.class, e.getCause().getClass());
            assertEquals("Department already exists", e.getMessage());
            assertEquals(1, departmentDAO.getAll().size());
            throw e;
        }
    }
    @Test(expected = DAOBusinessException.class)
    @InSequence(3)
    public void testAddWithDepartmentDuplicateDescriptions() throws Exception {
        try {
            Department equalDescrDepartment = new Department(testedDepartment.getDescription());
            departmentDAO.add(equalDescrDepartment);
        } catch (DAOBusinessException e) {
            assertEquals(EntityExistsException.class, e.getCause().getClass());
            assertEquals("Department already exists", e.getMessage());
            assertEquals(1, departmentDAO.getAll().size());
            throw e;
        }
    }
}