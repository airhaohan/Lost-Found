<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/19
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <a href="<%= path %>/index.jsp" class="lbackpage black-text tooltipped" data-tooltip="退出登录" data-position="right" ><i class="material-icons">arrow_back</i></a>
    <h3 class="center">管&nbsp;&nbsp;理</h3>
    <div class="leftdivider"></div>
    <div class="alist">
        <div class="card alist-item">
            <a href="ManagerServlet?op=viewpost" class="black-text"><div class="card-content">
                <h4>管&nbsp;理&nbsp;帖&nbsp;子</h4>
            </div></a>
        </div>

        <div class="card alist-item">
            <a href="ManagerServlet?op=userlist" class="black-text"><div class="card-content">
                <h4>管&nbsp;理&nbsp;用&nbsp;户</h4>
            </div></a>
        </div>

        <div class="card alist-item">
            <a href="ManagerServlet?op=viewannounce" class="black-text"><div class="card-content">
                <h4>管&nbsp;理&nbsp;公&nbsp;告</h4>
            </div></a>
        </div>

        <div class="card alist-item">
            <a href="ManagerServlet?op=taglist" class="black-text"><div class="card-content">
            <h4>管&nbsp;理&nbsp;标&nbsp;签</h4>
            </div></a>
        </div>

        <div class="card alist-item">
            <a href="ManagerServlet?op=adminlist" class="black-text"><div class="card-content">
                <h4>管&nbsp;理&nbsp;人&nbsp;员</h4>
            </div></a>
        </div>

    </div>

    <div class="fixed-action-btn">
        <a class="btn-floating btn-large teal">
            <i class="large material-icons">menu</i>
        </a>
        <ul>
            <li>
                <button class="btn-floating blue tooltipped modal-trigger" data-position="left" data-tooltip="添加新管理员" data-target="addadmin">
                    <i class="material-icons">person_add</i>
                </button>
            </li>
            <li>
                <button class="btn-floating yellow tooltipped modal-trigger" data-position="left" data-tooltip="修改密码" data-target="editpassword">
                    <i class="material-icons">panorama_wide_angle</i>
                </button>
            </li>
            <li>
                <a class="btn-floating red tooltipped" data-tooltip="发布新公告" data-position="left" href="ManagerServlet?op=newann">
                    <i class="material-icons">mode_edit</i>
                </a>
            </li>
        </ul>
    </div>

</div>

<!-- add a new administrtor-->
<div id="addadmin" class="modal form-control">
    <form action="ManagerServlet?op=newadmin" method="post">
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
            <button type="submit" class="btn blue white-text waves-effect waves-light btn-large">添&nbsp;&nbsp;加</button>
        </div>
    </form>
</div>

<!--edit password-->
<div id="editpassword" class="modal form-control">
    <div class="editmodal">
        <form action="ManagerServlet?op=editpassword" method="post">
            <div class="input-field">
                <i class="material-icons prefix">panorama_wide_angle</i>
                <input type="password" id="password" name="password">
                <label for="password">新的密码</label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn teal white-text waves-effect waves-light btn-large">更&nbsp;&nbsp;改</button>
            </div>
        </form>
    </div>
</div>


<script>
    $(document).ready(function(){
        $("h3").hide();
        $("h3").fadeIn();
        $('.tooltipped').tooltip();
        $('.modal').modal();
        $("input[type='text'], input[type='password']").characterCounter();
        var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"]
        var alist = document.querySelectorAll(".alist-item");
        for (let i = 0; i != alist.length; ++i) {
            alist[i].style.backgroundColor = colorList[Math.floor(Math.random()*(colorList.length))]
        }
        $('.fixed-action-btn').floatingActionButton();
    });
</script>
</body>
</html>

