<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/26
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>taglist</title>--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
<%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
<%--    <!-- Compiled and minified JavaScript -->--%>
<%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px" id="userlist">
    <a href="" class="backpage black-text"><i class="material-icons right">arrow_back</i></a>
    <h2 class="center">用&nbsp;户</h2>
    <div class="divider"></div>

    <%--filter--%>
    <div class="my-5">
        <form action="ManagerServlet?op=searchusername">
            <div class="input-field">
                <i class="material-icons prefix">search</i>
                <input type="text" id="searchword" name="searchword" value="${sessionScope.searchusername}" maxlength="20">
                <label for="searchword">搜索用户名</label>
                <input type="text" value="searchusername" name="op" hidden>
            </div>
            <div class="input-field d-none">
                <button type="submit" class="btn blue white-text waves-effect waves-light btn-large center">搜&nbsp;&nbsp;索</button>
            </div>
        </form>
    </div>

    <c:forEach items="${sessionScope.inituser}" var="user" varStatus="vs">
    <div class="card useritem" id="${vs.index}item">
        <div class="card-content">
                <span class="card-title activator grey-text text-darken-4">
                    <i class="material-icons right">more_vert</i>
                    <div class="entity">
                        <img src="${user.getProfile()}" alt="" class="circle responsive-img">
                        <div class="entity-title">
                            <h5>${user.getUsername()}</h5>
                            <p>${user.getEmail()}</p>
                        </div>
                    </div>
                    <button class="btn white black-text waves-effect waves-red modal-trigger deletebtn" data-target="deleteuser" id="${vs.index}btn" useremail="${user.getEmail()}">删&nbsp;除</button>
                </span>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4"><i class="material-icons right">close</i></span>
            <p>${user.getSelfintro()}</p>
        </div>
    </div>
    </c:forEach>

    <button class="my-5 teal white-text waves-effect waves-light btn" id="loadMoreBtn">加载更多。。。</button>

</div>

<div id="deleteuser" class="modal form-control">
    <div class="editmodal">
        <form action="ManagerServlet?op=deleteuser" method="post">
            <h3 class="center my-5">是否删除？</h3>
            <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                <button type="submit" class="btn red white-text waves-effect waves-light btn-large">确&nbsp;&nbsp;定</button>
            </div>
            <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                <button type="button" class="modal-close btn teal white-text waves-effect waves-light btn-large">取&nbsp;&nbsp;消</button>
            </div>
            <input type="text" id="deleteuseremail" name="deleteuseremail" hidden>
        </form>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('.modal').modal();
        var colorList = ["#d1c4e9", "#bbdefb", "#b2ebf2", "#b2dfdb", "#b3e5fc", "#c8e6c9", "#c5cae9"]
        var userlist = document.querySelectorAll(".useritem");
        for (let i = 0; i < userlist.length; ++i) {
            userlist[i].style.backgroundColor = colorList[Math.floor(Math.random()*(colorList.length))];
        }

        // delete user logic
        var initbtns = document.querySelectorAll(".deletebtn");
        for (let i = 0; i < initbtns.length; ++i) {
            initbtns[i].onclick = function () {
                $("#deleteuseremail").prop("value", initbtns[i].getAttribute("useremail"));
            };
        }

        // load posts logic
        $("#loadMoreBtn").click(function () {
            getTenMoreUsers();
        });
        let userBeginIdx = 10;
        function getTenMoreUsers() {
            let url = "ManagerServlet?op=gettenmoreusers&userBeginIdx=" + userBeginIdx;
            userBeginIdx += 10;
            console.log("click");
            $.get(url, function (data) {
                addNewUser($.parseJSON(data));
            }, "json");
        }
        function addNewUser(data) {
            let len = data.length;
            for (let i = 0; i < len; ++i) {
                let user = data[i];

                let userNode = document.createElement("div");
                userNode.innerHTML = '<div class="card useritem" id="' + (i + userBeginIdx) + 'item"> \
                                        <div class="card-content"> \
                                                <span class="card-title activator grey-text text-darken-4"> \
                                                    <i class="material-icons right">more_vert</i> \
                                                    <div class="entity"> \
                                                        <img src="' + user.profile + '" alt="" class="circle responsive-img"> \
                                                        <div class="entity-title"> \
                                                            <h5>' + user.username + '</h5> \
                                                            <p>' + user.email + '</p> \
                                                        </div> \
                                                    </div> \
                                                    <button class="btn white black-text waves-effect waves-red modal-trigger deletebtn" data-target="deleteuser" id="' + (i + userBeginIdx) + 'btn" useremail="' + user.email + '" >删&nbsp;除</button> \
                                                </span> \
                                        </div> \
                                        <div class="card-reveal"> \
                                            <span class="card-title grey-text text-darken-4"><i class="material-icons right">close</i></span> \
                                            <p>' + user.selfintro + '</p> \
                                        </div> \
                                    </div>';
                document.querySelector("#userlist").insertBefore(userNode, document.querySelector("#loadMoreBtn"));
                $("#" + (i + userBeginIdx) + "item").css("backgroundColor", colorList[Math.floor(Math.random()*(colorList.length))]);
            }
            var initbtns = document.querySelectorAll(".deletebtn");
            for (let i = 0; i < initbtns.length; ++i) {
                initbtns[i].onclick = function () {
                    $("#deleteuseremail").prop("value", initbtns[i].getAttribute("useremail"));
                };
            }
            if (len < 10) {
                let buttomline = document.createElement("div");
                buttomline.innerHTML = '<div class="divider"></div><h5 class="center my-5">已经到底啦~</h5>'
                document.querySelector("#userlist").insertBefore(buttomline, document.querySelector("#loadMoreBtn"));
                $("#loadMoreBtn").fadeOut();
            }
            $('.modal').modal();
        }
    });
</script>
</body>
</html>
