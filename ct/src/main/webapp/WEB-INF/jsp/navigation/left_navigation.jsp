<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<jsp:useBean id="account" scope="session" class="by.tarasiuk.ct.model.entity.impl.Account"/>
<jsp:useBean id="company" scope="session" class="by.tarasiuk.ct.model.entity.impl.Company"/>

<!-- Left navigation block -->
<div id="m-left">

    <!-- Account information -->
    <div class="ml-header">
        <fmt:message key="header.accountInfo"/>
    </div>

    <div class="ml-ci-row">
        <span class="icon icon-user">&nbsp;</span>
        <span><b>${account.firstName}&nbsp;${account.lastName}</b></span>
    </div>

    <c:if test="${account.role != 'ADMINISTRATOR'}">
        <div class="ml-ci-row">
            <span class="icon icon-building">&nbsp;</span>
            <span><b>${company.name}</b></span>
        </div>
    </c:if>

    <div class="ml-ci-row">
        <span class="icon icon-id-card-o">&nbsp;</span>

        <c:choose>
            <c:when test="${account.role == 'ADMINISTRATOR'}">
                <span style="color: red"><b>${account.role}</b></span>
            </c:when>

            <c:when test="${account.role == 'MANAGER'}">
                <span style="color: #8B00FF"><b>${account.role}</b></span>
            </c:when>

            <c:otherwise>
                <span style="color: #FFA500"><b>${account.role}</b></span>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Menu items -->
    <div class="ml-header">
        <fmt:message key="header.menu"/>
    </div>

    <a class="ml-item" href="${pageContext.request.contextPath}/controller?command=show_open_offer_list">
        <div class="icon-block">
            <span class="icon icon-road"></span>
        </div>

        <fmt:message key="offer.all"/>
    </a>

    <c:if test="${account.role != 'ADMINISTRATOR'}">
        <a class="ml-item" href="${pageContext.request.contextPath}/controller?command=show_account_offers">
            <div class="icon-block">
                <span class="icon icon-archive"></span>
            </div>

            <fmt:message key="offer.my"/>
        </a>

        <a class="ml-item" href="${pageContext.request.contextPath}/controller?command=show_account_tradings">
            <div class="icon-block">
                <span class="icon icon-history"></span>
            </div>

            <fmt:message key="tradings.my"/>
        </a>
    </c:if>
</div>