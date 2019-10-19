<%--
  Created by IntelliJ IDEA.
  User: 14551
  Date: 2019/9/5
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<html>
<head>
    <title>login</title>
    <script src="/static/js/jquery-1.8.3.js"></script>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        <dl>
            <dt>用户登录</dt>
            <dd><label for="name">用户名:</label>
                <input type="text" name="username" value="${username}" id="name"/></dd>
            <dd><label for="password">密码:</label>
                <input type="password" name="userpassword" value="${userpassword}" id="password"></dd>
            <dd><label for="passwords">验证码:</label>
                <input type="text" name="inputCode" value="" id="passwords">
                <span>
                    <img id="validateCode" src="/Number.jsp" width="96" height="27"/>
                </span>
                <span>
                    <a  id="changCode" href="javascript:void(0);" >看不清</a>
                </span></dd>
            <dd>
                <span style="Font-size:18px;color:red;">${erroMsg}</span>
            </dd>
            <dd>
                <input type="submit" value="登录"/>
            </dd>
            <dd>
                <input type="button" value="注册" onclick="location='${pageContext.request.contextPath}/register.jsp'">
            </dd>
        </dl>
    </form>
    <script>
        $(function () {
            $("#changCode").mousedown(function () {
                $("#validateCode").attr("src","Number.jsp?"+Math.random());
                return false
            });
        });
    </script>
</body>
</html>
