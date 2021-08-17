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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <script src="${pageContext.request.contextPath}/js/company_validation.js"></script>
        <title><fmt:message key="forwarder.title.settings"/></title>
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
                            <fmt:message key="forwarder.title.settings"/>
                        </div>

                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="show_forwarder_creator_page">

                            <button class="btn-confirm" type="submit">
                                <span class="icon icon-user-plus">&nbsp;</span>
                                <fmt:message key="button.label.forwarder.add"/>
                            </button>
                        </form>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-circle icon-dodgerblue">&nbsp;</span>
                            <fmt:message key="forwarders.page.info"/>
                        </div>

                        <table>
                            <tr>
                                <th>&#8470;</th>
                                <th><fmt:message key="employee.table.firstName"/></th>
                                <th><fmt:message key="employee.table.lastName"/></th>
                                <th><fmt:message key="employee.table.login"/></th>
                                <th><fmt:message key="employee.table.email"/></th>
                                <th><fmt:message key="employee.table.registrationDate"/></th>
                                <th><fmt:message key="employee.table.role"/></th>
                                <th><fmt:message key="employee.table.status"/></th>
                            </tr>

                            <c:choose>
                                <c:when test="${empty account_list}">
                                    <tr>
                                        <td rowspan="7">Sss</td>
                                    </tr>
                                </c:when>

                                <c:otherwise>
                                    <c:forEach items="${account_list}" var="account" varStatus="loop">
                                        <tr>
                                            <td>${loop.count}</td>
                                            <td>${account.firstName}</td>
                                            <td>${account.lastName}</td>
                                            <td>${account.login}</td>
                                            <td>${account.email}</td>
                                            <td>${account.registrationDate}</td>
                                            <td>${account.role}</td>
                                            <td>${account.status}</td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </table>

                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>