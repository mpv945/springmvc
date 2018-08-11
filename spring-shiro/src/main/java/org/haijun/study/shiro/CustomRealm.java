package org.haijun.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.haijun.study.model.dto.PermissionDO;
import org.haijun.study.model.dto.UserDO;
import org.haijun.study.model.vo.ActiveUserVO;
import org.haijun.study.service.IPermissionService;
import org.haijun.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <p>
 * Title: CustomRealm
 * </p>
 * <p>
 * Description:自定义realm
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-3-23下午4:54:47
 * @version 1.0
 */
public class CustomRealm extends AuthorizingRealm {
	
	//注入service
	@Autowired
	private IUserService userService;

	@Autowired
	private IPermissionService permissionService;

	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	// 用于认证
	//realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// token是用户输入的用户名和密码 
		// 第一步从token中取出用户名
		String userCode = (String) token.getPrincipal();

		// 第二步：根据用户输入的userCode从数据库查询
		UserDO userInfo = userService.findUserByUserCode(userCode);

		// 如果查询不到返回null
		if(userInfo==null){
			return null;
		}
		// 从数据库查询到密码
		String password = userInfo.getPassword();
		
		//盐
		String salt = userInfo.getSalt();

		// 如果查询到返回认证信息AuthenticationInfo
		//activeUser就是用户身份信息
		ActiveUserVO activeUser = new ActiveUserVO();
		activeUser.setUserId(userInfo.getId());
		activeUser.setUserCode(userInfo.getUsercode());
		activeUser.setUserName(userInfo.getUsername());

		//将用户菜单 设置到activeUser
		activeUser.setPermissionDOS(permissionService.findMenuListByUserId(userInfo.getId()));

		//将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				activeUser, password,ByteSource.Util.bytes(salt), this.getName());

		return simpleAuthenticationInfo;
	}

	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		//从 principals获取主身份信息
		//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		ActiveUserVO activeUser =  (ActiveUserVO) principals.getPrimaryPrincipal();
		
		//根据身份信息获取权限信息
		//从数据库获取到权限数据
		List<PermissionDO> permissionList = permissionService.findPermissionListByUserId(activeUser.getUserId());
		//单独定一个集合对象 
		List<String> permissions = new ArrayList<String>();
		if(!CollectionUtils.isEmpty(permissionList)){
			permissions = permissionList.stream().map(PermissionDO::getPercode).filter(percode->StringUtils.isEmpty(percode)).collect(Collectors.toList());
		}
	/*	List<String> permissions = new ArrayList<String>();
		permissions.add("user:create");//用户的创建
		permissions.add("item:query");//商品查询权限
		permissions.add("item:add");//商品添加权限
		permissions.add("item:edit");//商品修改权限
*/		//....
		
		//查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);
		// 角色插入
		simpleAuthorizationInfo.addRole("admin");


		return simpleAuthorizationInfo;
	}
	
	//清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
		/*this.clearCachedAuthorizationInfo(principals);//缓存的授权信息
		this.clearCachedAuthenticationInfo(principals);// 认证信息*/
	}


}
