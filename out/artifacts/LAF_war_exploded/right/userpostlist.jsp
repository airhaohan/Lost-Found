<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/10
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% String path = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <title>viewpost</title>--%>
    <%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
    <%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
    <%--    <link rel="stylesheet" href="<%=path%>/css/viewpost.css">--%>
    <%--    <!-- Compiled and minified JavaScript -->--%>
    <%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
    <%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px" id="viewpost">
    <a href="ClientServlet?op=${sessionScope.rbackpage}" class="rbackpage black-text"><i class="material-icons right">arrow_back</i></a>
    <h2 class="center">我的发布</h2>
    <div class="divider"></div>

    <!-- post -->
    <c:forEach items="${sessionScope.userposts}" var="post">
        <div class="card horizontal postlist-item z-depth-2 my-5">
            <div class="card-image waves-effect waves-block waves-light">
                <a href="ClientServlet?op=postdetail&postid=${post.getPost_id()}">
                    <img class="activator" src="${post.getPost_pictures().get(0)}">
                </a>
            </div>
            <div class="card-content">
                    <span class="card-title activator grey-text text-darken-4">
                            ${post.getPost_title()}<i class="material-icons right">more_vert</i>
                    </span>
                <div class="chip">
                    <c:if test="${post.getPost_type()}">寻物</c:if>
                    <c:if test="${!post.getPost_type()}">招领</c:if>
                </div>
                <c:forEach items="${post.getTags()}" var="posttag">
                    <div class="chip">${posttag}</div>
                </c:forEach>
                <p class="post-content"></p>
                <p class="post-time"><fmt:formatDate value="${post.getPost_date()}" pattern="yyyy-MM-dd HH:mm" /></p>
            </div>
            <div class="card-reveal">
                <span class="card-title grey-text text-darken-4">${post.getPost_title()}<i class="material-icons right">close</i></span>
                <p style="margin: 20px 0 0 0;line-height:2rem">${post.getDetail()}</p>
            </div>
        </div>
    </c:forEach>

    <br/>
    <br/>
    <br/>
    <br/>
</div>

<script>
    $(document).ready(function(){
        $('.modal').modal();

    });
</script>
</body>
</html>
