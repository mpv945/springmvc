<%--
  Created by IntelliJ IDEA.
  User: zhenx
  Date: 2018/7/10
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
</body>
</html>
