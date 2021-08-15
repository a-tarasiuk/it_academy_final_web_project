<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="account_for_change" scope="request" class="by.tarasiuk.ct.model.entity.impl.Account"/>

<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <title><fmt:message key="account.title.editor"/></title>
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
                            <fmt:message key="account.title.editor"/>
                        </div>

                        <form action="controller" method="post">
                            <c:if test="${account_for_change.role != 'ADMINISTRATOR'}">
                                <c:if test="${account_for_change.status == 'ACTIVATED'}">
                                    <input type="hidden" name="command" value="ban_account">

                                    <button type="submit" class="btn-simple btn-red" name="account_id" value="${account_for_change.id}">
                                        <span class="icon icon-ban">&nbsp;</span>
                                        <fmt:message key="button.account.ban"/>
                                    </button>
                                </c:if>

                                <c:if test="${account_for_change.status == 'BANNED'}">
                                    <input type="hidden" name="command" value="unban_account">

                                    <button type="submit" class="btn-simple btn-green" name="account_id" value="${account_for_change.id}">
                                        <span class="icon icon-unlock">&nbsp;</span>
                                        <fmt:message key="button.account.unban"/>
                                    </button>
                                </c:if>
                            </c:if>
                        </form>
                    </div>

                    <div id="mr-down">
                        <div class="sub-title">
                            <span class="icon icon-circle icon-dodgerblue">&nbsp;</span>
                            <fmt:message key="account.title.editor.info"/>
                        </div>

                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="show_account_list_from_admin">

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user"></span>
                                        </div>
                                        <div class="input-block">
                                            <input type="text" id="account_first_name" name="account_first_name" value="${account_for_change.firstName}" disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="account_last_name" name="account_last_name" value="${account_for_change.lastName}" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="ad-row">
                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-user-circle-o"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="text" id="account_login" name="account_login" value="${account_for_change.login}" disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-email"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="email" value="${account_for_change.email}" disabled>
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
                                            <input type="text" value="${account_for_change.role}" disabled>
                                        </div>
                                    </div>
                                </div>

                                <div class="block">
                                    <div class="data">
                                        <div class="icon-block">
                                            <span class="icon icon-shield"></span>
                                        </div>

                                        <div class="input-block">
                                            <input type="email" value="${account_for_change.status}" disabled>
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
                                    <span class="icon icon-chevron-left">&nbsp;</span>
                                    <fmt:message key="button.label.back"/>
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