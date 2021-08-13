<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="account" scope="session" class="by.tarasiuk.ct.model.entity.impl.Account"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <script src="${pageContext.request.contextPath}/js/data-validation.js"></script>
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
                            <fmt:message key="account.page.setting"/>
                        </div>

                        <a class="btn-confirm" href="${pageContext.request.contextPath}/controller?command=go_to_account_password_page">
                            <span class="icon icon-lock">&nbsp;</span>
                            <fmt:message key="button.label.password"/>
                        </a>
                    </div>

                    <div id="mr-down">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="update_account">

                            <div class="ad-row">
                                <div class="sub-title">
                                    <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                                    <fmt:message key="page.title.account.settings"/>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user"></span>
                                        </div>
                                        <div class="input-block">
                                            <input type="text" id="account_first_name" name="account_first_name" value="${account.firstName}" oninput="validateAccountFirstName()">
                                        </div>
                                    </div>

                                    <label id="description_account_first_name" class="description"><fmt:message key="description.valid.userFirstName"/></label>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="account_last_name" name="account_last_name" value="${account.lastName}" oninput="validateAccountLastName()">
                                        </div>
                                    </div>

                                    <label id="description_account_last_name" class="description"><fmt:message key="description.valid.userLastName"/></label>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user-circle-o"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" value="${account.login}" disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-email"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="email" value="${account.email}" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-address-card-o"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" value="${account.role}" disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-shield"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="email" value="${account.status}" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="message">
                                <div class="message bc-red">
                                    <c:if test="${message_query_error == true}">
                                        <span class="icon icon-circle icon-red">&nbsp;</span>
                                        <fmt:message key="message.query.error"/>
                                    </c:if>

                                    <c:if test="${invalid_data == true}">
                                        <span class="icon icon-circle icon-red">&nbsp;</span>
                                        <fmt:message key="message.invalid.account.data"/>
                                    </c:if>
                                </div>

                                <c:if test="${successful_operation == true}">
                                    <div class="message bc-green">
                                        <span class="icon icon-circle icon-green">&nbsp;</span>
                                        <fmt:message key="message.successful.changesSaved"/>
                                    </div>
                                </c:if>
                            </div>

                            <div class="ad-row">
                                <button class="btn-confirm" type="submit">
                                    <span class="icon icon-floppy-o">&nbsp;</span>
                                    <fmt:message key="button.label.save"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>