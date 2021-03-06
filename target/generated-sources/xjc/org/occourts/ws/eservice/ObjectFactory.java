//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.07 at 08:25:33 AM PST 
//


package org.occourts.ws.eservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.occourts.ws.eservice package. 
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

    private final static QName _CcmstoolsEServiceEServiceCfcCFCInvocationExceptionCFCInvocationException_QNAME = new QName("http://eservice.ccmstools", "CFCInvocationException");
    private final static QName _SubmitForBatchProcessingRecipientName_QNAME = new QName("http://eservice.ccmstools", "recipient_name");
    private final static QName _SubmitForBatchProcessingRecipientAddressLine1_QNAME = new QName("http://eservice.ccmstools", "recipient_address_line1");
    private final static QName _SubmitForBatchProcessingRecipientAddressLine2_QNAME = new QName("http://eservice.ccmstools", "recipient_address_line2");
    private final static QName _SubmitForBatchProcessingUserName_QNAME = new QName("http://eservice.ccmstools", "user_name");
    private final static QName _SubmitForBatchProcessingDocumentName_QNAME = new QName("http://eservice.ccmstools", "document_name");
    private final static QName _SubmitForBatchProcessingDocumentDate_QNAME = new QName("http://eservice.ccmstools", "document_date");
    private final static QName _DocumentDelegateDocument_QNAME = new QName("http://rpc.xml.coldfusion/xsd", "document");
    private final static QName _EntryDelegateKey_QNAME = new QName("http://rpc.xml.coldfusion/xsd", "key");
    private final static QName _EntryDelegateValue_QNAME = new QName("http://rpc.xml.coldfusion/xsd", "value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.occourts.ws.eservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CFCInvocationException }
     * 
     */
    public CFCInvocationException createCFCInvocationException() {
        return new CFCInvocationException();
    }

    /**
     * Create an instance of {@link StructDelegate }
     * 
     */
    public StructDelegate createStructDelegate() {
        return new StructDelegate();
    }

    /**
     * Create an instance of {@link EntryDelegate }
     * 
     */
    public EntryDelegate createEntryDelegate() {
        return new EntryDelegate();
    }

    /**
     * Create an instance of {@link QueryBean }
     * 
     */
    public QueryBean createQueryBean() {
        return new QueryBean();
    }

    /**
     * Create an instance of {@link ArrayOfObject }
     * 
     */
    public ArrayOfObject createArrayOfObject() {
        return new ArrayOfObject();
    }

    /**
     * Create an instance of {@link DocumentDelegate }
     * 
     */
    public DocumentDelegate createDocumentDelegate() {
        return new DocumentDelegate();
    }

    /**
     * Create an instance of {@link ArrayDelegate }
     * 
     */
    public ArrayDelegate createArrayDelegate() {
        return new ArrayDelegate();
    }

    /**
     * Create an instance of {@link CcmstoolsEServiceEServiceCfcCFCInvocationException }
     * 
     */
    public CcmstoolsEServiceEServiceCfcCFCInvocationException createCcmstoolsEServiceEServiceCfcCFCInvocationException() {
        return new CcmstoolsEServiceEServiceCfcCFCInvocationException();
    }

    /**
     * Create an instance of {@link SubmitForBatchProcessing }
     * 
     */
    public SubmitForBatchProcessing createSubmitForBatchProcessing() {
        return new SubmitForBatchProcessing();
    }

    /**
     * Create an instance of {@link SubmitForBatchProcessingResponse }
     * 
     */
    public SubmitForBatchProcessingResponse createSubmitForBatchProcessingResponse() {
        return new SubmitForBatchProcessingResponse();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CFCInvocationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "CFCInvocationException", scope = CcmstoolsEServiceEServiceCfcCFCInvocationException.class)
    public JAXBElement<CFCInvocationException> createCcmstoolsEServiceEServiceCfcCFCInvocationExceptionCFCInvocationException(CFCInvocationException value) {
        return new JAXBElement<CFCInvocationException>(_CcmstoolsEServiceEServiceCfcCFCInvocationExceptionCFCInvocationException_QNAME, CFCInvocationException.class, CcmstoolsEServiceEServiceCfcCFCInvocationException.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "recipient_name", scope = SubmitForBatchProcessing.class)
    public JAXBElement<String> createSubmitForBatchProcessingRecipientName(String value) {
        return new JAXBElement<String>(_SubmitForBatchProcessingRecipientName_QNAME, String.class, SubmitForBatchProcessing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "recipient_address_line1", scope = SubmitForBatchProcessing.class)
    public JAXBElement<String> createSubmitForBatchProcessingRecipientAddressLine1(String value) {
        return new JAXBElement<String>(_SubmitForBatchProcessingRecipientAddressLine1_QNAME, String.class, SubmitForBatchProcessing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "recipient_address_line2", scope = SubmitForBatchProcessing.class)
    public JAXBElement<String> createSubmitForBatchProcessingRecipientAddressLine2(String value) {
        return new JAXBElement<String>(_SubmitForBatchProcessingRecipientAddressLine2_QNAME, String.class, SubmitForBatchProcessing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "user_name", scope = SubmitForBatchProcessing.class)
    public JAXBElement<String> createSubmitForBatchProcessingUserName(String value) {
        return new JAXBElement<String>(_SubmitForBatchProcessingUserName_QNAME, String.class, SubmitForBatchProcessing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "document_name", scope = SubmitForBatchProcessing.class)
    public JAXBElement<String> createSubmitForBatchProcessingDocumentName(String value) {
        return new JAXBElement<String>(_SubmitForBatchProcessingDocumentName_QNAME, String.class, SubmitForBatchProcessing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eservice.ccmstools", name = "document_date", scope = SubmitForBatchProcessing.class)
    public JAXBElement<XMLGregorianCalendar> createSubmitForBatchProcessingDocumentDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_SubmitForBatchProcessingDocumentDate_QNAME, XMLGregorianCalendar.class, SubmitForBatchProcessing.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Document }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rpc.xml.coldfusion/xsd", name = "document", scope = DocumentDelegate.class)
    public JAXBElement<Document> createDocumentDelegateDocument(Document value) {
        return new JAXBElement<Document>(_DocumentDelegateDocument_QNAME, Document.class, DocumentDelegate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rpc.xml.coldfusion/xsd", name = "key", scope = EntryDelegate.class)
    public JAXBElement<Object> createEntryDelegateKey(Object value) {
        return new JAXBElement<Object>(_EntryDelegateKey_QNAME, Object.class, EntryDelegate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rpc.xml.coldfusion/xsd", name = "value", scope = EntryDelegate.class)
    public JAXBElement<Object> createEntryDelegateValue(Object value) {
        return new JAXBElement<Object>(_EntryDelegateValue_QNAME, Object.class, EntryDelegate.class, value);
    }

}
