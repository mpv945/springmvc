package org.haijun.study.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import java.io.File;

/**
 * 
 * <p>Title: CustomFormAuthenticationFilter</p>
 * <p>Description:自定义FormAuthenticationFilter，认证之前实现 验证码校验 </p>
 * @author	XXXXX.XXXX
 * @date	2015-3-25下午4:53:15
 * @version 1.0
 */

// 各个过滤器职责：参考https://blog.csdn.net/scholar_man/article/details/50387131
// FormAuthenticationFilter 最终继承 AccessControlFilter提供了访问控制的基础功能；比如是否允许访问/当访问拒绝时如何处理等：
	// isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
	// onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可
	// void setLoginUrl(String loginUrl) //身份验证时使用，默认/login.jsp
	// Subject getSubject(ServletRequest request, ServletResponse response) //获取Subject实例
	// boolean isLoginRequest(ServletRequest request, ServletResponse response)//当前请求是否是登录请求
	// void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException //将当前请求保存起来并重定向到登录页面
	// void saveRequest(ServletRequest request) //将请求保存起来，如登录成功后再重定向回该请求
	// void redirectToLogin(ServletRequest request, ServletResponse response) //重定向到登录页面

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
		boolean isLogin = super.isLoginRequest(request, response);
		System.out.println("是否是登陆请求"+isLogin);
		return isLogin;
	}

	//原FormAuthenticationFilter的认证方法
	// 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {

		String getContextPath = ((HttpServletRequest)request).getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+getContextPath+"/";
		String getRemoteAddress=request.getRemoteAddr();
		String getServletPath =((HttpServletRequest)request).getServletPath();
		// 项目据对路径
		String getServletContext_getRealPath =request.getServletContext().getRealPath(File.separator);
		String getRequestURL =((HttpServletRequest)request).getRequestURL().toString();
		String getRequestURI =((HttpServletRequest)request).getRequestURI();
		String getQueryString =((HttpServletRequest)request).getQueryString();
		String getRemoteUser =((HttpServletRequest)request).getRemoteUser();
		System.out.println("getContextPath:"+ getContextPath +"<br>");
		System.out.println("basePath:"+basePath+"<br>");
		System.out.println("getRemoteAddress:"+ getRemoteAddress +"<br>");
		System.out.println("getServletPath:"+ getServletPath +"<br>");
		System.out.println("getServletContext_getRealPath:"+ getServletContext_getRealPath +"<br>");
		System.out.println("getRequestURL:"+ getRequestURL +"<br>");
		System.out.println("getRequestURI:"+ getRequestURI +"<br>");
		System.out.println("getQueryString:"+ getQueryString +"<br>");
		System.out.println("getRemoteUser:"+ getRemoteUser +"<br>");
		//在这里进行验证码的校验
		
		//从session获取正确验证码
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session =httpServletRequest.getSession();
		//取出session的验证码（正确的验证码）
		String validateCode = (String) session.getAttribute("validateCode");
		
		//取出页面的验证码
		//输入的验证和session中的验证进行对比 
		String randomCode = httpServletRequest.getParameter("randomcode");
		if(randomCode!=null && validateCode!=null && !randomCode.equals(validateCode)){
			//如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
			httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
			//拒绝访问，不再校验账号和密码 
			return true;
		}
		return super.onAccessDenied(request, response);
	}

		
}
