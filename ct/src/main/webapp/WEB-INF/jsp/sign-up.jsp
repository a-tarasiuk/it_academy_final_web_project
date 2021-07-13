<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>

<fmt:setBundle basename="locale" />

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
    <title>Sign up</title>
</head>
<body>
<div id="container">
    <div id="main">
        <div id="window-title" class="row"><fmt:message key="message.form.signup"/></div>

        <form action="controller" method="post" novalidate>
            <div class="row">
                <label class="description">* All fields are!</label>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-user"></span>
                        <input type="text" name="first_name" placeholder="First name">
                    </div>

                    <label class="description">First name may contain only letters. <br>Min. lengths - 3 characters. <br>Max. lengths - 50 characters.</label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-user"></span>
                        <input type="text" name="last_name" placeholder="Last name">
                    </div>

                    <label class="description">Last name may contain only letters. <br>Min. lengths - 3 characters. <br>Max. lengths - 50 characters.</label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-user-circle-o"></span>
                        <input type="text" name="user_login" placeholder="Login">
                    </div>

                    <label class="description">Login may contain only letters and numbers. <br>Min. lengths - 3 characters. <br>Max. lengths - 50 characters.</label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-email"></span>
                        <input type="email" name="user_email" placeholder="Email@host.com">
                    </div>

                    <label class="description">Specify the real address, it's necessary to activate your account.</label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-building"></span>
                        <input type="text" name="company_name" placeholder="Company name">
                    </div>

                    <label class="description">Company name may contain only letters and symbols of tire &#171;-&#187;. <br>Min. lengths - 3 characters. <br>Max. lengths - 150 characters.</label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-globe"></span>

                        <select name="country_name">
                            <option selected disabled>Select country</option>
                            <option>RUS</option>
                            <option>BLR</option>
                            <option>POL</option>
                            <option>UKR</option>
                            <option>DEU</option>
                            <option>CHN</option>
                        </select>
                    </div>

                    <label class="description">Country of registration company.</label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-map-marker"></span>
                        <input type="text" name="company_address" placeholder="Company address">
                    </div>

                    <label class="description">Specify the real address company.<br>Min. lengths - 3 characters. <br>Max. lengths - 200 characters.</label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-phone"></span>
                        <input type="text" name="company_phone" placeholder="+375 (29) 205-47-01">
                    </div>

                    <label class="description">Specify the real phone number for communication so that other freight forwarders can contact you.</label>
                </div>
            </div>

            <div class="row">
                <div class="block">
                    <div class="data">
                        <span class="icon icon-lock"></span>
                        <input type="password" name="user_password" placeholder="Password">
                    </div>

                    <label class="description">Your password may be from 8 to 30 characters long.</label>
                </div>

                <div class="block">
                    <div class="data">
                        <span class="icon icon-lock"></span>
                        <input type="password" name="user_confirm_password" placeholder="Confirm password">
                    </div>

                    <label class="description">Confirm your password.</label>
                </div>
            </div>

            <div class="row">
                <button class="button-back" name="command" value="go_to_main_page">Main page</button>
                <button class="button-confirm" name="command">Confirm</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="../../static/js/password.js"></script>
</body>
</html>