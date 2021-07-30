<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${locale}" scope="session"/>
<c:set var="path">${pageContext.request.contextPath}</c:set>

<div id="header">
    <div id="inside-header">
        <span id="logo" class="icon-logo"></span>

        <div id="left-menu">
            <a class="menu-item" href="/"><fmt:message key="header.button.label.main"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.about"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.contact"/></a>
        </div>

        <div id="right-menu">
            <div class="dropdown-wrapper">
                <button class="dropdown-button button-colour-standard">
                    <span class="icon icon-globe">&nbsp;</span>
                    <fmt:message key="header.label.locale"/>&nbsp;&nbsp;
                    <span class="icon icon-caret-down"></span>
                </button>
                <div class="content-wrapper">
                    <form class="dropdown-content" method="post" action="controller">
                        <input type="hidden" name="command" value="change_locale_page">
                        <button type="submit" name="locale" value="ru_RU"><fmt:message key="header.option.label.ruRU"/></button>
                        <button type="submit" name="locale" value="en_US"><fmt:message key="header.option.label.enUS"/></button>
                    </form>
                </div>
            </div>

            <a class="menu-button button-colour-standard" href="${path}/controller?command=go_to_sign_in_page"><span class="icon icon-sign-in">&nbsp;</span><fmt:message key="header.button.label.signIn"/></a>
            <a class="menu-button button-colour-red" href="${path}/controller?command=go_to_sign_up_page"><span class="icon icon-white icon-sign-up"></span>&nbsp;<fmt:message key="header.button.label.signUp"/></a>
        </div>
    </div>
</div>