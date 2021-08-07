<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cargo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <title><fmt:message key="html.head.title.ct"/></title>
    </head>

    <body>
        <div id="m-left">
            <div id="ml-company-logo">
                <span>${account.firstName}, welcome to the <b>Cargo Trading!</b></span>
            </div>

            <a class="ml-item" href="${pageContext.request.contextPath}/controller?command=go_to_account_offers_page">
                <span class="icon icon-archive">&nbsp;</span>
                <fmt:message key="offer.my"/>
            </a>

            <a class="ml-item" href="${pageContext.request.contextPath}/controller?command=go_to_create_offer_page">
                <span class="icon icon-users">&nbsp;</span>
                <fmt:message key="employees.my"/>
            </a>
        </div>

        <div id="m-right">
            <div id="mr-up">
                <div id="window-title">
                    <fmt:message key="offer.all"/>
                </div>

                <a class="btn-confirm" href="${pageContext.request.contextPath}/controller?command=go_to_create_offer_page">
                    <fmt:message key="cargo.button.createOffer"/>
                </a>
            </div>

            <div id="mr-down">
                <ct:all_offer_list/>
            </div>
        </div>
    </body>
</html>