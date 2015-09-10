package com.custom.service;

import com.custom.PartOfUnit;
import com.custom.Unit;
import com.custom.UnitStoreBean;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 07.06.15.
 */
@Dependent
public class UnitService {
    @EJB
    UnitStoreBean unitStoreBean;

    public List<String> getUnitsNames() {
        List<Unit> units = unitStoreBean.getAllUnits();
        List<String> unitsNames = new ArrayList<>(units.size());
        for (Unit unit : units) {
            unitsNames.add(unit.getName());
        }
        return unitsNames;
    }

    @Transactional
    public List<String> getUnitsPartsNames() {
        List<Unit> units = unitStoreBean.getAllUnits();
        List<String> partsNames = new ArrayList<>();
        for (Unit unit : units) {
            List<PartOfUnit> partOfUnits = unit.getPartOfUnits();
            for(PartOfUnit part : partOfUnits) {
                partsNames.add(part.getName());
            }
        }
        return partsNames;
    }
}
