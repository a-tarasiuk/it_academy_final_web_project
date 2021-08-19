<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="account" scope="session" type="by.tarasiuk.ct.model.entity.impl.Account"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <script src="${pageContext.request.contextPath}/js/company_validation.js"></script>
        <title><fmt:message key="accounts.title.list"/></title>
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
                            <fmt:message key="accounts.title.list"/>
                        </div>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-circle icon-dodgerblue">&nbsp;</span>
                            <fmt:message key="accounts.title.info"/>
                        </div>

                        <div class="table">
                            <div class="table-row">
                                <div class="table-header">&#8470;</div>
                                <div class="table-header"><fmt:message key="employee.table.firstName"/></div>
                                <div class="table-header"><fmt:message key="employee.table.lastName"/></div>
                                <div class="table-header"><fmt:message key="employee.table.login"/></div>
                                <div class="table-header"><fmt:message key="employee.table.email"/></div>
                                <div class="table-header"><fmt:message key="employee.table.registrationDate"/></div>
                                <div class="table-header"><fmt:message key="employee.table.role"/></div>
                                <div class="table-header"><fmt:message key="employee.table.status"/></div>
                            </div>

                            <c:choose>
                                <c:when test="${empty account_list}">
                                    <div class="table-row">
                                        <div class="table-data"><fmt:message key="accounts.not.found"/></div>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <c:forEach items="${account_list}" var="account" varStatus="loop">
                                        <a class="table-row" href="${pageContext.request.contextPath}/controller?command=show_account_editor_from_admin&account_id=${account.id}">
                                            <div class="table-data">${loop.count}</div>
                                            <div class="table-data">${account.firstName}</div>
                                            <div class="table-data">${account.lastName}</div>
                                            <div class="table-data">${account.login}</div>
                                            <div class="table-data">${account.email}</div>
                                            <div class="table-data">${account.registrationDate}</div>
                                            <div class="table-data">${account.role}</div>

                                            <c:if test="${account.status == 'ACTIVATED'}">
                                                <div class="table-data" style="font-weight: bold; color: green">${account.status}</div>
                                            </c:if>

                                            <c:if test="${account.status == 'NOT_ACTIVATED'}">
                                                <div class="table-data" style="font-weight: bold; color: orange">${account.status}</div>
                                            </c:if>

                                            <c:if test="${account.status == 'BANNED'}">
                                                <div class="table-data" style="font-weight: bold; color: red">${account.status}</div>
                                            </c:if>
                                        </a>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>