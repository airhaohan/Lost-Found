<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/13
  Time: 15:37
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
    <a href="ClientServlet?op=loginin" class="lbackpage black-text"><i class="material-icons">arrow_back</i></a>
    <img src="${sessionScope.user.getProfile()}" alt="" class="circle responsive-img selfprofile">
    <h3 class="usernameh2">${sessionScope.user.getUsername()}</h3>
    <div class="useremail">
        <div class="my-5" style="margin: 30px 0 40px">
            <h5>邮&nbsp;箱：${sessionScope.user.getEmail()}</h5>
            <div class="divider"></div>
        </div>
        <div class="my-5">
            <h5>个&nbsp;人&nbsp;简&nbsp;介</h5>
            <div class="divider"></div>
            <div>
                ${sessionScope.user.getSelfintro()}
            </div>
        </div>
    </div>
    <div class="fixed-action-btn">
        <a class="btn-floating btn-large red">
            <i class="large material-icons">mode_edit</i>
        </a>
        <ul>
            <li><a class="btn-floating green tooltipped modal-trigger" data-position="left" data-tooltip="换个有趣的头像" data-target="editprofile"><i class="material-icons">person_outline</i></a></li>
            <li><a class="btn-floating yellow darken-1 tooltipped modal-trigger" data-position="left" data-tooltip="换个有趣的名字" data-target="editusername"><i class="material-icons">format_quote</i></a></li>
            <li><a class="btn-floating blue tooltipped modal-trigger" data-position="left" data-tooltip="写个有趣的自我简介吧~" data-target="editselfintro"><i class="material-icons">short_text</i></a></li>
            <li><a class="btn-floating orange tooltipped modal-trigger" data-position="left" data-tooltip="偷偷修改密码" data-target="editpassword"><i class="material-icons">panorama_wide_angle</i></a></li>
        </ul>
    </div>
</div>

<div id="editprofile" class="modal form-control">
    <div class="editmodal">
        <h5 class="my-5">当前头像</h5>
        <img src="${sessionScope.user.getProfile()}" alt="" class="circle responsive-img" style="width: 25%;">
        <form action="UpdateFileServlet" method="post" enctype="multipart/form-data">
            <div class="input-field">
                <input type="file" name="profile">
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn teal white-text waves-effect waves-light btn-large">更&nbsp;&nbsp;改</button>
            </div>
        </form>
    </div>
</div>

<div id="editusername" class="modal form-control">
    <div class="editmodal">
        <form action="ClientServlet?op=editusername" method="post">
            <div class="input-field">
                <i class="material-icons prefix">account_circle</i>
                <input id="username" type="text" name="username" value="${sessionScope.user.getUsername()}" data-length="20">
                <label for="username">新的用户名</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn teal white-text waves-effect waves-light btn-large">更&nbsp;&nbsp;改</button>
            </div>
        </form>
    </div>
</div>

<div id="editselfintro" class="modal form-control">
    <div class="editmodal">
        <form action="ClientServlet?op=editselfintro" method="post">
            <div class="input-field" style="width:550px;">
                <textarea id="selfintro" class="materialize-textarea" name="selfintro" data-length="200">${sessionScope.user.getSelfintro()}</textarea>
                <label for="selfintro">介绍你自己</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn teal white-text waves-effect waves-light btn-large">更&nbsp;&nbsp;改</button>
            </div>
        </form>
    </div>
</div>

<div id="editpassword" class="modal form-control">
    <div class="editmodal">
        <form action="ClientServlet?op=editpassword" method="post">
            <div class="input-field">
                <i class="material-icons prefix">panorama_wide_angle</i>
                <input type="password" id="password" name="password" data-length="16">
                <label for="password">新的密码</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn teal white-text waves-effect waves-light btn-large">更&nbsp;&nbsp;改</button>
            </div>
        </form>
    </div>
</div>

<script>
    $(function() {
        $('.modal').modal();
        $("input[type='text'], input[type='password'], textarea").characterCounter();
        $('.fixed-action-btn').floatingActionButton();
        $('.tooltipped').tooltip();
        // var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"]
        var colorList = ["#ede7f6", "#e3f2fd", "#e0f7fa", "#e0f2f1", "#e1f5fe", "#e8f5e9", "#e8eaf6"]
    });
</script>
</body>
</html>