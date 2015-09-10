package com.custom.people;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import static org.junit.Assert.*;

public class FamilyTest {

    private static final String PERSISTENCE_UNIT_NAME = "people";
    private EntityManagerFactory factory;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT m from Person m");
        boolean createNewEntries = (query.getResultList().size() == 0);

        if (createNewEntries) {
            assertEquals(0, query.getResultList().size());
            Family family = new Family();
            family.setDescription("Tested family");
            entityManager.persist(family);
            for (int i = 0; i < 40; i++) {
                Person person = new Person();
                person.setFirstName("First name " + i);
                person.setLastName("Last name " + i);
                entityManager.persist(person);

                family.getMembers().add(person);
                entityManager.persist(person);
                entityManager.persist(family);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void testAvailablePeople() {
        EntityManager entityManager = factory.createEntityManager();
        Query query = entityManager.createQuery("SELECT m FROM Family m");
        assertEquals(1, query.getResultList().size());
        assertEquals(40, ((Family) query.getSingleResult()).getMembers().size());
        entityManager.close();
    }

}