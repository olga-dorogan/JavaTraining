package com.custom.model.dao.impl;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

/**
 * Created by olga on 05.04.15.
 */
@Ignore
@RunWith(Arquillian.class)
public class DepartmentGroupDAOImplTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Department.class, DepartmentGroup.class, Student.class)
                .addClasses(DepartmentDAO.class, DepartmentDAOImpl.class)
                .addClasses(DepartmentGroupDAO.class, DepartmentGroupDAOImpl.class)
                .addPackage(DAOBusinessException.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private DepartmentGroupDAO departmentGroupDAO;
    @EJB
    private DepartmentDAO departmentDAO;

    private Department testedDepartment;

    @Before
    public void setUp() throws Exception {
        testedDepartment = departmentDAO.add(new Department("tested department"));
        assertNotNull(testedDepartment);
    }


    @Test
    public void testAddToDepartment() throws Exception {
        DepartmentGroup group = new DepartmentGroup("tested group", 1);
        group = departmentGroupDAO.addToDepartment(testedDepartment, group);
        assertNotNull(group);
        assertEquals(testedDepartment, group.getDepartment());
        testedDepartment = departmentDAO.getById(testedDepartment.getId());
        assertEquals(1, testedDepartment.getGroups().size());
        assertTrue(testedDepartment.getGroups().contains(group));
    }
}