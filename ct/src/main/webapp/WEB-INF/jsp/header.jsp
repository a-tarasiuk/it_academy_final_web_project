<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="header-block">
    <form id="menu" action="controller" method="post">
        <div id="logo-menu">
            <span class="icon-logo"></span>
        </div>

        <div id="left-menu">
            <a class="menu-item" href="/">Main</a>
            <a class="menu-item" href="#">About</a>
            <a class="menu-item" href="#">Contact</a>
        </div>

        <div id="right-menu">
            <button class="language"><span class="icon icon-globe"></span>Language</button>
            <button class="menu-item__left" name="command" value="go_to_sign_in_page"><span class="icon icon-sign-in"></span>Sign In</button>
            <button class="menu-item__right" name="command" value="go_to_sign_up_page"><span class="icon icon-user-plus"></span>Sign Up</button>
        </div>
    </form>
</div>