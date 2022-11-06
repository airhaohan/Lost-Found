<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/12
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <title>viewpost</title>--%>
    <%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
    <%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
    <%--    <!-- Compiled and minified JavaScript -->--%>
    <%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
    <%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px">
    <h2 class="center">系统公告</h2>
    <div class="divider"></div>
    <div class="ann-list">
        <c:forEach items="${sessionScope.initanns}" var="ann">
            <ul class="collapsible popout my-5 py-3">
                <li class="card">
                    <div class="collapsible-header card-title  grey-text text-darken-4 annlist-item">${ann.getTitle()}</div>
                    <div class="collapsible-body grey-text text-darken-4">
                        <p>${ann.getContent()}</p>
                        <a class="btn red white-text waves-effect waves-light" href="ManagerServlet?op=editann&annid=${ann.getAnn_id()}">编&nbsp;辑</a>
                    </div>
                    <p class="ann-time right"><fmt:formatDate value="${ann.getDate()}" pattern="yyyy-MM-dd HH:mm" /></p>
                </li>
            </ul>
        </c:forEach>

        <button class="my-5 teal white-text waves-effect waves-light btn" id="loadMoreBtn">加载更多。。。</button>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('.collapsible').collapsible();
        var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"]
        var annlist = document.querySelectorAll(".annlist-item");
        for (let i = 0; i != annlist.length; ++i) {
            annlist[i].style.backgroundColor = colorList[Math.floor(Math.random()*(colorList.length))];
        }

        // pagination logic
        $("#loadMoreBtn").click(function () {
            getTenMoreAnns();
        });

        let annBeginIdx = 10;
        let annListItemLdx = 10;
        function getTenMoreAnns() {
            let url = "ManagerServlet?op=gettenmoreanns&annBeginIdx=" + annBeginIdx;
            annBeginIdx += 10;
            console.log("click");
            $.get(url, function (data) {
                addNewAnn($.parseJSON(data));
            }, "json");
        }
        function addNewAnn(data) {
            let len = data.length;
            for (let i = 0; i < len; ++i) {
                let annNode = document.createElement("div");
                annNode.innerHTML = '<ul class="collapsible popout my-5 py-3" >\
                    <li class="card">\
                        <div class="collapsible-header card-title  grey-text text-darken-4 annlist-item" id="' + annListItemLdx + 'annlist-item" >' + data[i].title + '</div>\
                        <div class="collapsible-body grey-text text-darken-4">\
                            <p>' + data[i].content + '</p>\
                            <a class="btn red white-text waves-effect waves-light" href="ManagerServlet?op=editann&annid=' + data[i].ann_id + '">编&nbsp;辑</a>\
                        </div>\
                        <p class="ann-time right">' + data[i].date + '</p>\
                    </li>\
                </ul>';
                document.querySelector(".ann-list").insertBefore(annNode, document.querySelector("#loadMoreBtn"));
                $("#" + annListItemLdx + "annlist-item").css("backgroundColor", colorList[Math.floor(Math.random()*(colorList.length))]);
                annListItemLdx++;
            }
            if (len < 10) {
                let buttomline = document.createElement("div");
                buttomline.innerHTML = '<div class="divider"></div><h5 class="center my-5">已经到底啦~</h5>'
                document.querySelector(".ann-list").insertBefore(buttomline, document.querySelector("#loadMoreBtn"));
                $("#loadMoreBtn").fadeOut();
            }
            $('.collapsible').collapsible();
        }
    });
</script>
</body>
</html>
