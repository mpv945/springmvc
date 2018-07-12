package org.haijun.study.controller;

import org.haijun.study.coreTools.MyExcelView;
import org.haijun.study.coreTools.MyPdfView;
import org.haijun.study.vo.AccountVO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/*软件协议常见说明：GPL、AGPL、LGPL、Apache、ZLIB/LIBPNG、MIT*/
//GPL：如果包含GPL授权代码，重新打包以软件方式发布，必须新的软件也要开源，如果是使用或者调用可以不开源，例如发布web应用
//AGPL：由于现在网络服务公司兴起（如：google）产生了一定的漏洞，在web应用中调用了或者使用都必须开源，一般有商业版的选择
//LGPL：基于 LGPL 的软件也允许商业化销售，但不允许封闭源代码。
//Apache：该协议和BSD类似，同样鼓励代码共享和尊重原作者的著作权，同样允许代码修改，再发布（作为开源或商业软件）。
// 需要满足的条件也和BSD类似：1.需要给代码的用户一份Apache Licence；2.如果你修改了代码，需要在被修改的文件中说明。
// 3.在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。
// 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。
// Zlib/Libpng协议：不能歪曲原软件的著作权；修改后的软件不能歪曲为原版软件；不能删除源码中的协议许可内容；如果发布二进制代码可以不用附上源代码。
// BSD 协议：如果再发布的产品中包含源代码，则在源代码中必须带有原来代码中的BSD协议。
@Controller
@RequestMapping("/test")//修饰类（表示路径）
// 只能用在类上，可以把请求域Madel对象的值和对象类型存放到session
//@SessionAttributes(value = {"user"},types = {String.class})//该注解用来绑定HttpSession中的attribute对象的值，便于在方法中的参数里使用。该注解有value、types两个属性，可以通过名字和类型指定要使用的attribute 对象；
public class TestController {

    @Autowired
    private MyPdfView myPdfView;

   /* A、处理requet uri 部分（这里指uri template中variable，不含queryString部分）的注解：   @PathVariable;
    B、处理request header部分的注解：   @RequestHeader, @CookieValue;
    C、处理request body部分的注解：@RequestParam,  @RequestBody;
    D、处理attribute类型是注解： @SessionAttributes, @ModelAttribute;*/

    // 这种方式实际的效果就是在调用@RequestMapping的方法之前，为request对象的model里put（“account”， Account）；
    @ModelAttribute
    //public AccountVO addAccount(@ModelAttribute AccountVO accountVO) {
    public void addAccount(@RequestParam(value = "id", required = false) Integer id, Model model ) {
        //return accountManager.findAccount(number);
        System.out.println("调用@RequestMapping的方法之前被调用了");
        if(id != null){
            System.out.println("有id表示是修改");
            model.addAttribute("user",new AccountVO());// 这步配合@SessionAttributes可以存session，如果新增时。POJO 参数对象会根据这个对象填充（key和参数一样）
        }else {
            System.out.println("没有id表示新增");
        }
    }

