<%--
  Created by IntelliJ IDEA.
  User: zhenx
  Date: 2018/7/10
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
   测试跳转到success页面成功
   <br/>
    国际化语言=<fmt:message key="i18n.uname"></fmt:message>
    <br/>
    <a href="/test/myView">自定义视图</a><br/>
   <a href="/test/myExeclView">导出execl</a><br/>
   <a href="/test/myPdfView">导出pdf</a><br/>
   <a href="/test/javaTubiao">java图表生成</a><br/>
   <%--如果使用spring 表单标签，框架认为需要回显表单，所以需要在model里面包含在表单字段的对象，或者@ModelAttribute创建--%>
    <form:form action="" method="post">
        姓名：<form:input path="name" /><%--path相当于name属性，用单选和多选以及select标签可以直接渲染Map数据，select需要指定显示lable--%>
    </form:form>
</body>
</html>
