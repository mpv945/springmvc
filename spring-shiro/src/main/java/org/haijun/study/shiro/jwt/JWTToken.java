package org.haijun.study.shiro.jwt;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 *  支持token 参考https://www.jianshu.com/p/f37f8c295057 和 https://blog.csdn.net/he90227/article/details/53308222 和 https://blog.csdn.net/pomer_huang/article/details/78138465
 */
public class JWTToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private String token;
    //private char[] password;
    private boolean rememberMe;
    private String host;

    public JWTToken() {
        this.rememberMe = false;
    }

    public JWTToken(String token) {
        this(token,  false, (String)null);
    }

    public JWTToken(String token, String host) {
        this(token,  false, host);
    }

    public JWTToken(String token, boolean rememberMe) {
        this(token, rememberMe, (String)null);
    }

    public JWTToken(String token, boolean rememberMe, String host) {
        this.rememberMe = false;
        this.token = token;
        this.rememberMe = rememberMe;
        this.host = host;
    }


    /**\
     * doGetAuthenticationInfo 获取信息的方法
     * @return
     */
    public Object getPrincipal() {
        return this.token;
    }

    /**
     * 密码校验会调用该方法
     * @return
     */
    public Object getCredentials() {
        return this.token;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        this.token = null;
        this.host = null;
        this.rememberMe = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" - ");
        sb.append(this.token);
        sb.append(", rememberMe=").append(this.rememberMe);
        if (this.host != null) {
            sb.append(" (").append(this.host).append(")");
        }

        return sb.toString();
    }
}
