package com.custom;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by olga on 21.04.15.
 */
@Named
public class MyItemWriter extends AbstractItemWriter {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println("Write items: " + items);
        for (Object person : items) {
            em.persist(person);
        }
    }
}
