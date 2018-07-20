package org.haijun.study.coreTools.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyTestInterceptor implements HandlerInterceptor {

    // 业务的目标方法处理请求之前调用，需要后面的拦截和业务处理方法执行返回true，不要返回false
    // 可以考虑做权限，日志，事物
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    // 这个方法在业务处理器请求完后渲染视图之前，但是DispatcherServlet向客户相应前被调用（如果preHandle返回false，该方法不执行）
    // 可以对请求域中的属性和视图进行修改
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 渲染视图之后被调用（如果拦截器的preHandle 返回false，该方法就不会执行了，没必要取回收资源了）
    // 释放资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
