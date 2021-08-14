<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="company" scope="request" class="by.tarasiuk.ct.model.entity.impl.Company"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <script src="${pageContext.request.contextPath}/js/company_validation.js"></script>
        <title><fmt:message key="company.title.settings"/></title>
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
                            <fmt:message key="company.title.settings"/>
                        </div>
                    </div>

                    <div id="mr-down">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="update_company">

                            <div class="ad-row">
                                <div class="sub-title">
                                    <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                                    <fmt:message key="company.page.info"/>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-building"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" value="${company.name}" disabled>
                                        </div>
                                    </div>

                                    <label id="description_company_name" class="description"><fmt:message key="description.valid.companyName"/></label>

                                    <div class="error-message row">
                                        <c:if test="${error_company_already_exist == true}">
                                            <fmt:message key="message.exist.company"/>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-map-marker"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="company_address" name="company_address" value="${company.address}" oninput="validateCompanyAddress()" required>
                                        </div>
                                    </div>

                                    <label id="description_company_address" class="description"><fmt:message key="description.valid.companyAddress"/></label>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-phone"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="company_phone_number" name="company_phone_number" value="${company.phoneNumber}" oninput="validateCompanyPhoneNumber()" required>
                                        </div>
                                    </div>

                                    <label id="description_company_phone_number" class="description"><fmt:message key="description.valid.companyPhoneNumber"/></label>
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
                                        <fmt:message key="message.invalid.company.data"/>
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
                                <button class="btn-confirm" type="submit" name="company_id" value="${company.id}">
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