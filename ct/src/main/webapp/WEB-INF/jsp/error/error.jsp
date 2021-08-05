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
        <div id="info">
            <span class="icon icon-exclamation-circle x4 icon-red"></span>

            <div id="error-wrapper">
                <div class="ew-column">
                    <div class="ew-row">
                        <span>ssssssssssssssssssssss</span>
                    </div>

                    <div class="ew-row">
                        <span>xxx</span>
                    </div>

                    <div class="ew-row">
                        <span>xxx</span>
                    </div>
                </div>

                <div class="ew-column">
                    <div class="ew-row">
                        <span>sss</span>
                    </div>

                    <div class="ew-row">
                        <span>xxddddddddddddddddddddx</span>
                    </div>

                    <div class="ew-row">
                        <span>xxx</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</div>
</body>
</html>