    /**
     * @ModelAttribute 注解在参数上，首先查询 @SessionAttributes有无绑定的AccountVO对象，若没有则查询@ModelAttribute方法层面上是否绑定了AccountVO对象，
     * 若没有则将URI template中的值按对应的名称绑定到Pet对象的各属性上。
     * @param accountVO
     * @return
     */
    @RequestMapping(value="/owners/{ownerId}/pets/{petId}/edit", method = RequestMethod.POST)
    public String add(@ModelAttribute AccountVO accountVO){
        return "success";
    }
    /**
     * params 可以包含参数集合，!参数表示不能包含该参数，否则必须包含。age!=10表示能包含10,headers功能类似
     * value 支持ant风格的路径，？匹配一个字符；*匹配一个路径的任意字符；** 匹配多层路径
     * @return
     */
    @RequestMapping(value = "/success",method = RequestMethod.GET, params = {"uname","age!=200"})//修饰控制器处理哪个路径url（多个url是与的关系）,method指定请求方式
    public String hello(String uname){
        System.out.println("hell word,用户姓名="+uname);
        return "success";// 返回结果=配置视图解析器配置的前缀+"success"+解析器配置的后缀，做转发操作
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public String updateTest(@PathVariable("id") Integer id){
        System.out.println("更新数据了="+id==null?"":id);
        return "success";//默认使用InternalResourceView作为视图实现，如果假如jstl，那么实现会变成JstlView；根据xml配置的视图解析器，最终拼接资源进行重定向
    }
    /**
     * 隐射url的占位符的值到参数中
     * @return
     */
    @RequestMapping("/path/{param}")
    public String path(@PathVariable("param") String val,@RequestHeader("Accept-Encoding") String encoding,
                       @CookieValue("JSESSIONID") String cookie,Model model){
        try {
            System.out.println("传递参数="+new String(val.getBytes("ISO-8859-1"), "UTF-8"));
            System.out.println("编码信息="+encoding);
            System.out.println("JSESSIONID="+cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }


    /*@RequestParam
    A） 常用来处理简单类型的绑定，通过Request.getParameter() 获取的String可直接转换为简单类型的情况
    （ String--> 简单类型的转换操作由ConversionService配置的转换器来完成）；
            因为使用request.getParameter()方式获取参数，所以可以处理get 方式中queryString的值，也可以处理post方式中 body data的值；
    B）用来处理Content-Type: 为 application/x-www-form-urlencoded编码的内容，提交方式GET、POST；
    C) 该注解有两个属性： value、required； value用来指定要传入值的id名称，required用来指示参数是否必须绑定；*/

    @RequestMapping(value = "/sendParam")
    public String sendParam(@RequestParam(value = "name" ,required = true, defaultValue = "") String name,
                            @RequestHeader("Content-Type") String contentType){//value为参数名
        System.out.println("请求类型="+contentType+"请求的姓名="+name);
        return "success";
    }

    /*@RequestBody
    该注解常用来处理Content-Type: 不是application/x-www-form-urlencoded编码的内容，例如application/json, application/xml等；
    它是通过使用HandlerAdapter 配置的HttpMessageConverters来解析post data body，然后绑定到相应的bean上的。
    因为配置有FormHttpMessageConverter，所以也可以用来处理 application/x-www-form-urlencoded的内容，处理完的结果放在一个MultiValueMap<String, String>里，
    这种情况在某些特殊需求下使用，详情查看FormHttpMessageConverter api;*/
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public String sendObject(AccountVO accountVO){ //支持级联属性（内置对象属性对象属性，小写开头）
        System.out.println(accountVO);
        return "success";
    }

    /**
     * 要使用Sevlet的一些API，只要直接将其作为参数传入即可。你需要request，就传入request， 需要session，就传入session。
     * 使用servlet原生对象参数:
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale
     * InputStream
     * OutputStream
     * Reader
     * Writer
     * @return
     */
    @RequestMapping(value = "/servletApiParam")//,produces = "text/html;charset=UTF-8"
    public void testServletAPI(HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session,
                                 Writer out) throws IOException{
        System.out.println(request);// 打断点，debug查看以前执行代码端点
        System.out.println(response);
        System.out.println(session);
        response.setContentType("text/html;charset=utf-8");
        try {
            //out.write(new String("测试返回流".getBytes(Charset.forName("UTF-8"))));
            out.write("测试返回流");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/modelAndView")
    public ModelAndView pageView(){
        Map<String,Object> data = new HashMap<>();
        data.put("names", Arrays.asList("zhangshan","lisi"));
       return new ModelAndView("view",data);
    }
    @RequestMapping("/modelAndView2")
    public String pageView(Model model){ //模型对象最后都是BindingAwareModelMap extends ExtendedModelMap类型
        Map<String,Object> data = new HashMap<>();
        data.put("names", Arrays.asList("zhangshan","lisi"));
        //model.addAllAttributes(data);
        model.addAttribute("data",data);
        return "view";
    }

    @RequestMapping("/myView")
    public String myView(){
        System.out.println("自定义视图，返回值为自定义视图实现类的bean 名字，类名前面小写");
        return "myView";
    }

    /**
     * 对word操作https://www.cnblogs.com/wzzkaifa/p/7140506.html
     * @return
     */
    @RequestMapping("/myExeclView")
    public ModelAndView myExeclView(){
        System.out.println("自定义视图，返回值为自定义视图实现类的bean 名字，类名前面小写");
        Map<String,Object> data = new HashMap<>();
        data.put("name", "谢海军测试导出execl");
        // 自定义视图
        View excelView = new MyExcelView();
        return new ModelAndView(excelView,data);
    }

    /**
     * 参考：https://blog.csdn.net/TOP__ONE/article/details/65442390
     * https://blog.csdn.net/eff666/article/details/64923590
     * @return
     */
    @RequestMapping("/myPdfView")
    public ModelAndView myPdfView(){
        System.out.println("自定义视图，返回值为自定义视图实现类的bean 名字，类名前面小写");
        Map<String,Object> data = new HashMap<>();
        data.put("name", "谢海军测试导出pdf");
        // 自定义视图
        return new ModelAndView(myPdfView,data);
    }

    /**
     * api 文档：http://www.jfree.org/jfreechart/api/javadoc/index.html
     * 具体实现：https://blog.csdn.net/crazy1235/article/details/8535999
     * @param response
     */
    @RequestMapping("/javaTubiao")
    public void javaTubiao(HttpServletResponse response){
        response.setContentType("image/jpeg");// 将输出设置为image/jepg格式
        DefaultPieDataset data = getDataSet();// 创建数据集合容器
        JFreeChart chart = ChartFactory.createPieChart3D("水果产量图", // 图表标题
                data, // 数据集
                true, // 是否显示图例
                false, // 是否生成工具
                false // 是否生成URL链接
        );// 创建图表
        Font titleFont = new Font("黑体", Font.BOLD, 20);
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(titleFont);// 为标题设置上字体
        Font plotFont = new Font("宋体", Font.PLAIN, 16);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(plotFont); // 为饼图元素设置上字体
        Font LegendFont = new Font("楷体", Font.PLAIN, 18);
        LegendTitle legend = chart.getLegend(0);
        legend.setItemFont(LegendFont);// 为图例说明设置字体
        plot.setLabelGenerator(
                new StandardPieSectionLabelGenerator("{0} {2}",
                        NumberFormat.getNumberInstance(),
                        new DecimalFormat("0.00%")));// 显示百分比
        try {
            ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 450,new ChartRenderingInfo(
                    new StandardEntityCollection()));// 输出图表
            // 鼠标悬浮效果 https://blog.csdn.net/zhao19861029/article/details/6873277
            //ServletUtilities.saveChartAsPNG()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建一个数据集合
     * @return
     */
    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("苹果", 100);
        dataset.setValue("梨子", 200);
        dataset.setValue("葡萄", 300);
        dataset.setValue("香蕉", 400);
        dataset.setValue("荔枝", 500);
        return dataset;
    }
}
