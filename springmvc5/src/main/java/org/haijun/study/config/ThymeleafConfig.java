package org.haijun.study.config;

import lombok.extern.log4j.Log4j2;
import org.haijun.study.tools.interceptor.MyHandlerInterceptor;
import org.haijun.study.tools.springFormatter.MyDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.messageresolver.SpringMessageResolver;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

// @EnableConfigurationProperties//开启对@ConfigurationProperties注解配置Bean的支持。把bena当作Properties来访问
@Configuration
@EnableWebMvc//<mvc:annotation-driven>
@ComponentScan(basePackages = { "org.haijun.study.controller"})
// extends WebMvcConfigurationSupport； 去掉@EnableWebMvc注解
@PropertySource({"classpath:common.properties"})// ,号隔开，可以配置多个，然后通@value获取
@EnableSwagger2
@Log4j2
public class ThymeleafConfig implements WebMvcConfigurer { // implements WebMvcConfigurer

    @Autowired
    private Environment env;

    // 去掉implements ApplicationContextAware
/*    private ApplicationContext applicationContext;

    public ThymeleafConfig() {
        super();
    }

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }*/

    /**
     * 资源模板解析器 //模板解析器
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        //templateResolver.setOrder(1);
        templateResolver.setCacheable(false);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //templateResolver.setApplicationContext(this.applicationContext);
        return templateResolver;
    }

    /**
     * 前端国际化资源配置
     * @param messageSource
     * @return
     */
    @Bean
    public SpringMessageResolver messageResolver(MessageSource messageSource){
        SpringMessageResolver messageResolver = new SpringMessageResolver();
        messageResolver.setMessageSource(messageSource);
        messageResolver.setOrder(1);
        return messageResolver;
    }

    /**SpringResourceTemplateResolver
     * 模板引擎
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver,
                                               SpringMessageResolver messageResolver) {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setMessageSource(messageResolver.getMessageSource());
        templateEngine.setMessageResolver(messageResolver);

        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.clearTemplateCache();
        // 开启EL
        templateEngine.setEnableSpringELCompiler(true);
        //templateEngine.setTemplateEngineMessageSource(messageSource());
        return templateEngine;
    }

    /**
     * Thymeleaf视图解析器
     * @param templateEngine
     * @return
     */
    @Bean
    public ViewResolver viewResolver(ISpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding("utf-8");
        //resolver.setCache(false);//去掉缓存
        resolver.setOrder(1);//设置优先级
        //resolver.setCacheUnresolved(false);
        //resolver.setViewNames(new String[]{"*.html"});
        return resolver;
    }


    /**
     * 静态资源配置
     * idea  File------>"Settings..." 在弹出的对话框中的搜索框中输入"File Encodings" ,勾选下方的to-ascii
     * @param registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // swagger 配置
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/images/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // excludePathPatterns 不拦截 ；addPathPatterns 拦截列表
        registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/").excludePathPatterns("/admin");
        registry.addInterceptor(localeChangeInterceptor());// i18n 拦截器
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    /**
     * ObjectMapper定制JSON和XML的消息转换。
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
                //.modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.xml().build()));
    }

    /**
     * 不需要逻辑就返回视图的控制器
     * @param registry
     */
/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        // 对 "/hello" 的 请求 redirect 到 "/home"
        registry.addRedirectViewController("/hello", "/home");
        // 对 "/admin/**" 的请求 返回 404 的 http 状态
        registry.addStatusController("/admin/**", HttpStatus.NOT_FOUND);
        // 将 "/home" 的 请求响应为返回 "home" 的视图
        registry.addViewController("/home").setViewName("home");
    }*/

    // https://juejin.im/entry/5addedcc6fb9a07acd4d58d3 这里配置视图解析器
    //  MVC 简化了视图解析器的注册。下面这个例子是根据内容协商使用JSP和Jackson作为默认视图返回JSON:
/*    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.jsp();
    }*/

    /*参数解析*//*
    void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers);

    *//*返回值解析*//*
    void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers);

    *//*异常处理*//*
    void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers);*/

    /**
     * 除了默认注册的那些之外，还添加Converters和Formatters。
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addFormatter(varietyFormatter());
        // vo在渲染时，如果使用${{vo.date}} 时候会触发
        registry.addFormatter(dateFormatter());
        //registry.addConverter(new CustomDateConverter());
        //super.addFormatters(registry);

    }


    @Bean
    public MyDateFormatter dateFormatter(){
        MyDateFormatter dateFormatter = new MyDateFormatter();
        return dateFormatter;
    }

    /**
     * 消息资源：MessageSource对象是管理Spring MVC中外部化文本的标准方式
     * basename属性表明我们将拥有类似于Messages_es.properties或Messages_en.properties
     * @return
     */
    @Bean
    @Description("Spring Message Resolver")
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        //设置编码
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        //source.setBasename("classpath:i18n/Messages");
        source.setBasename("i18n/Messages");
        // 把它设置为True，则找不到不会抛出异常，而是使用messageKey作为返回值。默认否则异常
        //source.setUseCodeAsDefaultMessage(true);
        //source.setCacheSeconds(0);
        return source;
    }

    /**
     * 设定本地默认语言环境
     * @return
     */
/*    @Bean
    public LocaleResolver localeResolver() {
        //SessionLocaleResolver
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);//new Locale("cn", "ZH")
        return cookieLocaleResolver;
    }*/

    /**
     * 设定验证消息资源
     * @return
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }



    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())// .apis(RequestHandlerSelectors.basePackage("org.baeldung.web.controller")) // 扫描路径
                .paths(PathSelectors.any())// 扫描路径路径
                .build()
                .enable(getSwaggerEnable(env.getProperty("swagger.enable")))//是否开启
                .apiInfo(apiInfo());
        // 自定义返回信息
/*        .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("500 message")
                                        .responseModel(new ModelRef("Error"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden!")
                                        .build()));*/
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "集成swagger的接口测试",
                "闻道有先后，术业有专攻。",
                "1.0.0",
                "http://blog.csdn.net/qq_27093465?viewmode=contents",
                new Contact("谢海军", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    // 判断是否需要开启swagger ui开关
    private static boolean getSwaggerEnable(String flg){
        log.info("swagger 配置参数为="+flg);
        if(StringUtils.isEmpty(flg)){
            return false;
        }
        return flg.equalsIgnoreCase("on");
    }

}
