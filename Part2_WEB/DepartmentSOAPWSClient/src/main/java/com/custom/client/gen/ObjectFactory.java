
package com.custom.client.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.custom.client.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteStudent_QNAME = new QName("http://service.custom.com/", "deleteStudent");
    private final static QName _DAOBusinessException_QNAME = new QName("http://service.custom.com/", "DAOBusinessException");
    private final static QName _SaveStudentResponse_QNAME = new QName("http://service.custom.com/", "saveStudentResponse");
    private final static QName _GetAll_QNAME = new QName("http://service.custom.com/", "getAll");
    private final static QName _GetByOrderNumberResponse_QNAME = new QName("http://service.custom.com/", "getByOrderNumberResponse");
    private final static QName _DeleteStudentResponse_QNAME = new QName("http://service.custom.com/", "deleteStudentResponse");
    private final static QName _UpdateStudentResponse_QNAME = new QName("http://service.custom.com/", "updateStudentResponse");
    private final static QName _GetByOrderNumber_QNAME = new QName("http://service.custom.com/", "getByOrderNumber");
    private final static QName _SaveStudent_QNAME = new QName("http://service.custom.com/", "saveStudent");
    private final static QName _GetAllResponse_QNAME = new QName("http://service.custom.com/", "getAllResponse");
    private final static QName _UpdateStudent_QNAME = new QName("http://service.custom.com/", "updateStudent");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.custom.client.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetByOrderNumber }
     * 
     */
    public GetByOrderNumber createGetByOrderNumber() {
        return new GetByOrderNumber();
    }

    /**
     * Create an instance of {@link DeleteStudent }
     * 
     */
    public DeleteStudent createDeleteStudent() {
        return new DeleteStudent();
    }

    /**
     * Create an instance of {@link GetAllResponse }
     * 
     */
    public GetAllResponse createGetAllResponse() {
        return new GetAllResponse();
    }

    /**
     * Create an instance of {@link UpdateStudent }
     * 
     */
    public UpdateStudent createUpdateStudent() {
        return new UpdateStudent();
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link DAOBusinessException }
     * 
     */
    public DAOBusinessException createDAOBusinessException() {
        return new DAOBusinessException();
    }

    /**
     * Create an instance of {@link GetAll }
     * 
     */
    public GetAll createGetAll() {
        return new GetAll();
    }

    /**
     * Create an instance of {@link UpdateStudentResponse }
     * 
     */
    public UpdateStudentResponse createUpdateStudentResponse() {
        return new UpdateStudentResponse();
    }

    /**
     * Create an instance of {@link SaveStudentResponse }
     * 
     */
    public SaveStudentResponse createSaveStudentResponse() {
        return new SaveStudentResponse();
    }

    /**
     * Create an instance of {@link SaveStudent }
     * 
     */
    public SaveStudent createSaveStudent() {
        return new SaveStudent();
    }

    /**
     * Create an instance of {@link DeleteStudentResponse }
     * 
     */
    public DeleteStudentResponse createDeleteStudentResponse() {
        return new DeleteStudentResponse();
    }

    /**
     * Create an instance of {@link GetByOrderNumberResponse }
     * 
     */
    public GetByOrderNumberResponse createGetByOrderNumberResponse() {
        return new GetByOrderNumberResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteStudent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "deleteStudent")
    public JAXBElement<DeleteStudent> createDeleteStudent(DeleteStudent value) {
        return new JAXBElement<DeleteStudent>(_DeleteStudent_QNAME, DeleteStudent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DAOBusinessException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "DAOBusinessException")
    public JAXBElement<DAOBusinessException> createDAOBusinessException(DAOBusinessException value) {
        return new JAXBElement<DAOBusinessException>(_DAOBusinessException_QNAME, DAOBusinessException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveStudentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "saveStudentResponse")
    public JAXBElement<SaveStudentResponse> createSaveStudentResponse(SaveStudentResponse value) {
        return new JAXBElement<SaveStudentResponse>(_SaveStudentResponse_QNAME, SaveStudentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "getAll")
    public JAXBElement<GetAll> createGetAll(GetAll value) {
        return new JAXBElement<GetAll>(_GetAll_QNAME, GetAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByOrderNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "getByOrderNumberResponse")
    public JAXBElement<GetByOrderNumberResponse> createGetByOrderNumberResponse(GetByOrderNumberResponse value) {
        return new JAXBElement<GetByOrderNumberResponse>(_GetByOrderNumberResponse_QNAME, GetByOrderNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteStudentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "deleteStudentResponse")
    public JAXBElement<DeleteStudentResponse> createDeleteStudentResponse(DeleteStudentResponse value) {
        return new JAXBElement<DeleteStudentResponse>(_DeleteStudentResponse_QNAME, DeleteStudentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStudentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "updateStudentResponse")
    public JAXBElement<UpdateStudentResponse> createUpdateStudentResponse(UpdateStudentResponse value) {
        return new JAXBElement<UpdateStudentResponse>(_UpdateStudentResponse_QNAME, UpdateStudentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByOrderNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "getByOrderNumber")
    public JAXBElement<GetByOrderNumber> createGetByOrderNumber(GetByOrderNumber value) {
        return new JAXBElement<GetByOrderNumber>(_GetByOrderNumber_QNAME, GetByOrderNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveStudent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "saveStudent")
    public JAXBElement<SaveStudent> createSaveStudent(SaveStudent value) {
        return new JAXBElement<SaveStudent>(_SaveStudent_QNAME, SaveStudent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "getAllResponse")
    public JAXBElement<GetAllResponse> createGetAllResponse(GetAllResponse value) {
        return new JAXBElement<GetAllResponse>(_GetAllResponse_QNAME, GetAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStudent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.custom.com/", name = "updateStudent")
    public JAXBElement<UpdateStudent> createUpdateStudent(UpdateStudent value) {
        return new JAXBElement<UpdateStudent>(_UpdateStudent_QNAME, UpdateStudent.class, null, value);
    }

}
