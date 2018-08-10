<html>

<head><title> FreeMarker Spring MVC Hello World</title>
    <base id="base" href="${base}">
<#--    <link href="${base}/static/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <script src="${base}/static/bootstrap-3.3.4/js/bootstrap.min.js"></script>-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <#--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>-->
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>

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

    <script type="application/javascript">

        function checkmcode(){
            var mcode = $("#randomcode").val();
            alert(mcode);
            $.ajax({
                //async : false,
                //cache : false,
                type : 'POST',
                url : '${base}/kaptcha/checkmCode',// 请求的action路径
                data:{"mcode":mcode},//参数
                error : function() {// 请求失败处理函数
                },
                success : backfunc
            });
        }
        function backfunc(data){
            //var d = $.parseJSON(data);
            alert(data.success);
            if(data.success){
                $("#mcodecheck").attr("src","http://localhost:8083/images/ok.png");
            }else{
                $("#mcodecheck").attr("src","${base}/images/no.png");
            }
        }
    </script>

<body>
<div id="header">
    <H2>
        FreeMarker Spring MVC Hello World
    </H2>
</div>

<div id="content">

    <fieldset>
        <legend>Add Car</legend>
        <form name="car" action="/login" method="post">
            用户名 : <input type="text" name="username" />	<br/>
            密码: <input type="password" name="password" />	<br/>
            <dt>验证码:</dt>
            <dd>
                <input id="randomcode" name="randomcode" value="" onchange="checkmcode()" class="hst2"  style="vertical-align:middle"/>
                <img id=""  style="vertical-align:middle" alt="验证码" onclick="this.src='${base}/kaptcha/getKaptchaImage?e='+new Date()" src="${base}/kaptcha/getKaptchaImage"/>
                <img id="mcodecheck" src="" alt=""  style="vertical-align:middle"/>
            </dd>
            <input type="submit" value="   登陆   " />
        </form>
    </fieldset>

    <br/>
    <table class="datatable">
        <tr>
            <th>Make</th>  <th>Model</th>
        </tr>
    <#list model["carList"] as car>
	  	<tr>
            <td>${car.make}</td> <td>${car.model}</td>
        </tr>
    </#list>
    </table>

</div>
</body>
</html>