<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/10
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath(); %>
<html>
<head>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>unloginin</title>--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
<%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="<%=path%>/css/unloginin.css">--%>
<%--    <!-- Compiled and minified JavaScript -->--%>
<%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
<%--    <title>Title</title>--%>
</head>
<body class="row">
    <div class="col s4 z-depth-2 left">
        <div class="menu-top">
            <img src="<%=path%>/img/unloginin-profile.webp" alt="" class="circle responsive-img" style="height: 40px">
            <a href="ClientServlet?op=viewpost" class="black-text ml-5">首页</a>

            <a href="#logininfirst" class="black-text ml-5 modal-trigger">个人中心</a>
            <a href="#logininfirst" class="black-text ml-5 modal-trigger">消息</a>
            <a href="#" data-target="slide-out" class="sidenav-trigger">
                <i class="material-icons prefix black-text">menu</i>
            </a>
        </div>
        <h3>欢迎来到&nbsp; Lost'Found !</h3>
        <div>
            <button class="btn blue waves-effect btn-large modal-trigger" data-target="loginin">登&nbsp;录</button>
            <button class="btn teal waves-effect btn-large modal-trigger" data-target="signup">注&nbsp;册</button>
        </div>
    </div>

    <div id="slide-out" class="sidenav" style="width: 33vw;">
        <img src="<%=path%>/img/unloginin-profile.webp" alt="" class="circle responsive-img">
        <h6>
            <a href="index.jsp" class="black-text">首页</a>
        </h6>
        <h6>
            <a href="#logininfirst" class="black-text modal-trigger">个人中心</a>
        </h6>
        <h6>
            <a href="#logininfirst" class="black-text modal-trigger">消息</a>
        </h6>
        <div class="divider"></div>
        <h6>
            <a href="ClientServlet?op=viewannounce" class="black-text">系统公告</a>
        </h6>
        <h6>
            <a href="#logininfirst" class="black-text modal-trigger">我的关注</a>
        </h6>
        <h6>
            <a href="#logininfirst" class="black-text modal-trigger">我的发布</a>
        </h6>
        <div id="privacy">
            <a href="#aloginin" class="modal-trigger">管理员入口</a>
            &nbsp;|&nbsp;
            <a href="ClientServlet?op=viewannounce">使用条款</a>
        </div>
    </div>

    <div id="logininfirst" class="modal">
        <div class="modal-content center-align">
            <h4>请先登录！</h4>
        </div>
    </div>

    <div id="loginin" class="modal form-control">
        <form action="ClientServlet?op=loginin" method="post">
            <div class="input-field">
                <i class="material-icons prefix">email</i>
                <input type="email" id="email" name="useremail" required data-length="30">
                <label for="email">Your email</label>
            </div>
            <div class="input-field">
                <i class="material-icons prefix">panorama_wide_angle</i>
                <input type="password" id="password" name="password" required data-length="16">
                <label for="password">Your password</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn blue white-text waves-effect waves-light btn-large">登&nbsp;&nbsp;录</button>
            </div>
<%--            <input type="hidden" name="op" value="loginin">--%>
        </form>
    </div>

    <div id="signup" class="modal form-control">
        <form action="ClientServlet?op=register" method="post">
            <div class="input-field">
                <i class="material-icons prefix">email</i>
                <input type="email" id="remail" name="useremail" required data-length="30">
                <label for="remail">Your email</label>
            </div>
            <div class="input-field">
                <i class="material-icons prefix">person_outline</i>
                <input type="text" id="name" name="username" required data-length="20">
                <label for="name">Your name</label>
            </div>
            <div class="input-field">
                <i class="material-icons prefix">panorama_wide_angle</i>
                <input type="password" id="rpassword" name="password" required data-length="16">
                <label for="rpassword">Your password</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn teal white-text waves-effect waves-light btn-large">注&nbsp;&nbsp;册</button>
            </div>
<%--            <input type="hidden" name="op" value="register">--%>
        </form>
    </div>

    <div id="aloginin" class="modal form-control">
        <form action="ManagerServlet?op=aloginin" method="post">
            <div class="input-field">
                <i class="material-icons prefix">person_outline</i>
                <input type="text" id="adminid" name="adminid" required data-length="5">
                <label for="adminid">Admin ID</label>
            </div>
            <div class="input-field">
                <i class="material-icons prefix">panorama_wide_angle</i>
                <input type="password" id="apassword" name="apassword" required data-length="16">
                <label for="apassword">Admin Password</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn blue white-text waves-effect waves-light btn-large">登&nbsp;&nbsp;录</button>
            </div>
            <%--            <input type="hidden" name="op" value="loginin">--%>
        </form>
    </div>


    <script>
        $(document).ready(function(){
            $('.sidenav').sidenav();
            $('.modal').modal();
            $("input[type='text'], input[type='password'], input[type='email']").characterCounter();
        });
    </script>
</body>
</html>
