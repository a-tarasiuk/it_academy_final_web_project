<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale_page}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <title><fmt:message key="html.head.title"/></title>
</head>
<body>
<div id="container">
    <jsp:include page="common/header.jsp"/>

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

    <%@include file="common/footer.jsp"%>
</div>
</body>
</html>