package com.custom.servlet;

import com.custom.PartOfUnit;
import com.custom.PartOfUnitStoreBean;
import com.custom.Unit;
import com.custom.UnitStoreBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by olga on 06.05.15.
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private static final String PARAM_ACTION = "action";

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(PARAM_ACTION);

        PrintWriter writer = resp.getWriter();
        if (action == null) {
            writer.print("Any parameter");
            return;
        }

        if (action.equalsIgnoreCase("addBaseUnit")) {
            writer.print(addBaseUnit());
        } else if (action.equalsIgnoreCase("addParts")) {
            writer.print(addParts());
        } else if (action.equalsIgnoreCase("updateParts")) {
            writer.print(updateParts());
        } else if (action.equalsIgnoreCase("deletePart")) {
            writer.print(deletePart());
        } else if (action.equalsIgnoreCase("deleteUnit")) {
            deleteUnit(writer);
        } else {
            writer.print("Any action");
        }
    }

    private void deleteUnit(PrintWriter writer) {
        System.out.println("============================");
        System.out.println("Delete unit with parts");
        System.out.println("============================");

        unitStoreBean.addUnit(unitForDelete);
        partOfUnitStoreBean.addPartToUnit(firstPartForDeletedUnit, unitForDelete);
        partOfUnitStoreBean.addPartToUnit(secondPartForDeletedUnit, unitForDelete);

        unitStoreBean.deleteUnit(unitForDelete.getId());
        writer.print(partOfUnitStoreBean.getAllParts());
        writer.print("\n");
        writer.print(unitStoreBean.getAllUnits());

    }

    private List<PartOfUnit> deletePart() {
        System.out.println("============================");
        System.out.println("Delete part");
        System.out.println("============================");
        partOfUnitStoreBean.deletePartOfUnit(firstPartBase);
        return partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
    }

    private List<PartOfUnit> updateParts() {
        System.out.println("============================");
        System.out.println("Update parts");
        System.out.println("============================");
        firstPartBase.setName("First part -- updated");
        secondPartBase.setName("Second part -- upated");
        partOfUnitStoreBean.updatePart(firstPartBase);
        partOfUnitStoreBean.updatePart(secondPartBase);
        return partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
    }

    private List<PartOfUnit> addParts() {
        System.out.println("============================");
        System.out.println("Add parts");
        System.out.println("============================");
        partOfUnitStoreBean.addPartToUnit(firstPartBase, baseUnit);
        partOfUnitStoreBean.addPartToUnit(secondPartBase, baseUnit);
        return partOfUnitStoreBean.getAllPartsOfUnit(baseUnit);
    }

    private List<Unit> addBaseUnit() {
        System.out.println("============================");
        System.out.println("Add unit");
        System.out.println("============================");
        unitStoreBean.addUnit(baseUnit);
        return unitStoreBean.getAllUnits();
    }
}
