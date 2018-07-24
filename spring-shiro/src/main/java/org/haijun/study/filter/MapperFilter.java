package org.haijun.study.filter;

import org.haijun.study.dao.CountryMapper;
import org.haijun.study.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.*;
import java.io.IOException;
import java.util.List;

public class MapperFilter implements Filter {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        List<Test> countries = countryMapper.selectAll();
        System.out.println("过滤器通过mybatis获取数据");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
