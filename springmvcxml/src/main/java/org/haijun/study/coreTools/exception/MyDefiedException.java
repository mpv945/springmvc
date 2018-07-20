package org.haijun.study.coreTools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * 自定义异常{默认异常类为DefaultHandlerExceptionResolver，里面有对常用的异常进行处理，具体进入类查看}
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "参数有误，拒绝访问")//定制默认错误页面状态码和消息内容，作用在类上。也可以用在处理方法上
public class MyDefiedException extends RuntimeException { // throw new 该异常跳到默认的系统异常页面使用上面的设置

    static final long serialVersionUID = -1L;
}
