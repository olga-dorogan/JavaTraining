package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 30.04.15.
 */
@Ignore
@RunWith(Arquillian.class)
public class UnitStoreBeanTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(UnitStoreBean.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    UnitStoreBean unitStoreBean;

    private static Unit firstUnit = new Unit(1, "First unit");
    private static Unit secondUnit = new Unit(2, "Second unit");

    @Test
    @InSequence(1)
    public void testAddUnit() {
        unitStoreBean.addUnit(firstUnit);
    }

    @Test
    @InSequence(2)
    public void testGetAllUnits() {
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(1, unitList.size());
        assertEquals(firstUnit, unitList.get(0));
    }

    @Test
    @InSequence(3)
    public void testUpdateUnit() {
        firstUnit.setName("First unit - updated");
        unitStoreBean.updateUnit(firstUnit);
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(1, unitList.size());
        assertEquals(firstUnit, unitList.get(0));
    }

    @Test
    @InSequence(4)
    public void testAddUnitToNotEmptyDB() {
        unitStoreBean.addUnit(secondUnit);
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(2, unitList.size());
    }

    @Test
    @InSequence(5)
    public void testDeleteUnit() {
        unitStoreBean.deleteUnit(1);
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(1, unitList.size());
    }


}