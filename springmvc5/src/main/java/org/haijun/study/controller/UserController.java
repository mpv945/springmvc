package org.haijun.study.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.haijun.study.annotation.ConvertRequestData;
import org.haijun.study.async.AsyncTaskService;
import org.haijun.study.model.entity.User;
import org.haijun.study.model.vo.UserVO;
import org.haijun.study.service.IUserService;
import org.haijun.study.tools.SpringContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.lang.annotation.Inherited;
import java.util.concurrent.Future;

@Controller
@Log4j2
@RequestMapping("/user")
@Api(tags = "用户管理", description = "用户增加，修改，删除")// value：url的路径值；tags：如果设置这个值、value的值会被覆盖
public class UserController {

    // 使用SpEL，使用默认值
    @Value("#{systemProperties['some.key'] ?: 'my default system property value'}")
    private String spelWithDefaultValue;

    @Value("${some.key:one,two,three}")
    private String[] stringArrayWithDefaults;

    @Value("${some.key:true}")
    private boolean booleanWithDefaultValue;

    @Value("${some.key:}")//@Value("${some.key:my default value}")
    private String stringWithBlankDefaultValue;

/*    @Value("normal")
    private String normal; // 注入普通字符串

    @Value("#{systemProperties['os.name']}")
    private String systemPropertiesName; // 注入操作系统属性

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber; //注入表达式结果

    @Value("#{beanInject.another}")
    private String fromAnotherBean; // 注入其他Bean属性：注入beanInject对象的属性another，类具体定义见下面

    @Value("classpath:com/hry/spring/configinject/config.txt")
    private Resource resourceFile; // 注入文件资源

    @Value("http://www.baidu.com")
    private Resource testUrl; // 注入URL资源*/

	@Autowired
    private IUserService userService;

	@Autowired
    private AsyncTaskService asyncTaskService;

	// extensions 扩展属性,举例子；responseContainer = "" 这些对象是有效的 "List", "Set" or "Map".，其他无效
    // httpMethod  "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH"
    @ApiOperation(value = "用户注册", notes = "notes注册用户",response=UserVO.class)//response 用在@RequestBody这样的场景
    // @ApiModel(description = "群组") //描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用
    // @ApiParam(value = "Created user object", required = true)// 用在参数上 name:属性名称;value:属性值;defaultValue:默认属性值;example:举例子
    // @ApiResponses({ @ApiResponse(code = 400, message = "Invalid Order") })  //相应
    // @ResponseHeader(name="head1",description="response head conf") //相应头信息
    @GetMapping(value = {"/register"})
    public String register(){
        //futureAsyncTask();//有返回的异步任务
        // 异步执行的任务
        try {
            asyncTaskService.task1("异步任务");//异步十秒才做处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 缓存调用Cache
        UserVO cacheUser = new UserVO();
        cacheUser.setName("张三");
        log.info(userService.queryCacheObj(cacheUser));
        log.info(userService.queryCache(null,12));
        return "user/register"; // 返回页面时，最前面不能加/
    }

    // 有返回的异步任务，可以用来做多线程并发操作，最后统一返回结果的
    private void futureAsyncTask() {
        AsyncTaskService asyncTask = SpringContext.getBean(AsyncTaskService.class);//可以直接注入
        long currentTimeMillis = System.currentTimeMillis();
        //Future<String> task1 = asyncTask.task1();
        String result = null;
        long currentTimeMillis1 = 0;
        try {
            Future<String> task2 = asyncTask.task2();
            Future<String> task3 = asyncTask.task3("haijun");
            result = null;
            for (;;) {
                if(task2.isDone() && task3.isDone()) {
                    // 三个任务都调用完成，退出循环等待
                    break;
                }
                Thread.sleep(1000);
            }
            currentTimeMillis1 = System.currentTimeMillis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = "task任务总耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms";
    }

    @PostMapping("/addUser")
    @ConvertRequestData
    public String saveUser(@ModelAttribute("user") UserVO userVo,//@Valid
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("users", userService.list());
            return "editUsers";
        }
        User user = new User();
        BeanUtils.copyProperties(userVo,user);

        userService.save(user);
        return "redirect:/";
    }

    @ModelAttribute("user")
    public UserVO formBackingObject() {
        return new UserVO();
    }
    
}
