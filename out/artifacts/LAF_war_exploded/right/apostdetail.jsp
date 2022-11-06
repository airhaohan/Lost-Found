<%@ page import="domain.User" %>
<%@ page import="java.util.Objects" %>
<%@ page import="domain.Post" %><%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/16
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <title>postdetail</title>--%>
    <%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
    <%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
    <%--    <!-- Compiled and minified JavaScript -->--%>
    <%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
    <%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px">
    <a href="ManagerServlet?op=${sessionScope.rbackpage}" class="rbackpage black-text"><i class="material-icons right">arrow_back</i></a>
    <h2 class="center">详&nbsp;情</h2>
    <div class="card">
        <div class="entity">
            <a href="ManagerServlet?op=othersinfo"><img src="${sessionScope.nowentity.getProfile()}" alt="" class="circle responsive-img"></a>
            <div class="entity-title">
                <h5>${sessionScope.nowentity.getUsername()}</h5>
                <p><fmt:formatDate value="${sessionScope.nowpost.getPost_date()}" pattern="yyyy-MM-dd HH:mm" /></p>
            </div>
        </div>
        <h3 class="title">${sessionScope.nowpost.getPost_title()}</h3>
        <div class="divider"></div>
        <div class="detail">
            ${sessionScope.nowpost.getDetail()}
        </div>
        <div class="tags">
            <div class="chip">
                <% if (((Post)session.getAttribute("nowpost")).getPost_type()) {
                %> 寻物 <% } else { %> 招领 <% } %>
            </div>
            <c:forEach items="${sessionScope.nowposttags}" var="tag">
                <div class="chip un">${tag.getTag_name()}</div>
            </c:forEach>
        </div>
        <div class="carousel carousel-slider center" style="height: 300px">
            <c:forEach items="${sessionScope.nowpost.getPost_pictures()}" var="picpath">
                <a class="carousel-item"><img src="${picpath}" alt="" class="materialboxed"></a>
            </c:forEach>
        </div>
        <div class="fixed-action-btn">
            <a class="btn-floating btn-large red tooltipped modal-trigger" data-tooltip="删除贴子" data-position="left"  data-target="deletepost">
                <i class="large material-icons">delete_forever</i>
            </a>
        </div>
        <br/>

        <%-- delete post--%>
        <div id="deletepost" class="modal form-control">
            <div class="editmodal">
                <form action="ManagerServlet?op=deletepost" method="post">
                    <h3 class="center my-5">是否删除？</h3>
                    <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                        <button type="submit" class="btn red white-text waves-effect waves-light btn-large">确&nbsp;&nbsp;定</button>
                    </div>
                    <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                        <button type="button" class="modal-close btn teal white-text waves-effect waves-light btn-large" id="cancelDeletePost">取&nbsp;&nbsp;消</button>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<script>
    $(function() {
        $('.modal').modal();
        $('.fixed-action-btn').floatingActionButton();
        $('.tooltipped').tooltip();
        $('.materialboxed').materialbox();
        $('.carousel').carousel({
            duration: 100,
            fullWidth: true,
            indicators: true
        });

    });
</script>
</body>
</html>