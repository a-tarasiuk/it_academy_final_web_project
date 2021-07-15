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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
    <title><fmt:message key="form.signUp.title"/></title>
</head>
<body>
<div id="container">
    <div id="main">
        <div id="window-title" class="row"><fmt:message key="form.signUp.message"/></div>

        <form action="controller" method="post" novalidate>
            <div class="row">
                <label class="description">
                    <fmt:message key="form.signUp.warning"/>
                </label>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-user"></span>
                        <input type="text" name="first_name" placeholder="<fmt:message key="placeholder.userFirstName"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.userFirstName"/></label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-user"></span>
                        <input type="text" name="last_name" placeholder="<fmt:message key="placeholder.userLastName"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.userLastName"/></label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-user-circle-o"></span>
                        <input type="text" name="user_login" placeholder="<fmt:message key="placeholder.userLogin"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.userLogin"/></label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-email"></span>
                        <input type="email" name="user_email" placeholder="<fmt:message key="placeholder.userEmail"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.userEmail"/></label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-building"></span>
                        <input type="text" name="company_name" placeholder="<fmt:message key="placeholder.companyName"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.companyName"/></label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-globe"></span>

                        <select name="country_name">
                            <option selected disabled><fmt:message key="placeholder.selectCompanyCountry"/></option>
                            <option>RUS</option>
                            <option>BLR</option>
                            <option>POL</option>
                            <option>UKR</option>
                            <option>DEU</option>
                            <option>CHN</option>
                        </select>
                    </div>

                    <label class="description"><fmt:message key="description.valid.companyCountry"/></label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-map-marker"></span>
                        <input type="text" name="company_address" placeholder="<fmt:message key="placeholder.companyAddress"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.companyAddress"/></label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-phone"></span>
                        <input type="text" name="company_phone" placeholder="<fmt:message key="placeholder.companyPhoneNumber"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.companyPhone"/></label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-lock"></span>
                        <input type="password" name="user_password" placeholder="<fmt:message key="placeholder.userPassword"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.userPassword"/></label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-lock"></span>
                        <input type="password" name="user_confirm_password" placeholder="<fmt:message key="placeholder.userConfirmPassword"/>">
                    </div>

                    <label class="description"><fmt:message key="description.valid.userConfirmPassword"/></label>
                </div>
            </div>

            <div class="row">
                <button class="button-back" name="command" value="go_to_main_page"><fmt:message key="button.label.mainPage"/></button>
                <button class="button-confirm" name="command"><fmt:message key="button.label.confirm"/></button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="../../static/js/password.js"></script>
</body>
</html>