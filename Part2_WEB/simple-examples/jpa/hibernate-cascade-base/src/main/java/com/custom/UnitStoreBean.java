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
public class UnitStoreBean {
    @PersistenceContext(unitName = "dsEclipseLink")
    EntityManager em;

    public void addUnit(Unit unit) {
        em.persist(unit);
    }

    public List<Unit> getAllUnits() {
        return em.createQuery("SELECT unit From Unit unit", Unit.class).getResultList();
    }

    public void updateUnit(Unit unit) {
        Unit unitFromDB = em.find(Unit.class, unit.getId());
        if (unitFromDB == null) {
            throw new EntityNotFoundException();
        }
        em.merge(unit);
    }

    public void deleteUnit(Integer id) {
        Unit unitFromDB = em.find(Unit.class, id);
        if (unitFromDB == null) {
            throw new EntityNotFoundException();
        }
        em.remove(unitFromDB);
    }
}
