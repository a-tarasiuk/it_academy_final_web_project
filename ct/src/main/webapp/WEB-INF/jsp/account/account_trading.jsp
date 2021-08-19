<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="offer" scope="request" class="by.tarasiuk.ct.model.entity.impl.Offer"/>

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

        <script src="${pageContext.request.contextPath}/js/freight-validation.js"></script>

        <title><fmt:message key="html.head.title.ct"/></title>
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
                            <fmt:message key="offer.current"/>
                        </div>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-info-circle" style="color: dodgerblue">&nbsp;</span>
                            <fmt:message key="tradings.limitation"/>
                        </div>

                        <!-- Offer information -->
                        <ct:offer_viewer/>

                        <!-- Window for trading -->
                        <div class="title"><fmt:message key="freight.offer"/></div>

                        <form action="controller" method="post">
                            <input type="hidden" name="offer_id" value="${offer.id}">

                            <div class="trading">
                                <div class="trading-section">&#x24;</div>

                                <div class="trading-section">
                                    <input type="text" id="freight" name="trading_freight" placeholder="<fmt:message key="offer.freight"/>" oninput="validateFreight()" required>
                                </div>

                                <div class="trading-section">
                                    <button class="btn-simple btn-green" type="submit" id="confirm" name="command" value="create_trading">
                                        <fmt:message key="button.label.submit"/>
                                    </button>
                                </div>
                            </div>
                        </form>

                        <label id="description_freight" class="description">
                            <fmt:message key="description.tradingFreight"/>
                        </label>

                        <!-- Operation messages -->
                        <div class="error-message row">
                            <c:if test="${incorrect_trading_freight == true}">
                                <fmt:message key="message.invalid.tradingFreight"/>
                            </c:if>

                            <c:if test="${message_query_error == true}">
                                <fmt:message key="message.query.error"/>
                            </c:if>
                        </div>

                        <!-- List tradings if exist -->
                        <ct:trading_history/>
                    </div>
                </div>
            </div>
            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>