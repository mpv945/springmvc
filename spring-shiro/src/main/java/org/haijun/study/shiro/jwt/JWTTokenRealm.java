package org.haijun.study.shiro.jwt;

import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class JWTTokenRealm extends AuthorizingRealm {

    private String base64Security = "";

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //String username = JWTUtil.getUsername(principals.toString());// 获取登陆的凭证信息的用户名
        //UserBean user = userService.getUser(username);// 获取用户信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();// 创建授权信息
        //simpleAuthorizationInfo.addRole(user.getRole());
        //Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        //simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;

    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        Claims claims = JwtTools.parseJWT(token, base64Security);
        /*if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        UserBean userBean = userService.getUser(username);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (!JWTUtil.verify(token, username, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");

        }*/

        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
