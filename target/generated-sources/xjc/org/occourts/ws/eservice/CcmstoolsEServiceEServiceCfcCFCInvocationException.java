//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.07 at 08:25:33 AM PST 
//


package org.occourts.ws.eservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CFCInvocationException" type="{http://rpc.xml.coldfusion/xsd}CFCInvocationException" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cfcInvocationException"
})
@XmlRootElement(name = "ccmstools.eService.eService.cfcCFCInvocationException", namespace = "http://eservice.ccmstools")
public class CcmstoolsEServiceEServiceCfcCFCInvocationException {

    @XmlElementRef(name = "CFCInvocationException", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<CFCInvocationException> cfcInvocationException;

    /**
     * Gets the value of the cfcInvocationException property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CFCInvocationException }{@code >}
     *     
     */
    public JAXBElement<CFCInvocationException> getCFCInvocationException() {
        return cfcInvocationException;
    }

    /**
     * Sets the value of the cfcInvocationException property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CFCInvocationException }{@code >}
     *     
     */
    public void setCFCInvocationException(JAXBElement<CFCInvocationException> value) {
        this.cfcInvocationException = value;
    }

}