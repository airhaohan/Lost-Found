<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/20
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% String path = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <title>lostpost</title>--%>
    <%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">--%>
    <%--    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">--%>
    <%--    <link rel="stylesheet" href="<%=path%>/css/viewpost.css">--%>
    <%--    <!-- Compiled and minified JavaScript -->--%>
    <%--    <script src="https://cdn.staticfile.org/jquery/3.6.0/jquery.min.js"></script>--%>
    <%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>--%>
</head>
<body class="row">
<div class="col s8 z-depth-2 right" style="padding: 0 30px" id="lostpost">
    <a href="ClientServlet?op=viewpost" class="rbackpage black-text"><i class="material-icons right">arrow_back</i></a>
    <h2 class="center">寻物板块</h2>
    <div class="divider"></div>

    <!-- filter -->
    <a class="waves-effect waves-blue modal-trigger card my-5" href="#modal-tags" id="tags-select">筛&nbsp;&nbsp;选&nbsp;&nbsp;标&nbsp;&nbsp;签</a>
    <div class="my-5">
        <form action="ClientServlet?op=selsearch" id="search-form">
            <div class="input-field">
                <i class="material-icons prefix">search</i>
                <input type="text" id="searchword" name="searchword" value="${sessionScope.searchword}" maxlength="10">
                <label for="searchword">搜索内容</label>
                <input type="text" value="selsearch" name="op" hidden>
            </div>
            <div class="input-field d-none">
                <button type="submit" class="btn blue white-text waves-effect waves-light btn-large center">搜&nbsp;&nbsp;索</button>
            </div>
        </form>
    </div>

    <div id="modal-tags" class="modal bottom-sheet">
        <form action="ClientServlet?op=seltags">
            <div class="modal-content">
                <h4>标签筛选</h4>
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
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn blue white-text waves-effect waves-light btn-large center">保&nbsp;&nbsp;存</button>
                <button class="btn teal white-text waves-effect waves-light btn-large center" id="cleanTagsFilter">清&nbsp;&nbsp;空</button>
            </div>
            <input type="text" value="seltags" name="op" hidden>
        </form>
    </div>

    <!-- post -->
    <c:forEach items="${sessionScope.initposts}" var="post">
    <div class="card horizontal postlist-item z-depth-2 my-5">
        <div class="card-image waves-effect waves-block waves-light">
            <a href="ClientServlet?op=postdetail&postid=${post.getPost_id()}">
                <img class="activator" src="${post.getPost_pictures().get(0)}">
            </a>
        </div>
        <div class="card-content">
                    <span class="card-title activator grey-text text-darken-4">
                            ${post.getPost_title()}<i class="material-icons right">more_vert</i>
                    </span>
            <div class="chip">
                <c:if test="${post.getPost_type()}">寻物</c:if>
                <c:if test="${!post.getPost_type()}">招领</c:if>
            </div>
            <c:forEach items="${post.getTags()}" var="posttag">
                <div class="chip">${posttag}</div>
            </c:forEach>
            <p class="post-content"></p>
                <%--                    <p class="post-content">${post.getDetail()}</p>--%>
            <p class="post-time"><fmt:formatDate value="${post.getPost_date()}" pattern="yyyy-MM-dd HH:mm" /></p>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">${post.getPost_title()}<i class="material-icons right">close</i></span>
            <p style="margin: 20px 0 0 0;line-height:2rem">${post.getDetail()}</p>
        </div>
    </div>
    </c:forEach>

    <button class="my-5 teal white-text waves-effect waves-light btn" id="loadMoreBtn">加载更多。。。</button>
    <br/>
    <br/>
    <br/>
    <br/>

    <script>
        $(document).ready(function(){
            $('.modal').modal();
            $('.carousel').carousel({
                duration: 100,
                fullWidth: true,
                indicators: true
            });

            // load posts logic
            $("#loadMoreBtn").click(function () {
                getTenMorePosts();
            });
            let postBeginIdx = 10;
            function getTenMorePosts() {
                let url = "ClientServlet?op=gettentypemoreposts&postBeginIdx=" + postBeginIdx;
                postBeginIdx += 10;
                console.log("click");
                $.get(url, function (data) {
                    addNewPost($.parseJSON(data));
                }, "json");
            }
            function addNewPost(data) {
                let len = data.length;
                for (let i = 0; i < len; ++i) {
                    let post = data[i];

                    let tagsstr = '';
                    for (let j = 0; j != post.tags.length; ++j) {
                        tagsstr += '<div class="chip">' + post.tags[j] + '</div>';
                    }

                    let typestr = post.post_type ? "寻物" : "招领";

                    let postNode = document.createElement("div");
                    postNode.innerHTML = '<div class="card horizontal postlist-item z-depth-2 my-5"> \
                                            <div class="card-image waves-effect waves-block waves-light"> \
                                                <a href="ClientServlet?op=postdetail&postid=' + post.post_id + '"><img class="activator" src="' + post.post_pictures[0] + '"></a> \
                                            </div> \
                                            <div class="card-content"> \
                                                <span class="card-title activator grey-text text-darken-4">'
                        + post.post_title + '<i class="material-icons right">more_vert</i>\
                                                </span> \
                                                <div class="chip">' + typestr + '</div>'
                        + tagsstr +
                        '<p class="post-content"></p> \
                        <p class="post-time"></p> \
                    </div> \
                    <div class="card-reveal"> \
                        <span class="card-title grey-text text-darken-4">' + post.post_title + '<i class="material-icons right">close</i></span> \
                                                <p style="margin: 20px 0 0 0;line-height:2rem">' + post.detail + '</p> \
                                            </div> \
                                        </div>';
                    document.querySelector("#lostpost").insertBefore(postNode, document.querySelector("#loadMoreBtn"));
                }
                if (len < 10) {
                    let buttomline = document.createElement("div");
                    buttomline.innerHTML = '<div class="divider"></div><h5 class="center my-5">已经到底啦~</h5>'
                    document.querySelector("#lostpost").insertBefore(buttomline, document.querySelector("#loadMoreBtn"));
                    $("#loadMoreBtn").fadeOut();
                }
                $('.modal').modal();
            }

            // init tags logic
            var ses = document.querySelectorAll(".se i");
            var uns = document.querySelectorAll(".un");
            var che = document.querySelectorAll("input[type='checkbox']");

            var tagslen = ses.length;
            for (let i = 0; i !== tagslen; ++i) {
                uns[i].onclick = function() {
                    ses[i].parentNode.classList.remove("d-none");
                    uns[i].classList.add("d-none");
                    che[i].checked = true;
                }
                ses[i].onclick = function() {
                    ses[i].parentNode.classList.add("d-none");
                    uns[i].classList.remove("d-none");
                    che[i].checked = false;
                }
            }
            $("#cleanTagsFilter").click(()=>{
                for (let i = 0; i !== tagslen; ++i) {
                    ses[i].parentNode.classList.add("d-none");
                    uns[i].classList.remove("d-none");
                    che[i].checked = false;
                }
            });
            function getSelectedTags() {
                let url = "ClientServlet?op=getseltags";
                $.get(url, function (data) {
                    checkSelectedTags($.parseJSON(data));
                }, "json");
            }
            function checkSelectedTags(data) {
                for (let i = 0; i < data.length; ++i) {
                    $("input[value='" + data[i] + "']").prop("checked", "true");
                    $("#se" + data[i]).removeClass("d-none");
                    $("#un" + data[i]).addClass("d-none");
                }
            }
            getSelectedTags();
        });
    </script>
</body>
</html>
