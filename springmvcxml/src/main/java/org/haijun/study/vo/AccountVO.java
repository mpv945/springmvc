package org.haijun.study.vo;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

public class AccountVO {

    /*JSR303 标准的实现为Hibernate Validator（其中除了实现标准的以外还支持扩展注解，@Email,@Length,@NotEmpty,@Range）*/
    // 加入Hibernate Validator，需要<mvc:annotation-driven 配置，增加本地的验证类，各种注解参考网上文档
    // 如果希望错误消息国际化，请在资源配置文件添加key=验证的注解名.控制器@Valid对象的类名小写.属性名，例如该key为NotBlank.accountVO.name=名字不能为空
    // 对其他异常也能做国际化，例如@RequestParam 默认required = true,如果没传入，提示错误，可以使用required.参数名
    //      当使用POJO作为入参，类型转换异常可以使用typeMismatch.{POJO 类名首小写}.字段名=错误消息
    //      spring MVC在调用处理方法时发生了错误，使用methodInvocation.?网站找资料
    @NotBlank
    private String name;

    @Email//(message = "{items.email.valid.msg}")直接指定消息内容，通过占位
    private String email;

    /*日期格式化 前端字符串转date*/
    //DataBinder 的conversion组件FormattingC.....FactroyBean内部注册了JodaDae.....Factroy这个工厂方法支持该注解
    @DateTimeFormat(pattern = "yyyy-MM-dd")// 支持iso和style属性
    @Past// 之前的时间
    private Date birth;

    /*对前端 12,234,455.23*/
    @NumberFormat(pattern = "#,###,###.#")//还支持style
    private Float salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AccountVO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
