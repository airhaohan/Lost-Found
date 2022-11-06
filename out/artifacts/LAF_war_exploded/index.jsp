<%@ page import="service.ClientService" %>
<%@ page import="service.impl.ClientServiceImpl" %>
<%@ page import="domain.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Tag" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: air浩瀚
  Date: 2022/5/7
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lost' Found</title>
</head>
<body>
    <%
        ClientService service = new ClientServiceImpl();
        String path = request.getContextPath();

        //删除session状态
        session.invalidate();
        session = request.getSession();

        //initialization
        //删除登录状态
        session.setAttribute("loginin", false);
        session.setAttribute("left", "unloginin");
        //初始化标签
        if (session.getAttribute("tags") == null) {
            List<Tag> tags = service.findAllTags();
            session.setAttribute("tags", tags);
        }
        // 初始化贴
        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        session.setAttribute("right", "viewpost");
        request.getRequestDispatcher("/client.jsp").forward(request, response);
    %>
</body>
</html>
