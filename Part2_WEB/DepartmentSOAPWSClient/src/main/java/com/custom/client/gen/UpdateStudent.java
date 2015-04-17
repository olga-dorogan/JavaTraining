
package com.custom.client.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateStudent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateStudent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studentId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="updatedInfo" type="{http://service.custom.com/}student"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateStudent", propOrder = {
    "studentId",
    "updatedInfo"
})
public class UpdateStudent {

    protected int studentId;
    @XmlElement(required = true)
    protected Student updatedInfo;

    /**
     * Gets the value of the studentId property.
     * 
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the value of the studentId property.
     * 
     */
    public void setStudentId(int value) {
        this.studentId = value;
    }

    /**
     * Gets the value of the updatedInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Student }
     *     
     */
    public Student getUpdatedInfo() {
        return updatedInfo;
    }

    /**
     * Sets the value of the updatedInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Student }
     *     
     */
    public void setUpdatedInfo(Student value) {
        this.updatedInfo = value;
    }

}
