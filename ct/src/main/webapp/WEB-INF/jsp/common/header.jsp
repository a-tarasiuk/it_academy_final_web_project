<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/custom.tld" %>

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
            <ct:locale_button/>

            <c:choose>
                <c:when test="${empty account}">
                    <ct:header_buttons/>
                </c:when>

                <c:otherwise>
                    <div class="dropdown-wrapper">
                        <button class="dropdown-button button-colour-standard">
                            <span class="icon icon-user-circle-o">&nbsp;</span>
                                ${account.firstName}&nbsp;&nbsp;
                            <span class="icon icon-caret-down"></span>
                        </button>
                        <div class="content-wrapper">
                            <form class="dropdown-content" method="get" action="controller">
                               <button type="submit" name="command" value="en_US">
                                    <span class="icon icon-cog">&nbsp;</span>
                                    <fmt:message key="button.account.label.settings"/>
                                </button>

                                <button type="submit" name="command" value="logout">
                                    <span class="icon icon-sign-out">&nbsp;</span>
                                    <fmt:message key="button.account.label.logout"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>