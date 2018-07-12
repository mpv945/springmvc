package org.haijun.study.coreTools;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Component
public class MyView implements View {
    @Override
    public String getContentType() {
        return "text/html"; // 返回内容类型
    }

    /**
     * 渲染视图
     * @param map
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.getWriter().print("我是自定义视图"+new Date());
    }
}
