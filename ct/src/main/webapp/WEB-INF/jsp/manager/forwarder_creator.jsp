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
        <script src="${pageContext.request.contextPath}/js/employee_validation.js"></script>
        <title><fmt:message key="forwarder.title.creator"/></title>
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
                            <fmt:message key="forwarder.title.creator"/>
                        </div>

                        <a class="btn-confirm" href="${pageContext.request.contextPath}/controller?command=show_forwarder_settings_page">
                            <span class="icon icon-chevron-left">&nbsp;</span>
                            <fmt:message key="forwarders.button.back"/>
                        </a>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                            <fmt:message key="forwarder.page.creator"/>
                        </div>

                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="create_forwarder">
                            <input type="hidden" name="company_name" value="${company_name}">

                            <div id="ow-title">
                                <fmt:message key="forwarder.create"/>
                            </div>

                            <div class="ad-row">
                                <label class="description">
                                    <fmt:message key="form.warning"/>
                                </label>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user"></span>
                                        </div>
                                        <div class="input-block">
                                            <input type="text" id="account_first_name" name="account_first_name" value="${account_first_name}" placeholder="<fmt:message key="placeholder.userFirstName"/>" oninput="validateAccountFirstName()" autofocus required>
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
                                            <input type="text" id="account_last_name" name="account_last_name" value="${account_last_name}" placeholder="<fmt:message key="placeholder.userLastName"/>" oninput="validateAccountLastName()" required>
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
                                            <input type="text" id="account_login" name="account_login" value="${account_login}" placeholder="<fmt:message key="placeholder.userLogin"/>" oninput="validateAccountLogin()" required>
                                        </div>
                                    </div>

                                    <label id="description_account_login" class="description"><fmt:message key="description.valid.userLogin"/></label>

                                    <div class="error-message row">
                                        <c:if test="${error_login_already_exist == true}">
                                            <fmt:message key="message.exist.login"/>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-email"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="email" id="account_email" name="account_email" value="${account_email}" placeholder="<fmt:message key="placeholder.userEmail"/>" oninput="validateAccountEmail()" required>
                                        </div>
                                    </div>

                                    <label id="description_account_email" class="description"><fmt:message key="description.valid.userEmail"/></label>

                                    <div class="error-message row">
                                        <c:if test="${error_email_already_exist == true}">
                                            <fmt:message key="message.exist.email"/>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-lock"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="password" id="account_password" name="account_password" placeholder="<fmt:message key="placeholder.userPassword"/>" oninput="validatePassword()" required>
                                        </div>
                                    </div>

                                    <label id="description_account_password" class="description"><fmt:message key="description.valid.userPassword"/></label>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-lock"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="password" id="account_confirm_password" name="account_confirm_password" placeholder="<fmt:message key="placeholder.userConfirmPassword"/>" oninput="validateConfirmPassword()" required>
                                        </div>
                                    </div>

                                    <label id="description_account_confirm_password" class="description"><fmt:message key="description.valid.userConfirmPassword"/></label>
                                </div>
                            </div>

                            <div class="error-message row">
                                <c:if test="${message_query_error == true}">
                                    <fmt:message key="message.query.error"/>
                                </c:if>

                                <c:if test="${invalid_data == true}">
                                    <fmt:message key="message.invalid.account.data"/>
                                </c:if>
                            </div>

                            <div class="ad-row">
                                <button class="btn-confirm" type="submit">
                                    <fmt:message key="forwarder.button.create"/>
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