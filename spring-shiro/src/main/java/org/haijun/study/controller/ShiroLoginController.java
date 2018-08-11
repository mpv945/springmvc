package org.haijun.study.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.haijun.study.exception.CustomException;
import org.haijun.study.shiro.CustomRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShiroLoginController {

    //注入realm
    @Autowired
    private CustomRealm customRealm;

    //登陆提交地址，和applicationContext-shiro.xml中配置的loginurl一致
    @RequestMapping("login")
    public String login(HttpServletRequest request)throws Exception{

        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
                throw new CustomException("账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                throw new CustomException("用户名/密码错误");
            } else if("randomCodeError".equals(exceptionClassName)){
                throw new CustomException("验证码错误 ");
            }else {
                throw new Exception();//最终在异常处理器生成未知错误
            }
        }
        //此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        //登陆失败还到login页面
        return "login";
    }

    // 首页
    @RequestMapping("home")
    public String home(HttpServletRequest request){
        return "home";
    }

    // 角色测试 ；注解使用参考https://blog.csdn.net/w_stronger/article/details/73109248
    @RequiresRoles("admin")
    @RequestMapping("roleTest")
    public String testRole(HttpServletRequest request){
        return "home";
    }

    // 失败跳转
    @RequestMapping("refuse")
    public String refuse(HttpServletRequest request, Model model){
        return "refuse";
    }

    // 刷新当前操作人的凭证
    @RequestMapping("refresh")
    @ResponseBody
    public String refresh(HttpServletRequest request, Model model){
        //清除缓存，将来正常开发要在service调用customRealm.clearCached()
        customRealm.clearCached();
        return "清除缓存成功";
    }

    // 不受权限控制页面，测试shiro标签使用
    @RequestMapping("guest")
    public String guest(HttpServletRequest request){
        return "guest";
    }
}
