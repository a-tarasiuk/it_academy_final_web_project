<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="account" scope="session" class="by.tarasiuk.ct.model.entity.impl.Account"/>
<jsp:useBean id="company" scope="request" class="by.tarasiuk.ct.model.entity.impl.Company"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
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
                    </div>

                    <div id="mr-down">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="update_account">

                            <div class="ad-row">
                                <div class="sub-title">
                                    <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                                    <fmt:message key="form.warning"/>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user"></span>
                                        </div>
                                        <div class="input-block">
                                            <input type="text" id="account_first_name" name="account_first_name" value="${account.firstName}" onblur="validationFirstName()" onchange="validationFirstName()">
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
                                            <input type="text" id="account_last_name" name="account_last_name" value="${account.lastName}">
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
                                            <input type="text" id="account_login" name="account_login" value="${account.login}">
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
                                            <input type="email" id="account_email" name="account_email" value="${account.email}">
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
                                            <span class="icon icon-building"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="company_name" name="company_name" value="${company.name}">
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
                                            <input type="text" id="company_address" name="company_address" value="${company.address}">
                                        </div>
                                    </div>

                                    <label id="description_company_address" class="description"><fmt:message key="description.valid.companyAddress"/></label>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-phone"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="company_phone_number" name="company_phone_number" value="${company.phoneNumber}">
                                        </div>
                                    </div>

                                    <label id="description_company_phone_number" class="description"><fmt:message key="description.valid.companyPhoneNumber"/></label>
                                </div>
                            </div>

                            <div class="ad-row">
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

                            <div class="ad-row">
                                <button class="btn-confirm" type="submit"><fmt:message key="button.label.apply"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>