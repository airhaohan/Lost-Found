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
    <h2 class="center">发布公告</h2>
    <form action="ManagerServlet?op=newannounce">
        <h5 class="my-5">标题</h5>
        <div class="input-field">
            <i class="material-icons prefix">title</i>
            <input type="text" name="title" id="title" data-length="20"/>
            <label for="title">标题</label>
        </div>

        <h5 class="my-5">公告内容</h5>
        <div class="input-field">
            <i class="material-icons prefix">short_text</i>
            <textarea id="content" class="materialize-textarea" name="content"></textarea>
            <label for="content">公告内容</label>
        </div>

        <div class="btn-group my-5">
            <button type="submit" class="btn blue white-text waves-effect waves-light btn-large">发&nbsp;布</button>
        </div>
        <input type="text" name="op" value="newannounce" hidden>
    </form>

</div>


<script>
    $(function() {
        $("input[type='text']").characterCounter();
    });
</script>
</body>
</html>