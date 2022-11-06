<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="domain.Announce" %>
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
<%--    <title>newpost</title>--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
<%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
<%--    <!-- Compiled and minified JavaScript -->--%>
<%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px">
    <a href="ManagerServlet?op=${sessionScope.rbackpage}" class="backpage black-text"><i class="material-icons right">arrow_back</i></a>
    <h2 class="center">编辑公告</h2>
    <form action="ManagerServlet?op=editannounce">
        <h5 class="my-5">标题</h5>
        <div class="input-field">
            <i class="material-icons prefix">title</i>
            <input type="text" name="title" id="title" data-length="20" value="${sessionScope.nowannounce.getTitle()}"/>
            <label for="title">标题</label>
        </div>

        <h5 class="my-5">公告内容</h5>
        <div class="input-field">
            <i class="material-icons prefix">short_text</i>
            <textarea id="content" class="materialize-textarea" name="content">${sessionScope.nowannounce.getContent()}</textarea>
            <label for="content">公告内容</label>
        </div>

        <h5 class="my-5">日期</h5>
        <div class="input-field">
            <i class="material-icons prefix">perm_contact_calendar</i>
            <input type="text" class="datepicker" id="anndate" name="date">
            <label for="anndate">选择日期</label>
        </div>

        <div class="btn-group my-5">
            <button type="submit" class="btn blue white-text waves-effect waves-light btn-large">保&nbsp;存</button>
            <button class="btn red white-text waves-effect waves-light btn-large modal-trigger" data-target="deleteann">删&nbsp;除</button>
        </div>
        <input type="text" name="op" value="editannounce" hidden>
    </form>

    <div id="deleteann" class="modal form-control">
        <div class="editmodal">
            <form action="ManagerServlet?op=deleteannounce" method="post">
                <h3 class="center my-5">是否删除？</h3>
                <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                    <button type="submit" class="btn red white-text waves-effect waves-light btn-large">确&nbsp;&nbsp;定</button>
                </div>
                <div class="input-field center-align" style="display: inline-block; padding: 20px 30px">
                    <button type="button" class="modal-close btn teal white-text waves-effect waves-light btn-large">取&nbsp;&nbsp;消</button>
                </div>
            </form>
        </div>
    </div>

</div>


<script>
    $(function() {
        <%
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Announce nowannounce = (Announce) session.getAttribute("nowannounce");
            String anndate = formatter.format(nowannounce.getDate());
        %>
        $('.datepicker').datepicker({
            defaultDate: new Date("<%= anndate %>"),
            setDefaultDate: true,
            format: "yyyy-mm-dd"
        });
        $('.modal').modal();
        $("input[type='text']").characterCounter();
    });
</script>
</body>
</html>