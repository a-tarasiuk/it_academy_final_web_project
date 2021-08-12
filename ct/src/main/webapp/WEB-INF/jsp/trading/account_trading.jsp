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
                    <ct:employee_info/>
                    <ct:account_menu/>
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

                        <ct:offer_viewer/>
                        <ct:trading_freight/>

                        <div class="error-message row">
                            <c:if test="${incorrect_trading_freight == true}">
                                <fmt:message key="message.incorrect.tradingFreight"/>
                            </c:if>

                            <c:if test="${message_query_error == true}">
                                <fmt:message key="message.query.error"/>
                            </c:if>
                        </div>

                        <ct:trading_history/>
                    </div>
                </div>
            </div>
            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>