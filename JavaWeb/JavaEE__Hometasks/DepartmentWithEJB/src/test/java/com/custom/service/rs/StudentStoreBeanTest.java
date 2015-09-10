package com.custom.service.rs;

import com.custom.model.dao.StudentDAO;
import com.custom.model.dao.impl.StudentDAOImpl;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;
import com.custom.service.util.StudentService;
import com.custom.service.util.StudentVO;
import com.custom.util.Loggable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by olga on 29.04.15.
 */
@RunWith(Arquillian.class)
public class StudentStoreBeanTest {

    private WebTarget target;

    @ArquillianResource
    private URL base;

    private static final String GROUP_NAME = "Group 1 (dep_1)";

    @Deployment(testable = false)
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(StudentDAO.class.getPackage())
                .addPackage(StudentDAOImpl.class.getPackage())
                .addPackage(Student.class.getPackage())
                .addPackage(DAOBusinessException.class.getPackage())
                .addPackage(StudentService.class.getPackage())
                .addPackage(StudentStoreBean.class.getPackage())
                .addPackage(Loggable.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/data-develop.sql")
                .addAsResource("log4j2.xml");
    }

    @Before
    public void setUp() throws Exception {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "webresources/student").toExternalForm()));
        target.register(StudentVOWithLinks.class);
    }

    @Test
    @InSequence(1)
    public void testGetAll() {
        Response response = target
                .queryParam("group", GROUP_NAME)
                .queryParam("course", "1")
                .request(MediaType.APPLICATION_JSON).get();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());
        StudentVOWithLinks[] studentVOWithLinkses = response.readEntity(StudentVOWithLinks[].class);
        assertNotNull(studentVOWithLinkses);
        assertEquals(2, studentVOWithLinkses.length);
    }

    @Test
    @InSequence(2)
    public void testGetById() {
        Response response = target
                .path("{id}").resolveTemplate("id", "-1")
                .request(MediaType.APPLICATION_JSON).get();
        assertNotNull(response);
        assertTrue(response.hasEntity());
        StudentVOWithLinks studentVOWithLinks = response.readEntity(StudentVOWithLinks.class);
        assertNotNull(studentVOWithLinks);
    }

    @Test
    @InSequence(3)
    public void testAddStudent() {
        StudentVO savedStudent = new StudentVO();
        savedStudent.setAge(18);
        savedStudent.setFirstName("New name");
        savedStudent.setLastName("New surname");
        savedStudent.setGroup(GROUP_NAME);
        savedStudent.setCourse(1);

        Response response = target.request().post(Entity.json(savedStudent));
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());

        StudentVOWithLinks studentVOWithLinks = response.readEntity(StudentVOWithLinks.class);
        assertNotNull(studentVOWithLinks);
        assertEquals(savedStudent, studentVOWithLinks.getStudentVO());

        response = target
                .queryParam("group", GROUP_NAME).queryParam("course", "1")
                .request(MediaType.APPLICATION_JSON).get();
        assertNotNull(response);
        StudentVOWithLinks[] studentVOWithLinkses = response.readEntity(StudentVOWithLinks[].class);
        assertNotNull(studentVOWithLinkses);
        assertEquals(3, studentVOWithLinkses.length);
    }

    @Test
    @InSequence(4)
    public void testUpdateStudent() {
        StudentVO updatedStudent = new StudentVO();
        updatedStudent.setAge(18);
        updatedStudent.setFirstName("Updated name");
        updatedStudent.setLastName("Updated surname");
        updatedStudent.setGroup(GROUP_NAME);
        updatedStudent.setCourse(1);

        Response response = target.path("{id}").resolveTemplate("id", "-1")
                .request().put(Entity.json(updatedStudent));
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());

        StudentVOWithLinks studentVOWithLinks = response.readEntity(StudentVOWithLinks.class);
        assertNotNull(studentVOWithLinks);
        assertEquals(updatedStudent, studentVOWithLinks.getStudentVO());
    }

    @Test
    @InSequence(5)
    public void testDeleteStudent() {
        Response response = target.path("{id}").resolveTemplate("id", "-1").request().delete();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(response.hasEntity());
        StudentVOWithLinks.Link link = response.readEntity(StudentVOWithLinks.Link.class);
        assertNotNull(link);

        response = target
                .queryParam("group", GROUP_NAME).queryParam("course", "1")
                .request(MediaType.APPLICATION_JSON).get();
        assertNotNull(response);
        StudentVOWithLinks[] studentVOWithLinkses = response.readEntity(StudentVOWithLinks[].class);
        assertNotNull(studentVOWithLinkses);
        assertEquals(2, studentVOWithLinkses.length);
    }

}

