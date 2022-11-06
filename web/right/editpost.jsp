<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/18
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <a href="ClientServlet?op=${sessionScope.rbackpage}" class="rbackpage black-text"><i class="material-icons right">arrow_back</i></a>
    <h2 class="center">编辑贴子</h2>
    <h5 class="my-5">类型</h5>
    <div class="row my-5">
        <div class="col s1"></div>
        <div class="card col s4 mx-5 checkedcard" id="found-img">
            <div class="card-image">
                <img src="img/found-section.jpg">
                <span class="card-title black-text">招领贴</span>
            </div>
        </div>
        <div class="col s2"></div>
        <div class="card col s4 mx-5" id="lost-img">
            <div class="card-image">
                <img src="img/lost-section.jpg">
                <span class="card-title black-text">寻物贴</span>
            </div>
        </div>
        <div class="col s1"></div>
    </div>
    <form action="ClientServlet?op=savepost">
        <div id="typeselect">
            <p>
                <label for="found">
                    <input type="radio" name="type" id="found" value="found" checked>
                    <span>found</span>
                </label>
            </p>
            <p>
                <label for="lost">
                    <input type="radio" name="type" id="lost" value="lost">
                    <span>lost</span>
                </label>
            </p>
        </div>
        <h5 class="my-2">标题</h5>
        <div class="input-field">
            <i class="material-icons prefix">title</i>
            <input type="text" name="title" data-length="20" id="title" value="${sessionScope.nowpost.getPost_title()}" />
            <label for="title">标题</label>
        </div>
        <h5 class="my-2">描述</h5>
        <div class="input-field">
            <i class="material-icons prefix">short_text</i>
            <textarea id="content" class="materialize-textarea" name="content" data-length="400">${sessionScope.nowpost.getDetail()}</textarea>
            <label for="content">简单描述一下~</label>
        </div>
        <h5 class="my-2">标签筛选</h5>
        <div class="input-field">
            <div id="tags-filter">
                <div id="selected-tags" class="my-5">
                    <c:forEach items="${sessionScope.tags}" var="tag">
                        <div class="chip d-none se" id="se${tag.getTag_id()}">${tag.getTag_name()}<i class="material-icons">close</i></div>
                        <input type="checkbox" name="tags" value="${tag.getTag_id()}" hidden>
                    </c:forEach>
                </div>
                <div class="divider"></div>
                <div id="unselected-tags" class="my-5">
                    <c:forEach items="${sessionScope.tags}" var="tag">
                        <div class="chip un" id="un${tag.getTag_id()}">${tag.getTag_name()}</div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="input-field center-align">
            <button type="submit" class="btn blue white-text waves-effect waves-light btn-large">保&nbsp;&nbsp;存</button>
        </div>
        <input type="text" name="op" value="savepost" hidden>
    </form>
</div>


<script>
    $(function() {
        $('.modal').modal();

        // type logic
        function foundselected() {
            $("#found").prop("checked",true);
            $("#found-img").addClass("checkedcard");
            $("#lost-img").removeClass("checkedcard");
        }
        $("#found-img").click(()=>{
            foundselected();
        });
        function lostselected() {
            $("#lost").prop("checked",true);
            $("#lost-img").addClass("checkedcard");
            $("#found-img").removeClass("checkedcard");
        }
        $("#lost-img").click(()=>{
            lostselected();
        });
        $("input[type='text'], textarea").characterCounter();

        // init tags logic
        var ses = document.querySelectorAll(".se i");
        var uns = document.querySelectorAll(".un");
        var che = document.querySelectorAll("input[type='checkbox']");

        var tagslen = ses.length;
        var tagselectlen = 0;
        for (let i = 0; i != tagslen; ++i) {
            uns[i].onclick = function() {
                if (tagselectlen < 3) {
                    ses[i].parentNode.classList.remove("d-none");
                    uns[i].classList.add("d-none");
                    che[i].checked = true;
                    ++tagselectlen;
                }
                else {
                    alert("最多选择三个标签！")
                }
            }
            ses[i].onclick = function() {
                ses[i].parentNode.classList.add("d-none");
                uns[i].classList.remove("d-none");
                che[i].checked = false;
                tagselectlen--;
            }
        }

        // select tags logic
        function getSelectedTags() {
            let url = "ClientServlet?op=selectedtags";
            $.get(url, function (data) {
                selectTags($.parseJSON(data));
            }, "json");
        }
        function selectTags(data) {
            tagselectlen += data.length;
            for (let i = 0; i != data.length; ++i) {
                $("#se" + data[i].tag_id).removeClass("d-none");
                $("#un" + data[i].tag_id).addClass("d-none");
                $("input[value='" + data[i].tag_id + "']").prop("checked", true);
            }
        }
        getSelectedTags();

        // select type logic
        if (${sessionScope.nowpost.getPost_type()}) {
            lostselected();
        } else {
            foundselected();
        }

    });
</script>
</body>
</html>
