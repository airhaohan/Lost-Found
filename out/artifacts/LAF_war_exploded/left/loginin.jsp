<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/12
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>loginin</title>--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
<%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
<%--    <!-- Compiled and minified JavaScript -->--%>
<%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s4 z-depth-2 left">
    <div class="menu-top">
        <img src="${sessionScope.user.getProfile()}" alt="" class="circle responsive-img" style="height: 40px">
        <a href="ClientServlet?op=viewpost" class="black-text ml-5">首页</a>

        <a href="ClientServlet?op=usercenter" class="black-text ml-5 modal-trigger">个人中心</a>
        <a href="ClientServlet?op=msglist" class="black-text ml-5 modal-trigger">消息</a>
        <a href="#" data-target="slide-out" class="sidenav-trigger">
            <i class="material-icons prefix black-text">menu</i>
        </a>
    </div>
    <h3>你好，</h3>
    <h3>${sessionScope.user.getUsername()}</h3>
    <h3>来帮助他人/自己吧&rightarrow;</h3>
    <%
        String right = (String) session.getAttribute("right");
        if (!right.equals("newpost")) {
    %>
        <div class="fixed-action-btn">
            <a class="btn-floating btn-large red tooltipped" data-tooltip="发布新帖~" data-position="left" href="ClientServlet?op=newpost">
                <i class="large material-icons">mode_edit</i>
            </a>
        </div>
    <% } %>
</div>

<div id="slide-out" class="sidenav" style="width: 33vw;">
    <img src="${sessionScope.user.getProfile()}" alt="" class="circle responsive-img">
    <h6>
        <a href="index.jsp" class="black-text">首页</a>
    </h6>
    <h6>
        <a href="ClientServlet?op=usercenter" class="black-text modal-trigger">个人中心</a>
    </h6>
    <h6>
        <a href="ClientServlet?op=msglist" class="black-text modal-trigger">消息</a>
    </h6>
    <div class="divider"></div>
    <h6>
        <a href="ClientServlet?op=viewannounce" class="black-text">系统公告</a>
    </h6>
    <h6>
        <a href="ClientServlet?op=concernlist" class="black-text modal-trigger">我的关注</a>
    </h6>
    <h6>
        <a href="ClientServlet?op=userpostlist" class="black-text modal-trigger">我的发布</a>
    </h6>
    <div id="privacy">
        <a href="<%=path%>/index.jsp">退出登录</a>
        &nbsp;|&nbsp;
        <a href="ClientServlet?op=viewannounce">使用条款</a>
        &nbsp;|&nbsp;
        <a href="" class="modal-trigger" data-target="deleteaccount">注销账号</a>
    </div>
</div>

<div id="deleteaccount" class="modal form-control">
    <div class="editmodal">
        <form action="ClientServlet?op=deleteaccount" method="post">
            <h3 class="center my-5">是否注销账号？</h3>
            <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                <button type="submit" class="btn red white-text waves-effect waves-light btn-large">确&nbsp;&nbsp;定</button>
            </div>
            <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                <button type="button" class="modal-close btn teal white-text waves-effect waves-light btn-large">取&nbsp;&nbsp;消</button>
            </div>
        </form>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('.tooltipped').tooltip();
        $("h3").hide();
        $("h3").fadeIn();
        $('.sidenav').sidenav();
        $('.modal').modal();
    });
</script>
</body>
</html>

