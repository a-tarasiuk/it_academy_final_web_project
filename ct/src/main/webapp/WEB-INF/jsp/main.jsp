<%@ page import="jakarta.servlet.jsp.JspFactory" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${locale}" scope="session"/>

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
    <div id="container">
        <jsp:include page="common/header.jsp"/>

        <div id="middle">
            <c:choose>
                <c:when test="${not empty sessionScope.account}">
                    <jsp:include page="/WEB-INF/jsp/trading/trading.jsp"/>
                </c:when>

                <c:otherwise>
                    <span>Hello, user</span>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="common/footer.jsp"/>
    </div>
</body>
</html>