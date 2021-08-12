<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

<%--@elvariable id="locale" type="java.lang.String"--%>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${locale}" scope="session"/>

<div id="header">
    <div id="inside-header">
        <span id="logo" class="icon-logo"></span>

        <div id="left-menu">
            <a class="menu-item" href="/"><fmt:message key="header.button.label.main"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.about"/></a>
            <a class="menu-item" href="#"><fmt:message key="header.button.label.contact"/></a>
        </div>

        <div id="right-menu">
            <ct:locale_button/>
            <ct:account_buttons/>

            <a class="menu-button button-colour-standard" href="${pageContext.request.contextPath}/controller?command=go_to_account_editor_page"><span class="icon icon-users">&nbsp;</span>Employees</a>
        </div>
    </div>
</div>