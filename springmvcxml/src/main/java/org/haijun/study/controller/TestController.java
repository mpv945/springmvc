package org.haijun.study.controller;

import org.haijun.study.vo.AccountVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;

@Controller
@RequestMapping("/test")//修饰类（表示路径）
//@SessionAttributes//该注解用来绑定HttpSession中的attribute对象的值，便于在方法中的参数里使用。该注解有value、types两个属性，可以通过名字和类型指定要使用的attribute 对象；
public class TestController {

   /* A、处理requet uri 部分（这里指uri template中variable，不含queryString部分）的注解：   @PathVariable;
    B、处理request header部分的注解：   @RequestHeader, @CookieValue;
    C、处理request body部分的注解：@RequestParam,  @RequestBody;
    D、处理attribute类型是注解： @SessionAttributes, @ModelAttribute;*/

    // 这种方式实际的效果就是在调用@RequestMapping的方法之前，为request对象的model里put（“account”， Account）；
    @ModelAttribute
    public AccountVO addAccount(@ModelAttribute AccountVO accountVO) {
        //return accountManager.findAccount(number);
        System.out.println("调用@RequestMapping的方法之前被调用了");
        if(accountVO != null){
            System.out.println("accountVO 在回话中不为空");
            return accountVO;
        }
        return new AccountVO();
    }

    /**
     * @ModelAttribute 注解在参数上，首先查询 @SessionAttributes有无绑定的AccountVO对象，若没有则查询@ModelAttribute方法层面上是否绑定了AccountVO对象，
     * 若没有则将URI template中的值按对应的名称绑定到Pet对象的各属性上。
     * @param accountVO
     * @return
     */
    @RequestMapping(value="/owners/{ownerId}/pets/{petId}/edit", method = RequestMethod.POST)
    public String add(@ModelAttribute AccountVO accountVO){
        return "success";
    }
    /**
     * params 可以包含参数集合，!参数表示不能包含该参数，否则必须包含。age!=10表示能包含10,headers功能类似
     * value 支持ant风格的路径，？匹配一个字符；*匹配一个路径的任意字符；** 匹配多层路径
     * @return
     */
    @RequestMapping(value = "/success",method = RequestMethod.GET, params = {"uname","age!=200"})//修饰控制器处理哪个路径url（多个url是与的关系）,method指定请求方式
    public String hello(String uname){
        System.out.println("hell word,用户姓名="+uname);
        return "success";// 返回结果=配置视图解析器配置的前缀+"success"+解析器配置的后缀，做转发操作
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public String updateTest(@PathVariable("id") Integer id){
        System.out.println("更新数据了="+id==null?"":id);
        return "success";
    }
    /**
     * 隐射url的占位符的值到参数中
     * @return
     */
    @RequestMapping("/path/{param}")
    public String path(@PathVariable("param") String val,@RequestHeader("Accept-Encoding") String encoding,
                       @CookieValue("JSESSIONID") String cookie,Model model){
        try {
            System.out.println("传递参数="+new String(val.getBytes("ISO-8859-1"), "UTF-8"));
            System.out.println("编码信息="+encoding);
            System.out.println("JSESSIONID="+cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }


    /*@RequestParam
    A） 常用来处理简单类型的绑定，通过Request.getParameter() 获取的String可直接转换为简单类型的情况
    （ String--> 简单类型的转换操作由ConversionService配置的转换器来完成）；
            因为使用request.getParameter()方式获取参数，所以可以处理get 方式中queryString的值，也可以处理post方式中 body data的值；
    B）用来处理Content-Type: 为 application/x-www-form-urlencoded编码的内容，提交方式GET、POST；
    C) 该注解有两个属性： value、required； value用来指定要传入值的id名称，required用来指示参数是否必须绑定；*/

    @RequestMapping(value = "/sendParam")
    public String sendParam(@RequestParam(value = "name" ,required = true, defaultValue = "") String name,
                            @RequestHeader("Content-Type") String contentType){//value为参数名
        System.out.println("请求类型="+contentType+"请求的姓名="+name);
        return "success";
    }

    /*@RequestBody
    该注解常用来处理Content-Type: 不是application/x-www-form-urlencoded编码的内容，例如application/json, application/xml等；
    它是通过使用HandlerAdapter 配置的HttpMessageConverters来解析post data body，然后绑定到相应的bean上的。
    因为配置有FormHttpMessageConverter，所以也可以用来处理 application/x-www-form-urlencoded的内容，处理完的结果放在一个MultiValueMap<String, String>里，
    这种情况在某些特殊需求下使用，详情查看FormHttpMessageConverter api;*/
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public String sendObject(AccountVO accountVO){ //支持级联属性（内置对象属性对象属性，小写开头）
        System.out.println(accountVO);
        return "success";
    }

    /**
     * 要使用Sevlet的一些API，只要直接将其作为参数传入即可。你需要request，就传入request， 需要session，就传入session。
     * 使用servlet原生对象参数:
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale
     * InputStream
     * OutputStream
     * Reader
     * Writer
     * @return
     */
    @RequestMapping(value = "/servletApiParam")//,produces = "text/html;charset=UTF-8"
    public void testServletAPI(HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session,
                                 Writer out) throws IOException{
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);
        response.setContentType("text/html;charset=utf-8");
        try {
            //out.write(new String("测试返回流".getBytes(Charset.forName("UTF-8"))));
            out.write("测试返回流");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
