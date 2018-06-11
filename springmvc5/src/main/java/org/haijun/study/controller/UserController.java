package org.haijun.study.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.haijun.study.annotation.ConvertRequestData;
import org.haijun.study.model.entity.User;
import org.haijun.study.model.vo.UserVO;
import org.haijun.study.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@Log4j2
@RequestMapping("/user")
@Api(tags = "用户管理", description = "用户增加，修改，删除")// value：url的路径值；tags：如果设置这个值、value的值会被覆盖
public class UserController {

	@Autowired
    private IUserService userService;

	// extensions 扩展属性,举例子；responseContainer = "" 这些对象是有效的 "List", "Set" or "Map".，其他无效
    // httpMethod  "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH"
    @ApiOperation(value = "用户注册", notes = "notes注册用户",response=UserVO.class)//response 用在@RequestBody这样的场景
    // @ApiModel(description = "群组") //描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用
    // @ApiParam(value = "Created user object", required = true)// 用在参数上 name:属性名称;value:属性值;defaultValue:默认属性值;example:举例子
    // @ApiResponses({ @ApiResponse(code = 400, message = "Invalid Order") })  //相应
    // @ResponseHeader(name="head1",description="response head conf") //相应头信息
    @GetMapping(value = {"/register"})
    public String register(){
        return "user/register"; // 返回页面时，最前面不能加/
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
