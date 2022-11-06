<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/12
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Loading...</title>
    <style>
        body {
            background-image: url("img/bg.svg");
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
            height: 100vh;
            width: 100vw;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        h1 {
            font-size: 4rem !important;
        }
    </style>
</head>
<body>
    <h1 style="display: block">${message}</h1>
    <br/>
    <h2 style="display: block">即将跳转。。。</h2>
    <% response.setHeader("refresh", "1;URL=client.jsp"); %>
</body>
</html>
