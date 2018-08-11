package org.haijun.study.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

// 参考https://blog.csdn.net/zzzgd_666/article/details/80475707
public class UrlPermissionResolver implements PermissionResolver {
    /**
     * 经过调试发现
     * subject.isPermitted(url) 中传入的字符串
     * 和自定义 Realm 中传入的权限字符串集合都要经过这个 resolver
     * @param s
     * @return
     */
    @Override
    public Permission resolvePermission(String s) {
        //logger.debug("s => " + s);

        // 权限信息是通过字符串：“/admin/**”等来进行匹配的。这时就不能使用 Shiro 默认的权限匹配器 WildcardPermission 了。
        if(s.startsWith("/")){
            return new UrlPermission(s);
        }
        return new WildcardPermission(s);
    }
}
