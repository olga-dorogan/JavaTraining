package com.custom.service.rs;

import com.custom.service.util.StudentService;
import com.custom.service.util.StudentVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 26.04.15.
 */
@Path("/student")
@Stateless(name = "restStudentStoreBean")
public class StudentStoreBean {
    private static final String URL_LOCAL_SUFFIX = "/webresources/student";
    @EJB
    private StudentService studentService;

    /**
     * Get all students from the specified group
     *
     * @param groupDescr The name of the group
     * @param course     The number of the course student studied
     * @return The collection with searched students
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public StudentVOWithLinks[] getAll(@QueryParam("group") String groupDescr, @QueryParam("course") int course,
                                         @Context HttpServletRequest req) {
        List<StudentVO> studentVOs = studentService.getAllStudentsFromGroup(groupDescr, course);
        List<StudentVOWithLinks> studentVOWithLinkses = new ArrayList<>(studentVOs.size());
        String urlPrefix = getUrlPrefixFromRequest(req);
        for (StudentVO studentVO : studentVOs) {
            studentVOWithLinkses.add(new StudentVOWithLinks(studentVO, urlPrefix));
        }
        return studentVOWithLinkses.toArray(new StudentVOWithLinks[studentVOWithLinkses.size()]);
    }

    /**
     * Get the student with specified id
     *
     * @param id id of the student to search
     * @return description of the student
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getById(@PathParam("id") int id, @Context HttpServletRequest req) {
        StudentVO studentVO = studentService.getStudentById(id);
        return Response.ok(new StudentVOWithLinks(studentVO, getUrlPrefixFromRequest(req))).build();
    }

    /**
     * Add a student to the group
     *
     * @param studentVO The student description; 'systemId' and 'idByOrder' fields are not required
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postStudent(StudentVO studentVO, @Context HttpServletRequest req) {
        StudentVO studentVOSaved = studentService.saveStudent(studentVO);
        return Response.ok(new StudentVOWithLinks(studentVOSaved, getUrlPrefixFromRequest(req))).build();
    }


    /**
     * Update student's description
     *
     * @param id        The systemId of the student to update
     * @param studentVO The student description; 'systemId' and 'idByOrder' fields are not required
     */
    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putStudent(@PathParam("id") int id, StudentVO studentVO, @Context HttpServletRequest req) {
        StudentVO updatedStudentVO = studentService.updateStudent(id, studentVO);
        return Response.ok(new StudentVOWithLinks(updatedStudentVO, getUrlPrefixFromRequest(req))).build();
    }

    /**
     * Delete the student
     *
     * @param id The systemId of the student to delete
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public StudentVOWithLinks.Link delete(@PathParam("id") int id, @Context HttpServletRequest req) {
        studentService.deleteStudent(id);
        return new StudentVOWithLinks.Link("list", getUrlPrefixFromRequest(req), "GET");
    }

    private String getUrlPrefixFromRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://")
                .append(request.getServerName()).append(":").append(request.getServerPort())
                .append(request.getContextPath())
                .append(URL_LOCAL_SUFFIX);
        return sb.toString();
    }
}
