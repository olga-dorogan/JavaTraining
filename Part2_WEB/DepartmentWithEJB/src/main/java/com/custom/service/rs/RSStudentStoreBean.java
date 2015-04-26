package com.custom.service.rs;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.dao.StudentDAO;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;
import com.custom.model.exception.DAOBusinessException;
import com.custom.service.StudentVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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

    /**
     * Get all students from the specified group
     * @param groupDescr The name of the group
     * @param course The number of the course student studied
     * @return The collection with searched students
     */
    @GET
    @Produces({"application/json","application/xml"})
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
     * Add a student to the group
     * @param studentVO The student description; 'systemId' and 'idByOrder' fields are not required
     * @throws DAOBusinessException
     */
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response postStudent(StudentVO studentVO) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLastName(), studentVO.getAge());
        DepartmentGroup group = departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse());
        Student createdStudentEntity = studentDAO.addToGroup(group, studentEntity);
        return Response
                .ok(new StudentVO(createdStudentEntity))
                .build();
    }

    /**
     * Updated the student description
     * @param id The systemId of the student to update
     * @param studentVO The student description; 'systemId' and 'idByOrder' fields are not required
     * @throws DAOBusinessException
     */
    @PUT
    @Consumes("application/xml")
    @Path("{id}")
    public void putStudent(@PathParam("id")int id, StudentVO studentVO) throws DAOBusinessException {
        Student studentEntity = new Student(studentVO.getFirstName(), studentVO.getLastName(), studentVO.getAge());
        studentEntity.setDepartmentGroup(departmentGroupDAO.getByDescrAndCourse(studentVO.getGroup(), studentVO.getCourse()));
        studentEntity.setId(id);
        studentDAO.update(studentEntity);
    }

    /**
     * Delete the student
     * @param id The systemId of the student to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        studentDAO.delete(studentDAO.getById(id));
    }
}
