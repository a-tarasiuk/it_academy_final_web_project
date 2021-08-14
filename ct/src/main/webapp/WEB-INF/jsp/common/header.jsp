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
            <ct:header_buttons/>

            <div class="dropdown-wrapper">
                <button class="dropdown-button button-colour-standard">
                    <span class="icon icon-building">&nbsp;</span>
                    <fmt:message key="header.button.label.company"/>&nbsp;&nbsp;
                    <span class="icon icon-caret-down"></span>
                </button>

                <div class="content-wrapper">
                    <form class="dropdown-content" action="controller" method="get">
                        <button type="submit" name="command" value="go_to_account_editor_page">
                            <span class="icon icon-cog">&nbsp;</span>
                            <fmt:message key="header.button.label.companySettings"/>
                        </button>

                        <button type="submit" name="command" value="">
                            <span class="icon icon-users">&nbsp;</span>
                            <fmt:message key="header.button.label.employeesSettings"/>
                        </button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>