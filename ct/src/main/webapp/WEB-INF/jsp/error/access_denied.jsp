<%@ page isErrorPage="true" import="jakarta.servlet.jsp.JspFactory" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <title><fmt:message key="error.access.denied.header"/></title>
</head>

<body>
    <div id="container">
        <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

        <div id="middle">
            <div id="info">
                <span class="icon icon-exclamation-circle icon-red x4"></span>
                <span id="info-header"><fmt:message key="error.access.denied.header"/></span>
                <span id="info-message"><fmt:message key="error.access.denied.message"/>&nbsp<a class="btn-confirm" href="/"><fmt:message key="button.label.homepage"/></a></span>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
    </div>
    </body>
</html>