package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 30.04.15.
 */

@RunWith(Arquillian.class)
public class PartOfUnitStoreBeanTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(PartOfUnitStoreBean.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
    }

    private static Unit baseUnit = new Unit(100, "Base unit for parts");
    private static Unit unitForDelete = new Unit(101, "Unit for delete with parts");

    private static PartOfUnit firstPartBase = new PartOfUnit(1, "First part");
    private static PartOfUnit secondPartBase = new PartOfUnit(2, "Second part");
    private static PartOfUnit firstPartForDeletedUnit = new PartOfUnit(3, "First part");
    private static PartOfUnit secondPartForDeletedUnit = new PartOfUnit(4, "Second part");

    @EJB
    private UnitStoreBean unitStoreBean;
    @EJB
    private PartOfUnitStoreBean partOfUnitStoreBean;


    @Test
    @InSequence(1)
    public void testAddUnit() {
        System.out.println("============================");
        System.out.println("Add unit");
        System.out.println("============================");
        unitStoreBean.addUnit(baseUnit);
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(1, unitList.size());
    }

    @Test
    @InSequence(2)
    public void testAddParts() {
        System.out.println("============================");
        System.out.println("Add parts");
        System.out.println("============================");
        partOfUnitStoreBean.addPartToUnit(firstPartBase, baseUnit);
        partOfUnitStoreBean.addPartToUnit(secondPartBase, baseUnit);
        List<PartOfUnit> partsOfUnit = partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
        assertEquals(2, partsOfUnit.size());
    }

    @Test
    @InSequence(3)
    public void testUpdateParts() {
        System.out.println("============================");
        System.out.println("Update parts");
        System.out.println("============================");
        firstPartBase.setName("First part -- updated");
        secondPartBase.setName("Second part -- upated");
        partOfUnitStoreBean.updatePart(firstPartBase);
        partOfUnitStoreBean.updatePart(secondPartBase);
        List<PartOfUnit> partsOfUnit = partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
        assertEquals(2, partsOfUnit.size());
        assertThat(partsOfUnit, containsInAnyOrder(firstPartBase, secondPartBase));
    }

    @Test
    @InSequence(4)
    public void testDeletePart() {
        System.out.println("============================");
        System.out.println("Delete part");
        System.out.println("============================");
        partOfUnitStoreBean.deletePartOfUnit(firstPartBase);
        List<PartOfUnit> partsOfUnit = partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
        assertEquals(1, partsOfUnit.size());
        assertEquals(secondPartBase, partsOfUnit.get(0));
    }

    @Test
    @InSequence(5)
    public void testDeleteUnitWithParts() {
        System.out.println("============================");
        System.out.println("Delete unit with parts");
        System.out.println("============================");
        int initUnitsCnt = unitStoreBean.getAllUnits().size();
        int initPartsCnt = partOfUnitStoreBean.getAllParts().size();

        unitStoreBean.addUnit(unitForDelete);
        partOfUnitStoreBean.addPartToUnit(firstPartForDeletedUnit, unitForDelete);
        partOfUnitStoreBean.addPartToUnit(secondPartForDeletedUnit, unitForDelete);
        List<PartOfUnit> partOfUnits = partOfUnitStoreBean.getAllParts();
        List<Unit> unitList = unitStoreBean.getAllUnits();
        assertEquals(initPartsCnt + 2, partOfUnits.size());
        assertEquals(initUnitsCnt + 1, unitList.size());

        unitStoreBean.deleteUnit(unitForDelete.getId());
        partOfUnits = partOfUnitStoreBean.getAllParts();
        unitList = unitStoreBean.getAllUnits();
        assertEquals(initPartsCnt, partOfUnits.size());
        assertEquals(initUnitsCnt, unitList.size());
    }
}