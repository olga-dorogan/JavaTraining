package com.custom.service.rs;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.dao.StudentDAO;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;
import com.custom.service.StudentVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 26.04.15.
 */
@Path("/student")
@Stateless
public class RSStudentStoreBean {
    @EJB
    private StudentDAO studentDAO;
    @EJB
    private DepartmentGroupDAO departmentGroupDAO;

    private static final String URL_LOCAL_SUFFIX = "/webresources/student";
    private static final String URL_LOCAL_PREFIX = "http://localhost:8080";

    /**
     * Get all students from the specified group
     *
     * @param groupDescr The name of the group
     * @param course     The number of the course student studied
     * @return The collection with searched students
     */
    @GET
    @Produces({"application/json", "application/xml"})
    public StudentVO[] getAll(@QueryParam("group") String groupDescr, @QueryParam("course") int course) {
        List<Student> studentEntities = studentDAO.getAllByLastNameOrder(departmentGroupDAO.getByDescrAndCourse(groupDescr, course));
        List<StudentVO> studentVOs = new ArrayList<>(studentEntities.size());
        StudentVO studentVO;
        int i = 0;
        for (Student student : studentEntities) {
            studentVO = new StudentVO();
            studentVO.setGroup(groupDescr);
            studentVO.setCourse(course);
            studentVO.setIdByOrder(++i);
            studentVO.setId((int) student.getId());
            studentVO.setFirstName(student.getFirstName());
            studentVO.setLastName(student.getLastName());
            studentVO.setAge(student.getAge());
            studentVOs.add(studentVO);
        }
        return studentVOs.toArray(new StudentVO[studentVOs.size()]);
    }

    /**
     * Get the student with specified id
     *
     * @param id id of the student to search
     * @return description of the student
     */
    @GET
    @Path("{id}")
    @Produces({"application/json", "application/xml"})
    public Response getById(@PathParam("id") int id, @Context HttpServletRequest req) throws UnsupportedEncodingException {
        System.out.println(req.getContextPath());
        return Response
                .ok(new RSStudentVOWithLinks(
                        new StudentVO(studentDAO.getById((long) id)),
                        URL_LOCAL_PREFIX + req.getContextPath() + URL_LOCAL_SUFFIX))
                .build();
    }

    /**
     * Add a student to the group
     *
     * @param studentVO The student description; 'systemId' and 'idByOrder' fields are not required
     */
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response postStudent(StudentVO studentVO, @Context HttpServletRequest req) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLastName(), studentVO.getAge());
        DepartmentGroup group = departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse());
        Student createdStudentEntity = studentDAO.addToGroup(group, studentEntity);
        return Response
                .ok(new RSStudentVOWithLinks(
                        new StudentVO(createdStudentEntity),
                        URL_LOCAL_PREFIX + req.getContextPath() + URL_LOCAL_SUFFIX))
                .build();

    }


    /**
     * Update student's description
     *
     * @param id        The systemId of the student to update
     * @param studentVO The student description; 'systemId' and 'idByOrder' fields are not required
     * @throws DAOBusinessException
     */
    @PUT
    @Path("{id}")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response putStudent(@PathParam("id") int id, StudentVO studentVO, @Context HttpServletRequest req) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLastName(), studentVO.getAge());
        studentEntity.setDepartmentGroup(departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse()));
        studentEntity.setId(id);
        Student updatedStudentEntity = studentDAO.update(studentEntity);
        return Response
                .ok(new RSStudentVOWithLinks(
                        new StudentVO(updatedStudentEntity),
                        URL_LOCAL_PREFIX + req.getContextPath() + URL_LOCAL_SUFFIX))
                .build();
    }

    /**
     * Delete the student
     *
     * @param id The systemId of the student to delete
     */
    @DELETE
    @Path("{id}")
    @Produces({"application/json", "application/xml"})
    public RSStudentVOWithLinks.Link delete(@PathParam("id") int id, @Context HttpServletRequest req) {
        studentDAO.delete(studentDAO.getById(id));
        return new RSStudentVOWithLinks.Link("list", URL_LOCAL_PREFIX + req.getContextPath() + URL_LOCAL_SUFFIX, "GET");
    }
}
