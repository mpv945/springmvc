<html>
<head><title> FreeMarker Spring MVC Hello World</title>

    <style>
        body, input {
            font-family: Calibri, Arial;
            margin: 0px;
            padding: 0px;
        }
        #header h2 {
            color: white;
            background-color: #3275A8;
            height: 50px;
            padding: 5px 0 0 5px;
            font-size: 20px;
        }

        .datatable {margin-bottom:5px;border:1px solid #eee;border-collapse:collapse;width:400px;max-width:100%;font-family:Calibri}
        .datatable th {padding:3px;border:1px solid #888;height:30px;background-color:#B2D487;text-align:center;vertical-align:middle;color:#444444}
        .datatable tr {border:1px solid #888}
        .datatable tr.odd {background-color:#eee}
        .datatable td {padding:2px;border:1px solid #888}
        #content { padding 5px; margin: 5px; text-align: center}
        fieldset { width: 300px; padding: 5px; margin-bottom: 0px; }
        legend { font-weight: bold; }
    </style>

<body>
<div id="header">
    <H2>
        FreeMarker Spring MVC Hello World
    </H2>
</div>

<div id="content">


    <br/>
    <table class="datatable">
        <tr>
            <@shiro.user>
                欢迎[<@shiro.principal property="name"/>]登录，<a href="/logout">退出</a>
            </@shiro.user>
            <@shiro.authenticated>
                用户[<@shiro.principal/>]已身份验证通过
            </@shiro.authenticated>
            <@shiro.hasRole name="系统管理员">
                用户[<@shiro.principal/>]拥有角色系统管理员<br/>
            </@shiro.hasRole>
            guest标签：验证当前用户是否为“访客”，即未认证（包含未记住）的用户：
            <@shiro.guest>是访客</@shiro.guest>
<#--            <br>user标签：认证通过或已记住的用户：
            <@shiro.user>认证通过或已记住的用户</@shiro.user>
            <br>authenticated标签：已认证通过的用户。不包含已记住的用户：
            <@shiro.authenticated>已认证通过的用户。不包含已记住的用户</@shiro.authenticated>
            <br>notAuthenticated标签：未认证通过的用户。与authenticated标签相对：
            <@shiro.notAuthenticated>未认证通过的用户</@shiro.notAuthenticated>
            <br>principal标签：输出当前用户信息，通常为登录帐号信息：&lt;#&ndash;，shiro标签：<@shiro.principal property="name" />&ndash;&gt;
            <@shiro.principal property="name" />
            <br>hasRole标签：验证当前用户是否属于该角色：
            <@shiro.hasRole name="admin">Hello admin!</@shiro.hasRole>
            <br>hasAnyRoles标签：验证当前用户是否属于这些角色中的任何一个，角色之间逗号分隔 ，：
            <@shiro.hasAnyRoles name="admin,user,operator">Hello admin!</@shiro.hasAnyRoles>
            <br>hasPermission标签：验证当前用户是否拥有该权限：
            <@shiro.hasPermission name="/order:*">订单/@shiro.hasPermission>
            <br>lacksPermission标签：验证当前用户不拥有某种权限，与hasPermission标签是相对的，：
            <@shiro.lacksPermission name="/order:*">trade</@shiro.lacksPermission>
            <br>lacksRole标签：验证当前用户不属于该角色，与hasRole标签想反，-->
        </tr>

    </table>

</div>
</body>
</html>