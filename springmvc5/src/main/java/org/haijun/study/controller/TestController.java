package org.haijun.study.controller;

import lombok.extern.log4j.Log4j2;
import org.haijun.study.model.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@Log4j2
@Controller
@Deprecated
public class TestController {

    //private static final Logger log = LogManager.getLogger(TestController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
    public String home(final Locale locale, final Model model) {
        log.info("测试处理开始");
        model.addAttribute("now", LocalDateTime.now().toString());
        model.addAttribute("date",new Date());
        model.addAttribute("msagessJava",this.messageSource.getMessage("foo.test", new Object[]{"测试","后台",1}, locale));
        UserVO userVo = new UserVO();
        userVo.setName("张三");
        userVo.setAge(9);
        userVo.setBirthday(new Date());
        model.addAttribute("userInfo",userVo);
        log.info("测试处理结束");
        return "home";
    }


}
