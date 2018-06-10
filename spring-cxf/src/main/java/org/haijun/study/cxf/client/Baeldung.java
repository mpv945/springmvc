package org.haijun.study.cxf.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * My portType description
 *
 * This class was generated by Apache CXF 3.2.4
 * 2018-06-09T23:17:02.199+08:00
 * Generated source version: 3.2.4
 *
 */
@WebService(targetNamespace = "http://cxf.study.haijun.org/", name = "Baeldung")
@XmlSeeAlso({ObjectFactory.class})
public interface Baeldung {

    /**
     * 我运行的helloward示例
     */
    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "helloResponse", targetNamespace = "http://cxf.study.haijun.org/", partName = "parameters")
    public java.lang.String hello(
        @WebParam(partName = "parameters", name = "hello", targetNamespace = "http://cxf.study.haijun.org/")
        java.lang.String parameters
    );

    @WebMethod
    @RequestWrapper(localName = "getusers", targetNamespace = "http://cxf.study.haijun.org/", className = "org.haijun.study.cxf.client.Getusers")
    @ResponseWrapper(localName = "getusersResponse", targetNamespace = "http://cxf.study.haijun.org/", className = "org.haijun.study.cxf.client.GetusersResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<org.haijun.study.cxf.client.User> getusers();

    @WebMethod
    @RequestWrapper(localName = "register", targetNamespace = "http://cxf.study.haijun.org/", className = "org.haijun.study.cxf.client.Register")
    @ResponseWrapper(localName = "registerResponse", targetNamespace = "http://cxf.study.haijun.org/", className = "org.haijun.study.cxf.client.RegisterResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String register(
        @WebParam(name = "student", targetNamespace = "")
        org.haijun.study.cxf.client.Student student
    );
}
