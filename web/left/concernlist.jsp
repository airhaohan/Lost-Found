<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/19
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>usercenter</title>--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
<%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
<%--    <!-- Compiled and minified JavaScript -->--%>
<%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s4 z-depth-2 left">
    <a href="ClientServlet?op=loginin" class="backpage black-text"><i class="material-icons">arrow_back</i></a>
    <h3 class="center">关&nbsp;&nbsp;注</h3>
    <div class="leftdivider"></div>

    <c:forEach items="${sessionScope.concernlist}" var="concern">
    <div class="card horizontal concern-item z-depth-2 my-5">
        <div class="card-image waves-effect waves-block waves-light">
            <a href="ClientServlet?op=postdetail&postid=${concern.getPost().getPost_id()}">
                <img class="activator" src="${concern.getPost().getPost_pictures().get(0)}">
            </a>
        </div>
        <div class="card-content">
                <span class="card-title grey-text text-darken-4">
                    ${concern.getPost().getPost_title()}
                </span>
            <div class="chip">
                <c:if test="${concern.getPost().getPost_type()}">寻物</c:if>
                <c:if test="${!concern.getPost().getPost_type()}">招领</c:if>
            </div>
            <c:forEach items="${concern.getPost().getTags()}" var="posttag">
                <div class="chip">${posttag}</div>
            </c:forEach>
            <p class="concernpost-time"><fmt:formatDate value="${concern.getPost().getPost_date()}" pattern="yyyy-MM-dd HH:mm" /></p>
        </div>
    </div>
    </c:forEach>

</div>


<script>
    $(function() {
        var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"];
    });
</script>
</body>
</html>
