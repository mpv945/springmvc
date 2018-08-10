package org.haijun.study.shiro.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ValidateImg extends DefaultKaptcha {

    // 具体配置参考https://blog.csdn.net/zona_wzq/article/details/78059565
    public ValidateImg(){
        Properties p = new Properties();
        p.setProperty("kaptcha.border", "no");
        p.setProperty("kaptcha.background.clear.from","white");
        p.setProperty("kaptcha.background.clear.to","white");
        p.setProperty("kaptcha.textproducer.font.color","black");
        p.setProperty("kaptcha.image.width","200");
        p.setProperty("kaptcha.image.height","50");
        p.setProperty("kaptcha.session.key","code");
        p.setProperty("kaptcha.textproducer.char.length","4");
        p.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        Config config = new Config(p);
        this.setConfig(config);
    }
}
