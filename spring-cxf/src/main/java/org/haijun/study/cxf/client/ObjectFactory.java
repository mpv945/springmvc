
package org.haijun.study.cxf.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.haijun.study.cxf.client package. 
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

    private final static QName _Getusers_QNAME = new QName("http://cxf.study.haijun.org/", "getusers");
    private final static QName _GetusersResponse_QNAME = new QName("http://cxf.study.haijun.org/", "getusersResponse");
    private final static QName _Register_QNAME = new QName("http://cxf.study.haijun.org/", "register");
    private final static QName _RegisterResponse_QNAME = new QName("http://cxf.study.haijun.org/", "registerResponse");
    private final static QName _Hello_QNAME = new QName("http://cxf.study.haijun.org/", "hello");
    private final static QName _HelloResponse_QNAME = new QName("http://cxf.study.haijun.org/", "helloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.haijun.study.cxf.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Getusers }
     * 
     */
    public Getusers createGetusers() {
        return new Getusers();
    }

    /**
     * Create an instance of {@link GetusersResponse }
     * 
     */
    public GetusersResponse createGetusersResponse() {
        return new GetusersResponse();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Student }
     * 
     */
    public Student createStudent() {
        return new Student();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Getusers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.study.haijun.org/", name = "getusers")
    public JAXBElement<Getusers> createGetusers(Getusers value) {
        return new JAXBElement<Getusers>(_Getusers_QNAME, Getusers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetusersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.study.haijun.org/", name = "getusersResponse")
    public JAXBElement<GetusersResponse> createGetusersResponse(GetusersResponse value) {
        return new JAXBElement<GetusersResponse>(_GetusersResponse_QNAME, GetusersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.study.haijun.org/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.study.haijun.org/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.study.haijun.org/", name = "hello")
    public JAXBElement<String> createHello(String value) {
        return new JAXBElement<String>(_Hello_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.study.haijun.org/", name = "helloResponse")
    public JAXBElement<String> createHelloResponse(String value) {
        return new JAXBElement<String>(_HelloResponse_QNAME, String.class, null, value);
    }

}
