<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="offer" scope="request" type="by.tarasiuk.ct.model.entity.impl.Offer"/>

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
        <script src="${pageContext.request.contextPath}/js/offer-validation.js"></script>
        <title><fmt:message key="offer.edit"/></title>
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
                            <fmt:message key="offer.editor"/>
                        </div>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-circle" style="color: dodgerblue">&nbsp;</span>
                            <fmt:message key="offer.change"/>
                        </div>

                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="update_offer">
                            <input type="hidden" name="offer_id" value="${offer.id}">

                            <div class="ow-line">
                                <label class="description">
                                    <fmt:message key="form.warning"/>
                                </label>
                            </div>

                            <div class="ow-line">
                                <div class="data">
                                    <div class="icon-block">
                                        <span class="icon icon-cubes"></span>
                                    </div>
                                    <div class="input-block">
                                        <input type="text" id="product_name" name="offer_product_name" value="${offer.productName}" oninput="validateProductName()" required>
                                    </div>
                                </div>

                                <label id="description_product_name" class="description"><fmt:message key="description.productName"/></label>
                            </div>

                            <div class="ow-line">
                                <div class="data">
                                    <div class="icon-block">
                                        <span class="icon icon-weight"></span>
                                    </div>
                                    <div class="input-block">
                                        <input type="text" id="weight" name="offer_product_weight" value="${offer.productWeight}" oninput="validateWeight()" required>
                                    </div>
                                </div>

                                <label id="description_weight" class="description"><fmt:message key="description.weight"/></label>
                            </div>

                            <div class="ow-line">
                                <div class="data">
                                    <div class="icon-block">
                                        <span class="icon icon-cube"></span>
                                    </div>
                                    <div class="input-block">
                                        <input type="text" id="volume" name="offer_product_volume" value="${offer.productVolume}" oninput="validateVolume()" required>
                                    </div>
                                </div>

                                <label id="description_volume" class="description"><fmt:message key="description.volume"/></label>
                            </div>

                            <div class="ow-line">
                                <div class="data">
                                    <div class="icon-block">
                                        <span class="icon icon-map-marker"></span>
                                    </div>
                                    <div class="input-block">
                                        <input type="text" id="address_from" name="offer_address_from" value="${offer.addressFrom}" oninput="validateAddressFrom()" required>
                                    </div>
                                </div>

                                <label id="description_address_from" class="description"><fmt:message key="description.addressFrom"/></label>
                            </div>

                            <div class="ow-line">
                                <div class="data">
                                    <div class="icon-block">
                                        <span class="icon icon-map-marker"></span>
                                    </div>
                                    <div class="input-block">
                                        <input type="text" id="address_to" name="offer_address_to" value="${offer.addressTo}" oninput="validateAddressTo()" required>
                                    </div>
                                </div>

                                <label id="description_address_to" class="description"><fmt:message key="description.addressTo"/></label>
                            </div>

                            <div class="ow-line">
                                <div class="data">
                                    <div class="icon-block">
                                        <span class="icon icon-usd"></span>
                                    </div>
                                    <div class="input-block">
                                        <input type="text" id="freight" name="offer_freight" value="${offer.freight}" oninput="validateFreight()" required>
                                    </div>
                                </div>

                                <label id="description_freight" class="description"><fmt:message key="description.freight"/></label>
                            </div>

                            <div class="ow-line error-message">
                                <c:if test="${incorrect_offer_data == true}">
                                    <fmt:message key="message.incorrect.offerData"/>
                                </c:if>

                                <c:if test="${message_query_error == true}">
                                    <fmt:message key="message.query.error"/>
                                </c:if>
                            </div>

                            <button class="btn-confirm" type="submit"><fmt:message key="button.label.apply"/></button>
                        </form>
                    </div>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>