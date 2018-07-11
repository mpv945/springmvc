<%--
  Created by IntelliJ IDEA.
  User: zhenx
  Date: 2018/7/10
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="test/success?uname=haijun&age=18">测试第一个test控制器</a><br/>
    <a href="test/path/url占位参数">url占位符的参数传递</a><br/>

    <br/>
    "PUT" 更新操作，DELETE删除操作
    <form action="test/update/1" method="post">
        <input type="hidden" name="_method" value="PUT" />
        <input type="submit" value="更新1的数据" />
    </form>
    <br/>
    请求参数
    <form action="test/sendParam" method="post">
        <input type="hidden" name="_method" value="PUT" />
        <input type="text" name="name">
        <input type="submit" value="RequestParam 测试" />
    </form><br/>
    添加对象
    <form action="/test/add" method="post">
        用户姓名<input type="text" name="name"><br/>
        邮箱<input type="email" name="email"/>
        <input type="submit" value="添加对象 测试" />
    </form><br/>
    <a href="/test/servletApiParam">测试servlet 原生api参数</a>
</body>
</html>
