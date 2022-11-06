<%@ page import="domain.User" %><%--
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
    <img src="${sessionScope.nowentity.getProfile()}" alt="" class="circle responsive-img selfprofile">
    <h3 class="usernameh2">${sessionScope.nowentity.getUsername()}</h3>
    <div class="useremail">
        <div class="my-5">
            <h5>个&nbsp;人&nbsp;简&nbsp;介</h5>
            <div class="divider"></div>
            <div>
                ${sessionScope.nowentity.getSelfintro()}
            </div>
        </div>
    </div>
    <% if (!((User) session.getAttribute("user")).getEmail().equals("visitor@lost'found")) { %>
    <div class="fixed-action-btn">
        <a class="btn-floating btn-large green tooltipped modal-trigger" data-position="left" data-tooltip="给ta发送信息~" data-target="sendmsg">
            <i class="large material-icons">chat_bubble_outline</i>
        </a>
    </div>
    <% } %>
</div>

<div id="sendmsg" class="modal form-control">
    <div class="editmodal">
        <form action="ClientServlet?op=sendmsg" method="post">
            <div class="input-field">
                <i class="material-icons prefix">subject</i>
                <textarea id="msgcontent" class="materialize-textarea" name="msgcontent" data-length="50"></textarea>
                <label for="msgcontent"></label>
            </div>
            <div class="input-field center-align">
                <button type="submit" class="btn green white-text waves-effect waves-light btn-large">发&nbsp;&nbsp;送</button>
            </div>
        </form>
    </div>
</div>

<script>
    $(function() {
        $('.modal').modal();
        $('.fixed-action-btn').floatingActionButton();
        $('.tooltipped').tooltip();
        $("input[type='text'], textarea").characterCounter();
        // var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"]
        var colorList = ["#ede7f6", "#e3f2fd", "#e0f7fa", "#e0f2f1", "#e1f5fe", "#e8f5e9", "#e8eaf6"]
    });
</script>
</body>
</html>