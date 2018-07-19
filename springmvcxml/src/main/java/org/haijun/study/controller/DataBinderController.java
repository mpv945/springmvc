package org.haijun.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/study")
public class DataBinderController {

    /*数据绑定流程
    1 .spring mvc主框架将ServletRequest对象以目标方法入参实例传给WebDataBinderFactory实例，以创建DataBinder对象
    2.DataBinder调用mvc上下文的ConversionService组建进行数据类型转换和格式化，最后将数据填充到入参对象中
    3.调用Validator组件对入参对象数据进行校验，抽取校验错误的信息到BindingResult中，并增加到目标方法的请求参数中
    */
}
