<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${sessionScope.locale_page}"/>
<fmt:setBundle basename="locale"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

    <script src="${pageContext.request.contextPath}/js/sign-up-validation.js"></script>
    <title><fmt:message key="form.signUp.title"/></title>
</head>
<body>
    <div id="container">
        <div id="main">
            <div id="window-title" class="row">
                <fmt:message key="form.signUp.message"/>
            </div>

            <form action="controller" method="post" novalidate>
                <div class="row">
                    <label class="description">
                        <fmt:message key="form.warning"/>
                    </label>
                </div>

                <div class="row">
                    <div class="block">
                        <div class="data">
                            <div class="icon-block">
                                <span class="icon icon-user"></span>
                            </div>
                            <div class="input-block">
                                <input type="text" id="account_first_name" name="account_first_name" value="${account_first_name}" placeholder="<fmt:message key="placeholder.userFirstName"/>" onblur="validationFirstName()" onchange="validationFirstName()">
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
                                <input type="text" id="account_last_name" name="account_last_name" value="${account_last_name}" placeholder="<fmt:message key="placeholder.userLastName"/>">
                            </div>
                        </div>

                        <label id="description_account_last_name" class="description"><fmt:message key="description.valid.userLastName"/></label>
                    </div>
                </div>

                <div class="row">
                    <div class="block">
                        <div class="data">
                            <div class="icon-block">
                                <span class="icon icon-user-circle-o"></span>
                            </div>

                            <div class="input-block">
                                <input type="text" id="account_login" name="account_login" value="${account_login}" placeholder="<fmt:message key="placeholder.userLogin"/>">
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
                                <input type="email" id="account_email" name="account_email" value="${account_email}" placeholder="<fmt:message key="placeholder.userEmail"/>">
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

                <div class="row">
                    <div class="block">
                        <div class="data">
                            <div class="icon-block">
                                <span class="icon icon-building"></span>
                            </div>

                            <div class="input-block">
                                <input type="text" id="company_name" name="company_name" value="${company_name}" placeholder="<fmt:message key="placeholder.companyName"/>">
                            </div>
                        </div>

                        <label id="description_company_name" class="description"><fmt:message key="description.valid.companyName"/></label>

                        <div class="error-message row">
                            <c:if test="${error_company_already_exist == true}">
                                <fmt:message key="message.exist.company"/>
                            </c:if>
                        </div>
                    </div>

                    <div class="block">
                        <div class="data">
                            <div class="icon-block">
                                <span class="icon icon-map-marker"></span>
                            </div>

                            <div class="input-block">
                                <input type="text" id="company_address" name="company_address" value="${company_address}" placeholder="<fmt:message key="placeholder.companyAddress"/>">
                            </div>
                        </div>

                        <label id="description_company_address" class="description"><fmt:message key="description.valid.companyAddress"/></label>
                    </div>
                </div>

                <div class="row">
                    <div class="block">
                        <div class="data">
                            <div class="icon-block">
                                <span class="icon icon-phone"></span>
                            </div>

                            <div class="input-block">
                                <input type="text" id="company_phone_number" name="company_phone_number" value="${company_phone_number}" placeholder="<fmt:message key="placeholder.companyPhoneNumber"/>">
                            </div>
                        </div>

                        <label id="description_company_phone_number" class="description"><fmt:message key="description.valid.companyPhoneNumber"/></label>
                    </div>
                </div>

                <div class="row">
                    <div class="block">
                        <div class="data">
                            <div class="icon-block">
                                <span class="icon icon-lock"></span>
                            </div>

                            <div class="input-block">
                                <input type="password" id="account_password" name="account_password" placeholder="<fmt:message key="placeholder.userPassword"/>">
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
                                <input type="password" id="account_confirm_password" name="account_confirm_password" placeholder="<fmt:message key="placeholder.userConfirmPassword"/>">
                            </div>
                        </div>

                        <label id="description_account_confirm_password" class="description"><fmt:message key="description.valid.userConfirmPassword"/></label>
                    </div>
                </div>

                <div class="error-message row">
                    <c:if test="${message_query_error == true}">
                        <fmt:message key="message.query.error"/>
                    </c:if>

                    <c:if test="${incorrect_sign_up_data == true}">
                        <fmt:message key="message.incorrect.signUpData"/>
                    </c:if>
                </div>

                <div class="row">
                    <button type="submit" class="button-back" name="command" value="go_to_main_page"><fmt:message key="button.label.mainPage"/></button>
                    <button id="confirm" type="submit" class="button-confirm" name="command" value="sign_up"><fmt:message key="button.label.confirm"/></button>
                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript" src="../../static/js/password.js"></script>
</body>
</html>