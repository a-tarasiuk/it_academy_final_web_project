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
        <title><fmt:message key="tradings.my"/></title>
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
                        <ct:account_trading_list/>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>