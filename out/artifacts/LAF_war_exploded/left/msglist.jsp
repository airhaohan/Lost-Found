<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/18
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>msglist</title>--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
<%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
<%--    <!-- Compiled and minified JavaScript -->--%>
<%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>

</head>
<body class="row">
<div class="col s4 z-depth-2 left">
    <a href="ClientServlet?op=loginin" class="backpage black-text"><i class="material-icons">arrow_back</i></a>
    <h3 class="center">消&nbsp;&nbsp;息</h3>
    <div class="leftdivider"></div>
    <div class="msglist">
        <c:forEach items="${sessionScope.msglist}" var="pmu">
            <ul class="collapsible popout my-5 py-3">
                <li class="card">
                    <div class="collapsible-header card-title  grey-text text-darken-4 msglist-item">
                        <img src="${pmu.getValue().getProfile()}" alt="" class="circle responsive-img sender-profile">
                        <h5>${pmu.getValue().getUsername()}</h5>
                    </div>
                    <div class="collapsible-body grey-text text-darken-4">
                        ${pmu.getKey().getContent()}
                        <a href="ClientServlet?op=deletemsg&delmsgid=${pmu.getKey().getMsg_id()}"><i class="material-icons red-text delete-icon">delete_forever</i></a>
                    </div>
                    <p class="msg-time right"><fmt:formatDate value="${pmu.getKey().getDate()}" pattern="yyyy-MM-dd HH:mm" /></p>
                </li>
            </ul>
        </c:forEach>
        <div class="leftdivider"></div><h5 class="center my-5">已经到底啦~</h5>
    </div>
</div>


<script>
    $(function() {
        $('.modal').modal();
        $('.fixed-action-btn').floatingActionButton();
        $('.tooltipped').tooltip();
        $('.collapsible').collapsible();
        var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"]
        var msglist = document.querySelectorAll(".msglist-item");
        for (let i = 0; i != msglist.length; ++i) {
            msglist[i].style.backgroundColor = colorList[Math.floor(Math.random()*(colorList.length))]
        }
    });
</script>
</body>
</html>
