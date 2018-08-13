package org.haijun.study.shiro.jwt;

import org.apache.shiro.web.servlet.SimpleCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取token作为 sessionId（之前从Cookie取，现在先在Header取，没有再从请求参数取（？号后面
 *
 * 默认从 Cookie 读取 SessionId，但我们需要从请求头部读取 Token 字段
 */
public class JWTSimpleCookie extends SimpleCookie {

/*    public JWTSimpleCookie(String name) {
        super(name);
    }*/

    @Override
    public String readValue(HttpServletRequest request, HttpServletResponse ignored) {
        //读取 SessionId，即 jwt 串
        String value = request.getHeader(getName());// 需要跟token设定的name设定key
        if (value == null)
            value = request.getParameter(getName());
        return value;// 如果为空会调用SessionIdGenerator生成新的sessionId，所以重写
    }


}
