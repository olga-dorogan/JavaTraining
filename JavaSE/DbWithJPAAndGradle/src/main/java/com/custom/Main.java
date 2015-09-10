package com.custom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by root on 15.02.15.
 */
public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = factory.createEntityManager();
        Query query = entityManager.createQuery("SELECT t FROM Todo t");
        @SuppressWarnings("unchecked")
        List<Todo> list = (List<Todo>)query.getResultList();
        for (Todo todo : list) {
            System.out.println("Todo :" + todo);
        }
        System.out.println("Table size: " + list.size());

        entityManager.getTransaction().begin();
        Todo todo = new Todo();
        todo.setDescription("Task " + System.nanoTime() / 1000);
        todo.setSummary("Test");
        entityManager.persist(todo);
        entityManager.getTransaction().commit();

        entityManager.close();
    }
}