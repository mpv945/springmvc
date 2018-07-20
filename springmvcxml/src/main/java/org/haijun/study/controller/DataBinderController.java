package org.haijun.study.controller;

import org.haijun.study.vo.AccountVO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import javax.validation.Valid;

@Controller
@RequestMapping("/study")
public class DataBinderController {

    /*数据绑定流程
    1 .spring mvc主框架将ServletRequest对象以目标方法入参实例传给WebDataBinderFactory实例，以创建DataBinder对象
    2.DataBinder调用mvc上下文的ConversionService组建进行数据类型转换和格式化，最后将数据填充到入参对象中
    ConversionService 可以适配很多内置常用类型转换类，对象转字符串，数字转字符串，字符串转数字.......
    3.调用Validator组件对入参对象数据进行校验，抽取校验错误的信息到BindingResult中，并增加到目标方法的请求参数中
    */


    @PostMapping("convertTest")
    public void testTypeConverte(@RequestParam(value = "strObj" ) AccountVO vo, BindingResult result){// 转换和验证异常都会记录BindingResult
        // 测试对象转换（字符串转对象）
        if(result.getErrorCount()>0){
            for(FieldError obj : result.getFieldErrors()){
                System.out.println(obj.getField()+":"+obj.getDefaultMessage());
            }
        }

        System.out.println(vo);

    }

    /*验证测试*/
    @PostMapping("validtorTest")
    public void testValidator(@Valid AccountVO vo, BindingResult result){// 校验入参和错误消息要紧挨着
        if(result.getErrorCount()>0){
            for(FieldError obj : result.getFieldErrors()){
                System.out.println(obj.getField()+":"+obj.getDefaultMessage());
            }
        }
        System.out.println(vo);
    }
    /*1.只能标识方法，可以对WebDataBinder对象进行初始化，是dataBinder子类，用于表单到bena的绑定
      2.不能有返回值，参数通常是WebDataBinder，使用场景：用户角色可以多选，可以使用该注解把id映射成集合
      */
/*    @InitBinder
    public void aVoid(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("age");//标识哪些值不进行复制https://www.cnblogs.com/heyonggang/p/6186633.html
    }*/
}
