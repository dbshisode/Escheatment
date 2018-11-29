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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="case_id" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="recipient_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recipient_address_line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="recipient_address_line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="collage_document_id" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="user_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="document_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="document_date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
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
    "caseId",
    "recipientName",
    "recipientAddressLine1",
    "recipientAddressLine2",
    "collageDocumentId",
    "userName",
    "documentName",
    "documentDate"
})
@XmlRootElement(name = "submitForBatchProcessing", namespace = "http://eservice.ccmstools")
public class SubmitForBatchProcessing {

    @XmlElement(name = "case_id", namespace = "http://eservice.ccmstools")
    protected Double caseId;
    @XmlElementRef(name = "recipient_name", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<String> recipientName;
    @XmlElementRef(name = "recipient_address_line1", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<String> recipientAddressLine1;
    @XmlElementRef(name = "recipient_address_line2", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<String> recipientAddressLine2;
    @XmlElement(name = "collage_document_id", namespace = "http://eservice.ccmstools")
    protected Double collageDocumentId;
    @XmlElementRef(name = "user_name", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<String> userName;
    @XmlElementRef(name = "document_name", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<String> documentName;
    @XmlElementRef(name = "document_date", namespace = "http://eservice.ccmstools", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> documentDate;

    /**
     * Gets the value of the caseId property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCaseId() {
        return caseId;
    }

    /**
     * Sets the value of the caseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCaseId(Double value) {
        this.caseId = value;
    }

    /**
     * Gets the value of the recipientName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecipientName() {
        return recipientName;
    }

    /**
     * Sets the value of the recipientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecipientName(JAXBElement<String> value) {
        this.recipientName = value;
    }

    /**
     * Gets the value of the recipientAddressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecipientAddressLine1() {
        return recipientAddressLine1;
    }

    /**
     * Sets the value of the recipientAddressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecipientAddressLine1(JAXBElement<String> value) {
        this.recipientAddressLine1 = value;
    }

    /**
     * Gets the value of the recipientAddressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecipientAddressLine2() {
        return recipientAddressLine2;
    }

    /**
     * Sets the value of the recipientAddressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecipientAddressLine2(JAXBElement<String> value) {
        this.recipientAddressLine2 = value;
    }

    /**
     * Gets the value of the collageDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCollageDocumentId() {
        return collageDocumentId;
    }

    /**
     * Sets the value of the collageDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCollageDocumentId(Double value) {
        this.collageDocumentId = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserName(JAXBElement<String> value) {
        this.userName = value;
    }

    /**
     * Gets the value of the documentName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDocumentName() {
        return documentName;
    }

    /**
     * Sets the value of the documentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDocumentName(JAXBElement<String> value) {
        this.documentName = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDocumentDate(JAXBElement<XMLGregorianCalendar> value) {
        this.documentDate = value;
    }

}