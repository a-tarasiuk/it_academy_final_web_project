<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<jsp:useBean id="account" scope="session" class="by.tarasiuk.ct.model.entity.impl.Account"/>

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
                    <c:when test="${not empty account && account.role != 'GUEST'}">
                        <jsp:forward page="${pageContext.request.contextPath}/controller?command=show_open_offer_list"/>
                    </c:when>

                    <c:otherwise>
                        <span><fmt:message key="hello.user"/></span>
                    </c:otherwise>
                </c:choose>
            </div>

            <jsp:include page="common/footer.jsp"/>
        </div>
    </body>
</html>