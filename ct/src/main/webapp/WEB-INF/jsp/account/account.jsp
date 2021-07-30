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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <title><fmt:message key="html.head.title.ct"/></title>
</head>
<body>
    <jsp:useBean id="account" scope="session" type="by.tarasiuk.ct.entity.impl.Account"/>

    <div id="container">
        <jsp:include page="../common/header.jsp"/>

        <div id="middle">
            <div id="info">
                <span class="icon icon-exclamation-circle x2 icon-green"></span>
                <span>${account.firstName}, welcome to the <b>Cargo Trading!</b></span>
            </div>
        </div>

        <jsp:include page="../common/footer.jsp"/>
    </div>
</body>
</html>