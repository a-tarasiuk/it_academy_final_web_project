<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="static/css/font.css">
    <link rel="stylesheet" href="static/css/style.css">
    <link rel="stylesheet" href="static/css/font-awesome.min.css">
    <title>Main page</title>
</head>
<body>
<div id="container">
    <%@include file="header.jsp"%>

    <div id="search">
        <div id="block-for-search__for-input">
            <input class="input-style" type="search">
        </div>
        <div id="block-for-search__for-button">
            <a class="menu-item search-button" href="#"><span class="icon icon-search"></span></a>
        </div>
    </div>

    <div id="middle">
        <h4>Hello, user :)</h4>
        Server Version: <%= application.getServerInfo() %><br>
        Servlet Version: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %>
    </div>

    <%@include file="footer.jsp"%>
</div>
</body>
</html>