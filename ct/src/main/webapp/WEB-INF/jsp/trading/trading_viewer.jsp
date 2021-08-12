<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="company" scope="request" type="by.tarasiuk.ct.model.entity.impl.Company"/>
<jsp:useBean id="account" scope="request" type="by.tarasiuk.ct.model.entity.impl.Account"/>
<jsp:useBean id="trading" scope="request" type="by.tarasiuk.ct.model.entity.impl.Trading"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cargo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/offer.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <title><fmt:message key="offer.viewer"/></title>
    </head>

    <body>
        <div id="container">
            <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

            <div id="middle">
                <div id="m-left">
                    <ct:employee_info/>
                    <ct:account_menu/>
                </div>

                <div id="m-right">
                    <div id="mr-up">
                        <div id="window-title">
                            <fmt:message key="tradings.viewer"/>
                        </div>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                            <fmt:message key="tradings.action.title"/>
                        </div>

                        <div class="info-window">
                            <div class="title iw-header">
                                <fmt:message key="employee.info"/>
                            </div>
                            <div class="iw-line"><span class="icon icon-building">&nbsp;</span>${company.name}</div>
                            <div class="iw-line"><span class="icon icon-map-marker">&nbsp;</span>${company.address}</div>
                            <div class="iw-line"><span class="icon icon-phone">&nbsp;</span>${company.phoneNumber}</div>
                            <div class="iw-line"><span class="icon icon-user">&nbsp;</span>${account.lastName}&nbsp;${account.firstName}</div>
                            <div class="iw-line"><span class="icon icon-address-card-o">&nbsp;</span>${account.role}</div>
                            <div class="iw-line"><span class="icon icon-email">&nbsp;</span>${account.email}</div>
                        </div>

                        <div class="freight-row">
                            <div class="fr-inside">
                                <div class="fr-left">
                                    ${offer_freight}&nbsp;&#36;
                                    <span class="fr-description"><fmt:message key="freight.your"/></span>
                                </div>
                                <div class="fr-right">
                                    ${trading.freight}&nbsp;&#36;
                                    <span class="fr-description"><fmt:message key="freight.company"/></span>
                                </div>
                            </div>

                            <c:if test="${trading.status == 'NOT_ACCEPTED'}">
                                <div id="ow-buttons">
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="apply_freight">

                                        <button type="submit" style="border-radius: 10px" class="btn-simple btn-green" name="trading_id" value="${trading.id}">
                                            <span class="icon icon-check">&nbsp;</span>
                                            <fmt:message key="tradings.applyFreight"/>
                                        </button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>