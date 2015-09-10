
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

    private final static QName _WelcomeMessageResponse_QNAME = new QName("http://custom.com/", "welcomeMessageResponse");
    private final static QName _FindSurnames_QNAME = new QName("http://custom.com/", "findSurnames");
    private final static QName _SaveStudentResponse_QNAME = new QName("http://custom.com/", "saveStudentResponse");
    private final static QName _FindSurnamesResponse_QNAME = new QName("http://custom.com/", "findSurnamesResponse");
    private final static QName _SaveStudent_QNAME = new QName("http://custom.com/", "saveStudent");
    private final static QName _WelcomeMessage_QNAME = new QName("http://custom.com/", "welcomeMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.custom.client.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WelcomeMessage }
     * 
     */
    public WelcomeMessage createWelcomeMessage() {
        return new WelcomeMessage();
    }

    /**
     * Create an instance of {@link FindSurnamesResponse }
     * 
     */
    public FindSurnamesResponse createFindSurnamesResponse() {
        return new FindSurnamesResponse();
    }

    /**
     * Create an instance of {@link WelcomeMessageResponse }
     * 
     */
    public WelcomeMessageResponse createWelcomeMessageResponse() {
        return new WelcomeMessageResponse();
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link FindSurnames }
     * 
     */
    public FindSurnames createFindSurnames() {
        return new FindSurnames();
    }

    /**
     * Create an instance of {@link SaveStudent }
     * 
     */
    public SaveStudent createSaveStudent() {
        return new SaveStudent();
    }

    /**
     * Create an instance of {@link SaveStudentResponse }
     * 
     */
    public SaveStudentResponse createSaveStudentResponse() {
        return new SaveStudentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WelcomeMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://custom.com/", name = "welcomeMessageResponse")
    public JAXBElement<WelcomeMessageResponse> createWelcomeMessageResponse(WelcomeMessageResponse value) {
        return new JAXBElement<WelcomeMessageResponse>(_WelcomeMessageResponse_QNAME, WelcomeMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSurnames }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://custom.com/", name = "findSurnames")
    public JAXBElement<FindSurnames> createFindSurnames(FindSurnames value) {
        return new JAXBElement<FindSurnames>(_FindSurnames_QNAME, FindSurnames.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveStudentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://custom.com/", name = "saveStudentResponse")
    public JAXBElement<SaveStudentResponse> createSaveStudentResponse(SaveStudentResponse value) {
        return new JAXBElement<SaveStudentResponse>(_SaveStudentResponse_QNAME, SaveStudentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSurnamesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://custom.com/", name = "findSurnamesResponse")
    public JAXBElement<FindSurnamesResponse> createFindSurnamesResponse(FindSurnamesResponse value) {
        return new JAXBElement<FindSurnamesResponse>(_FindSurnamesResponse_QNAME, FindSurnamesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveStudent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://custom.com/", name = "saveStudent")
    public JAXBElement<SaveStudent> createSaveStudent(SaveStudent value) {
        return new JAXBElement<SaveStudent>(_SaveStudent_QNAME, SaveStudent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WelcomeMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://custom.com/", name = "welcomeMessage")
    public JAXBElement<WelcomeMessage> createWelcomeMessage(WelcomeMessage value) {
        return new JAXBElement<WelcomeMessage>(_WelcomeMessage_QNAME, WelcomeMessage.class, null, value);
    }

}
