<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!-- Left navigation block -->
<div id="m-left">

    <!-- Account information -->
    <div class="ml-header">
        <fmt:message key="header.accountInfo"/>
    </div>

    <div class="ml-ci-row">
        <div class="icon-block">
            <span class="icon icon-user">&nbsp;</span>
        </div>

        <span><b>${sessionScope.account.firstName}&nbsp;${sessionScope.account.lastName}</b></span>
    </div>

    <c:if test="${sessionScope.account.role != 'ADMINISTRATOR'}">
        <div class="ml-ci-row">
            <div class="icon-block">
                <span class="icon icon-building">&nbsp;</span>
            </div>

            <span><b>${sessionScope.company.name}</b></span>
        </div>
    </c:if>

    <div class="ml-ci-row">
        <div class="icon-block">
            <span class="icon icon-id-card-o">&nbsp;</span>
        </div>

        <c:choose>
            <c:when test="${sessionScope.account.role == 'ADMINISTRATOR'}">
                <span class="colour-administrator"><b>${sessionScope.account.role}</b></span>
            </c:when>

            <c:when test="${sessionScope.account.role == 'MANAGER'}">
                <span class="colour-manager"><b>${sessionScope.account.role}</b></span>
            </c:when>

            <c:otherwise>
                <span class="colour-forwarder"><b>${sessionScope.account.role}</b></span>
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