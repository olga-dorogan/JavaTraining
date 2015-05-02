package com.custom;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by olga on 30.04.15.
 */
@Stateless
public class PartOfUnitStoreBean {
    @PersistenceContext(unitName = "dsEclipseLink")
    EntityManager em;

    public void addPartToUnit(PartOfUnit partOfUnit, Unit unit) {
        partOfUnit.setUnit(unit);
        em.persist(partOfUnit);
    }

    public void updatePart(PartOfUnit partOfUnit) {
        PartOfUnit partFromDB = em.find(PartOfUnit.class, partOfUnit.getId());
        if (partFromDB == null) {
            throw new EntityNotFoundException();
        }
        em.merge(partOfUnit);
    }

    public List<PartOfUnit> getAllPartsOfUnit(Unit unit) {
        return em.createQuery("SELECT parts FROM PartOfUnit parts WHERE parts.unit.id = " + unit.getId(), PartOfUnit.class)
                .getResultList();
    }

    public List<PartOfUnit> getAllParts() {
        return em.createQuery("SELECT parts FROM PartOfUnit parts", PartOfUnit.class).getResultList();
    }

    public void deletePartOfUnit(PartOfUnit partOfUnit) {
        PartOfUnit partFromDB = em.find(PartOfUnit.class, partOfUnit.getId());
        if (partFromDB == null) {
            throw new EntityNotFoundException();
        }
        em.remove(partFromDB);
    }
}
