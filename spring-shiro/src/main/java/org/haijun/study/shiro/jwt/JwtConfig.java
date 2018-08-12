package org.haijun.study.shiro.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
// @EnableConfigurationProperties// 开启参数自定配置注入
//@ConfigurationProperties(prefix="jwt.config")
public class JwtConfig {

    private String iss;//(Issuser)：代表这个JWT的签发主体；
    //sub(Subject)：代表这个JWT的主体，即它的所有人；
    //aud(Audience)：代表这个JWT的接收对象；
    private Long exp;//(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
    //nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
    //iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
    //jti(JWT ID)：是JWT的唯一标识。


    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }
}
