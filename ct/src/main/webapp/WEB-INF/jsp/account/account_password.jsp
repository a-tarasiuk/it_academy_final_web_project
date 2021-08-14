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
        <script src="${pageContext.request.contextPath}/js/password_validation.js"></script>
        <title><fmt:message key="account.page.password"/></title>
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
                            <fmt:message key="account.page.password"/>
                        </div>

                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="show_account_settings">

                            <button class="btn-confirm" href="${pageContext.request.contextPath}/controller?command=go_to_create_offer_page">
                                <span class="icon icon-cog">&nbsp;</span>
                                <fmt:message key="account.page.setting"/>
                            </button>
                        </form>
                    </div>

                    <div id="mr-down">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="update_password">

                            <div class="ad-row">
                                <div class="sub-title">
                                    <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                                    <fmt:message key="page.title.account.password"/>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-lock"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="password" id="account_old_password" name="account_old_password" placeholder="<fmt:message key="placeholder.password.old"/>" oninput="validateOldPassword()" required autofocus>
                                        </div>
                                    </div>

                                    <label id="description_account_old_password" class="description"><fmt:message key="description.valid.userPassword"/></label>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-lock"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="password" id="account_new_password" name="account_new_password" placeholder="<fmt:message key="placeholder.password.new"/>" oninput="validateNewPassword()" required>
                                        </div>
                                    </div>

                                    <label id="description_account_new_password" class="description"><fmt:message key="description.valid.userPassword"/></label>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-lock"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="password" id="account_confirm_new_password" name="account_confirm_new_password" placeholder="<fmt:message key="placeholder.password.confirmNew"/>" oninput="validateConfirmNewPassword()" required>
                                        </div>
                                    </div>

                                    <label id="description_account_confirm_new_password" class="description"><fmt:message key="description.valid.userPassword"/></label>
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
                                        <fmt:message key="message.invalid.password"/>
                                    </c:if>

                                    <c:if test="${wrong_password == true}">
                                        <span class="icon icon-circle icon-red">&nbsp;</span>
                                        <fmt:message key="message.wrong.password"/>
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
                                    <fmt:message key="button.label.password.set"/>
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