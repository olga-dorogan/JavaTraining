
package com.custom.client.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.5-b03-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "DAOBusinessException", targetNamespace = "http://service.custom.com/")
public class DAOBusinessException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private DAOBusinessException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public DAOBusinessException_Exception(String message, DAOBusinessException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public DAOBusinessException_Exception(String message, DAOBusinessException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.custom.client.gen.DAOBusinessException
     */
    public DAOBusinessException getFaultInfo() {
        return faultInfo;
    }

}
