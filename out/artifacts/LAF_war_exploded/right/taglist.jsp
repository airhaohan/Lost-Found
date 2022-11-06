<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/26
  Time: 9:09
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
<div class="col s8 z-depth-2 right" style="padding: 0 30px">
    <h2 class="center">标&nbsp;签</h2>
    <div class="divider"></div>
    <table class="centered highlight">
        <thead>
        <tr>
            <th>ID</th><th>Tag Name</th><th>Op</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.taglist}" var="tag">
            <tr>
                <td>${tag.getTag_id()}</td><td>${tag.getTag_name()}</td>
                <td>
                    <button class="btn black-text white waves-effect waves-red modal-trigger edittag" data-target="edittag" tagid="${tag.getTag_id()}" tagname="${tag.getTag_name()}">
                        编辑
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="fixed-action-btn rfixed-action-btn">
    <a class="btn-floating btn-large red tooltipped modal-trigger" data-tooltip="添加标签" data-position="left" data-target="addtag">
        <i class="large material-icons">add</i>
    </a>
</div>

<div id="edittag" class="modal form-control">
    <form action="ManagerServlet?op=edittag" method="post">
        <div class="input-field">
            <i class="material-icons prefix">center_focus_weak</i>
            <input type="text" id="tagid" name="tagid" readonly>
            <label for="tagid">Tag ID</label>
        </div>
        <div class="input-field">
            <i class="material-icons prefix">short_text</i>
            <input type="text" id="tagname" name="tagname" data-length="10">
            <label for="tagname">Tag Name</label>
        </div>
        <div class="input-field center-align">
            <button type="submit" class="btn blue white-text waves-effect waves-light btn-large mx-3">保&nbsp;&nbsp;存</button>
            <button class="btn red white-text waves-effect waves-light btn-large modal-trigger" data-target="deletetag">删&nbsp;除</button>
        </div>
    </form>
</div>

<div id="addtag" class="modal form-control">
    <form action="ManagerServlet?op=addtag" method="post">
        <div class="input-field">
            <i class="material-icons prefix">short_text</i>
            <input type="text" id="newtagname" name="tagname" data-length="10">
            <label for="newtagname">Tag Name</label>
        </div>
        <div class="input-field center-align">
            <button type="submit" class="btn teal white-text waves-effect waves-light btn-large mx-3">添&nbsp;&nbsp;加</button>
        </div>
    </form>
</div>

<div id="deletetag" class="modal form-control">
    <div class="editmodal">
        <form action="ManagerServlet?op=deletetag" method="post">
            <h3 class="center my-5">是否删除？</h3>
            <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                <button type="submit" class="btn red white-text waves-effect waves-light btn-large">确&nbsp;&nbsp;定</button>
            </div>
            <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                <button type="button" class="modal-close btn teal white-text waves-effect waves-light btn-large">取&nbsp;&nbsp;消</button>
            </div>
            <input type="text" id="deletetagid" name="tagid" hidden>
        </form>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('.modal').modal();
        $('.tooltipped').tooltip();
        $('.fixed-action-btn').floatingActionButton();
        $("input[type='text']").characterCounter();

        let btns = document.querySelectorAll(".edittag");
        console.log(btns);
        for (let i = 0; i < btns.length; ++i) {
            btns[i].onclick = function () {
                $("#tagid").prop("value", btns[i].getAttribute("tagid"));
                $("#deletetagid").prop("value", btns[i].getAttribute("tagid"));
                $("#tagname").prop("value", btns[i].getAttribute("tagname"));
                console.log(btns[i]);
                console.log("clocked!")
            };
        }
    });
</script>
</body>
</html>
