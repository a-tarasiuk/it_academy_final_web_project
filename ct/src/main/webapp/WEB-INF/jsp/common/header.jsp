<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="locale"/>
<jsp:useBean id="account" scope="session" class="by.tarasiuk.ct.model.entity.impl.Account"/>

<div id="header">
    <div id="inside-header">
        <span id="logo" class="icon-logo"></span>

        <div id="left-menu">
            <a class="menu-item" href="/"><fmt:message key="header.button.label.main"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.about"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.contact"/></a>
        </div>

        <div id="right-menu">
            <!-- Locale changer button -->
            <div class="dropdown-wrapper">
                <button class="dropdown-button button-colour-standard">
                    <span class="icon icon-globe">&nbsp;</span>
                    <fmt:message key="header.label.locale"/>&nbsp;&nbsp;
                    <span class="icon icon-caret-down"></span>
                </button>

                <div class="content-wrapper">
                    <form class="dropdown-content" action="controller" method="get">
                        <input type="hidden" name="command" value="change_locale_page">

                        <button type="submit" name="locale" value="ru_RU">
                            <fmt:message key="header.option.label.ruRU"/>
                        </button>

                        <button type="submit" name="locale" value="en_US">
                            <fmt:message key="header.option.label.enUS"/>
                        </button>
                    </form>
                </div>
            </div>

            <c:choose>
                <c:when test="${not empty account && account.role != 'GUEST'}">
                    <!-- Account profile button -->
                    <div class="dropdown-wrapper">
                        <button class="dropdown-button button-colour-standard">
                            <span class="icon icon-user-circle-o">&nbsp;</span>
                                ${account.firstName}&nbsp;&nbsp;
                            <span class="icon icon-caret-down"></span>
                        </button>

                        <div class="content-wrapper">
                            <form class="dropdown-content" action="controller" method="get">
                                <button type="submit" name="command" value="show_account_settings_page">
                                    <span class="icon icon-cog">&nbsp;</span>
                                    <fmt:message key="button.account.label.settings"/>
                                </button>

                                <button type="submit" name="command" value="logout">
                                    <span class="icon icon-sign-out">&nbsp;</span>
                                    <fmt:message key="button.account.label.signOut"/>
                                </button>
                            </form>
                        </div>
                    </div>

                    <c:if test="${account.role == 'MANAGER'}">
                        <!-- Company manager button -->
                        <div class="dropdown-wrapper">
                            <button class="dropdown-button button-colour-standard">
                                <span class="icon icon-building">&nbsp;</span>
                                <fmt:message key="header.button.label.company"/>&nbsp;&nbsp;
                                <span class="icon icon-caret-down"></span>
                            </button>

                            <div class="content-wrapper">
                                <form class="dropdown-content" action="controller" method="get">
                                    <button type="submit" name="command" value="show_company_settings_page">
                                        <span class="icon icon-cog">&nbsp;</span>
                                        <fmt:message key="header.button.label.companySettings"/>
                                    </button>

                                    <button type="submit" name="command" value="show_forwarder_settings_page">
                                        <span class="icon icon-users">&nbsp;</span>
                                        <fmt:message key="header.button.label.forwarder.settings"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${account.role == 'ADMINISTRATOR'}">
                        <!-- Company manager button -->
                        <div class="dropdown-wrapper">
                            <button class="dropdown-button button-colour-standard">
                                <span class="icon icon-shield">&nbsp;</span>
                                <fmt:message key="header.button.label.administration"/>&nbsp;&nbsp;
                                <span class="icon icon-caret-down"></span>
                            </button>

                            <div class="content-wrapper">
                                <form class="dropdown-content" action="controller" method="get">
                                    <button type="submit" name="command" value="show_account_list_from_admin">
                                        <span class="icon icon-users">&nbsp;</span>
                                        <fmt:message key="header.button.label.admin.accounts.editor"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:when>

                <c:otherwise>
                    <!-- Sign IN button -->
                    <a class="menu-button button-colour-standard" href="${pageContext.request.contextPath}/controller?command=go_to_sign_in_page">
                        <span class="icon icon-sign-in">&nbsp;</span>
                        <fmt:message key="form.signIn.title"/>
                    </a>

                    <!-- Sign UP button -->
                    <a class="menu-button button-colour-red" href="${pageContext.request.contextPath}/controller?command=go_to_sign_up_page">
                        <span class="icon icon-sign-in">&nbsp;</span>
                        <fmt:message key="form.signUp.title"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>