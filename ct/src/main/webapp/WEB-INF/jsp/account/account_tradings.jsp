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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/offer.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <title><fmt:message key="tradings.my"/></title>
    </head>

    <body>
        <div id="container">
            <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

            <div id="middle">
                <div id="m-left">
                    <jsp:include page="/WEB-INF/jsp/navigation/left_navigation.jsp"/>
                </div>

                <div id="m-right">
                    <div id="mr-up">
                        <div id="window-title">
                            <fmt:message key="tradings.my"/>
                        </div>

                        <form id="ow-buttons" action="controller" method="get">
                            <input type="hidden" name="command" value="show_account_tradings">

                            <button type="submit" class="btn-simple btn-blue">
                                <fmt:message key="tradings.allMy"/>
                            </button>

                            <button type="submit" name="trading_status" value="accepted" class="btn-simple btn-blue">
                                <fmt:message key="tradings.accepted"/>
                            </button>

                            <button type="submit" name="trading_status" value="not_accepted" class="btn-simple btn-blue">
                                <fmt:message key="tradings.notAccepted"/>
                            </button>
                        </form>
                    </div>

                    <div id="mr-down">
                        <div class="table">
                            <div class="table-row">
                                <div class="table-header">&#x2116;</div>
                                <div class="table-header"><fmt:message key="offer.table.productName"/></div>
                                <div class="table-header"><fmt:message key="offer.table.productWeight"/></div>
                                <div class="table-header"><fmt:message key="offer.table.productVolume"/></div>
                                <div class="table-header"><fmt:message key="offer.table.address"/></div>
                                <div class="table-header"><fmt:message key="offer.table.creationDate"/></div>
                                <div class="table-header"><fmt:message key="offer.freight"/></div>
                                <div class="table-header"><fmt:message key="offer.table.myFreight"/></div>
                                <div class="table-header"><fmt:message key="tradings.table.status"/></div>
                            </div>

                            <c:forEach items="${trading_map}" var="map">
                                <c:set var="count" value="${count + 1}" scope="page"/>

                                <a class="table-row" href="${pageContext.request.contextPath}/controller?command=show_account_trading_viewer&trading_id=${map.value.id}">
                                    <div class="table-data">${count}</div>
                                    <div class="table-data">${map.key.productName}</div>
                                    <div class="table-data">${map.key.productWeight}</div>
                                    <div class="table-data">${map.key.productVolume}</div>
                                    <div class="table-data">${map.key.addressFrom} - ${map.key.addressTo}</div>
                                    <div class="table-data">${map.key.creationDate}</div>
                                    <div class="table-data">${map.key.freight}</div>
                                    <div class="table-data">${map.value.freight}</div>
                                    
                                    <c:choose>
                                        <c:when test="${map.value.status == 'ACCEPTED'}">
                                            <div class="table-data bc-green">${map.value.status}</div>
                                        </c:when>

                                        <c:otherwise>
                                            <div class="table-data bc-red">${map.value.status}</div>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>