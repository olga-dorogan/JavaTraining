package com.custom;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;

/**
 * Created by olga on 13.04.15.
 */
@Path("employee")
@Stateless
public class EmployeeResource {
    @PersistenceContext
    EntityManager em;

    @GET
    @Produces("application/xml")
    public Employee[] get() {
        return em.createQuery("SELECT empl FROM Employee empl", Employee.class).getResultList().toArray(new Employee[0]);
    }

    @POST
    public void post(@FormParam("name") String name) {
        em.persist(new Employee(name));
    }
}
