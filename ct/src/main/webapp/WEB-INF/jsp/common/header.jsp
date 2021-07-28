<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="locale" scope="session"/>
<fmt:setLocale value="${sessionScope.locale_page}"/>

<div id="header">
    <form id="inside-header" action="controller" method="post">
        <span id="logo" class="icon-logo"></span>

        <div id="left-menu">
            <a class="menu-item" href="/"><fmt:message key="header.button.label.main"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.about"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.contact"/></a>
        </div>

        <div id="right-menu">
            <button class="menu-button button-colour-standard" name="command" value="change_locale_page"><span class="icon icon-globe">&nbsp;</span><fmt:message key="header.label.locale"/></button>
            <button class="menu-button button-colour-standard" name="command" value="go_to_sign_in_page"><span class="icon icon-sign-in">&nbsp;</span><fmt:message key="header.button.label.signIn"/></button>
            <button class="menu-button button-colour-red" name="command" value="go_to_sign_up_page"><span class="icon icon-white icon-sign-up"></span>&nbsp;<fmt:message key="header.button.label.signUp"/></button>
        </div>
    </form>
</div>