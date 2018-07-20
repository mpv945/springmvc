package org.haijun.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/json")
public class JsonController {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @RequestMapping("getI18nVal")
    @ResponseBody
    public String getI18n(Locale locale){
        return messageSource.getMessage("key",null,locale);// 后台获取资源的值
    }


    //JSON和对象互转核心时HttpMessageConverter<T>,负责将请求信息转成（HttpInputMessage），经过该类转成对应T对象，或者将对象转换成HttpOutputMessage。最后相应信息
    @PostMapping("getJson")
    @ResponseBody// 返回JSON字符串，需要依赖Jackson Databind，Jackson Core ，Jackson Annotations，加入该jar会请求RequestMappingHanderAdapter
    //中的MessageConverter（默认6个）增加一个MappingJackson2HttpMessageConverter的实现来处理Json和javaBean的处理实现
    public List<String> getJson(){
        List<String> all = new ArrayList<>();
        all.add("dfdsfsdf");
        all.add("sfswwtrtrere");
        return all;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public String uploadFile(@RequestBody String uploadFileStr){//直接着找MessageConverter（默认6个）的StringHttpMessageConverter类处理该消息
        return "上传的文件内容和请求参数内容=  "+uploadFileStr;
    }


    /**
     * 使用ResponseEntity 模拟下载功能
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/down")
    public ResponseEntity<byte[]> moNiXiaZai(HttpSession session) throws IOException {
        byte[] body = null;
        ServletContext servletContext = session.getServletContext();
        InputStream in = servletContext.getResourceAsStream("/files/test.txt");//webroot 目录下
        body = new byte[in.available()];
        in.read(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment;filename=" + URLEncoder.encode("abc.txt", "utf-8"));

        HttpStatus status = HttpStatus.OK;

        ResponseEntity<byte[]> response = new ResponseEntity<>(body,headers,status);

        return response;
    }

}
