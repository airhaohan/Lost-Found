<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/19
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified JavaScript -->
    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <title>Admin Lost' Found</title>
    <link rel="stylesheet" href="css/${sessionScope.left}.css">
    <link rel="stylesheet" href="css/${sessionScope.right}.css">
    <style>
        div.right {
            -webkit-background-image: url("img/bg.svg");
            -webkit-background-size: cover;
            background-image: url("img/bg.svg");
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
        }
        div.left {
            -webkit-background-image: url("img/bg.svg");
            -webkit-background-size: cover;
            background-image: url("img/bg.svg");
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
        }
    </style>
</head>
<body class="row">
    <jsp:include page="left/${sessionScope.left}.jsp"></jsp:include>
    <jsp:include page="right/${sessionScope.right}.jsp"></jsp:include>
</body>
</html>
