<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/26
  Time: 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <title>taglist</title>--%>
    <%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
    <%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
    <%--    <!-- Compiled and minified JavaScript -->--%>
    <%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
    <%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px">
    <h2 class="center">管&nbsp;理&nbsp;员</h2>
    <div class="divider"></div>
    <table class="centered highlight">
        <thead>
        <tr>
            <th>ID</th><th>Password</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.adminlist}" var="admin">
            <tr>
                <td>${admin.getAdmin_id()}</td><td>${admin.getPassword()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    $(document).ready(function(){
    });
</script>
</body>
</html>
