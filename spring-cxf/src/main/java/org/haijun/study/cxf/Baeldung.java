package org.haijun.study.cxf;

import org.apache.cxf.annotations.WSDLDocumentation;
import org.apache.cxf.annotations.WSDLDocumentationCollection;
import org.haijun.study.vo.Student;
import org.haijun.study.vo.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService
@WSDLDocumentationCollection(
        {
                @WSDLDocumentation("My portType description"),
                @WSDLDocumentation(value = "My top level description",
                        placement = WSDLDocumentation.Placement.TOP),
                @WSDLDocumentation(value = "My binding description",
                        placement = WSDLDocumentation.Placement.BINDING)
        }
)
public interface Baeldung {

    @WSDLDocumentation("我运行的helloward示例")
    @WebMethod//exclude 是否排除该方法
    @WebResult
    @RequestWrapper(className = "java.lang.String")//请求参数类型
    @ResponseWrapper(className = "java.lang.String")//响应类型
    String hello(@WebParam(name = "name") String name);

    @WebMethod// 可以通过参数控制不显示
    @WebResult
    String register(@WebParam(name="student") Student student);

    List<User> getusers();
}
