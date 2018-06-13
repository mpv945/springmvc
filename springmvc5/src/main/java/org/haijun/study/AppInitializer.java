package org.haijun.study;

import com.alibaba.druid.support.http.StatViewServlet;
import org.haijun.study.config.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
// 获取spring上下文https://www.jianshu.com/p/fa5db63a12e5
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 通常是加载驱动应用后端的中间层和数据层组件，如Spring Security或Mybatis，由ContextLoaderListener创建
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{JpaRepositoriesConfig.class
                ,TaskSchedulerConfig.class
                //,AspectJConfig.class
                //,SwaggerConfig.class
                //,WebServiceConfig.class
                , DatabasePropertyConfig.class
                ,AsyncConfig.class};
    }

    /**
     * 加载包含Web组件的bean，如控制器、视图解析器以及处理器映射，由DispatcherServlet创建
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                ThymeleafConfig.class
                ,AspectJConfig.class
//                ,WebServiceConfig.class
                };
    }

    /**
     * 将一个或多个路径映射到DispatcherServlet上，“/”表示它是默认的Servlet，将处理进入应用的所有请求。
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // https://www.jianshu.com/p/c6e4d7de6e0a
    //要启用 Multipart 处理，你需要在 DispatcherServlet Spring 配置中声明名为 multipartResolver
/*    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        super.customizeRegistration(registration);
        registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
    }*/

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext webApplicationContext = super.createRootApplicationContext();
        String activeProfile = "dev";
        try {
            File cfgFile = ResourceUtils.getFile("classpath:db.properties");
            try (
                 InputStream inputStream = new FileInputStream(cfgFile)
            ) {
                Properties props = new Properties();
                props.load(inputStream);
                activeProfile = props.getProperty("spring.profiles.active");
            }
        } catch (IOException e) {
            activeProfile = "dev";
        }

        ((ConfigurableEnvironment)webApplicationContext.getEnvironment()).setActiveProfiles(activeProfile);
        return webApplicationContext;
    }

    /*添加配置servlet /filet 监听*/
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Druid监控 https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("DruidStatView",new StatViewServlet());
        dynamic.addMapping("/druid/*");// index.html 为主页面
        dynamic.setInitParameter("loginUsername","xiehaijun");
        dynamic.setInitParameter("loginPassword","12345");

        // cxf 配置

/*        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(WebServiceConfig.class);
        ContextLoaderListener loaderListener = new ContextLoaderListener(rootCtx);
        servletContext.addListener(loaderListener);*/


       /* ServletRegistration.Dynamic cxfynamicD = servletContext.addServlet("cxfDispatcher",new CXFServlet());
        cxfynamicD.addMapping("/services/*");*/

        super.onStartup(servletContext);
    }
}
