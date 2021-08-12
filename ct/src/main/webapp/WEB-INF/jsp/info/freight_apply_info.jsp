<%@ page isErrorPage="true" import="jakarta.servlet.jsp.JspFactory" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${locale}" scope="session"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <title><fmt:message key="error.title"/>&nbsp;${pageContext.errorData.statusCode}</title>
    </head>

    <body>
        <div id="container">
            <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

            <div id="middle">
                <form id="info" action="controller" method="get">
                    <input type="hidden" name="command" value="show_account_offers">

                    <span class="icon icon-handshake-o icon-green x4"></span>
                    <span id="info-header"><fmt:message key="tradings.congratulation"/></span>
                    <span id="info-message"><fmt:message key="tradings.congratulation.title"/></span>
                    <button type="submit" class="btn-confirm"><fmt:message key="button.label.backOfferViewerPage"/></button>
                </form>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>