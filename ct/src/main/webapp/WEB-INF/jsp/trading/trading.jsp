<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="account" scope="session" type="by.tarasiuk.ct.entity.impl.Account"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cargo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <title><fmt:message key="html.head.title.ct"/></title>
    </head>

    <body>
        <div id="m-left">
            <div id="ml-company-logo">
                <span>${account.firstName}, welcome to the <b>Cargo Trading!</b></span>
            </div>

            <div class="ml-item">
                <fmt:message key="cargo.all"/>
            </div>

            <div class="ml-item">
                <fmt:message key="cargo.my"/>
            </div>
        </div>

        <div id="m-right">
            <div id="mr-up">
                <a class="btn-confirm" href="${pageContext.request.contextPath}/controller?command=go_to_create_offer_page">
                    <fmt:message key="cargo.button.createOffer"/>
                </a>
            </div>

            <div id="mr-down">
                <span>${account.firstName}, welcome to the <b>Cargo Trading!</b></span>
            </div>
        </div>
    </body>
</html>