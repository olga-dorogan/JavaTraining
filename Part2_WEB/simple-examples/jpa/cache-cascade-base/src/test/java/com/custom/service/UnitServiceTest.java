package com.custom.service;

import com.custom.PartOfUnit;
import com.custom.PartOfUnitStoreBean;
import com.custom.Unit;
import com.custom.UnitStoreBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 07.06.15.
 */
@RunWith(Arquillian.class)
public class UnitServiceTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(PartOfUnitStoreBean.class.getPackage())
                .addClass(UnitService.class)
                .addAsResource("META-INF/persistence.xml");
    }
    private static Unit baseUnit = new Unit(100, "Base unit for parts");
    private static PartOfUnit firstPartBase = new PartOfUnit(1, "First part");
    private static PartOfUnit secondPartBase = new PartOfUnit(2, "Second part");


    @EJB
    private UnitStoreBean unitStoreBean;
    @EJB
    private PartOfUnitStoreBean partOfUnitStoreBean;
    @Inject
    private UnitService unitService;

    @Test
    @InSequence(1)
    public void testAddUnitWithParts() {
        System.out.println("============================");
        System.out.println("Add unit with parts");
        System.out.println("============================");
        unitStoreBean.addUnit(baseUnit);
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(1, unitList.size());
        partOfUnitStoreBean.addPartToUnit(firstPartBase, baseUnit);
        partOfUnitStoreBean.addPartToUnit(secondPartBase, baseUnit);
        List<PartOfUnit> partsOfUnit = partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
        assertEquals(2, partsOfUnit.size());
    }

    @Test
    @InSequence(2)
    public void testUnitService() {
        System.out.println("============================");
        System.out.println("Get unit and parts names");
        System.out.println("============================");
        List<String> unitsNames = unitService.getUnitsNames();
        assertEquals(1, unitsNames.size());
        List<String> unitsPartsNames = unitService.getUnitsPartsNames();
        assertEquals(2, unitsPartsNames.size());
    }

}