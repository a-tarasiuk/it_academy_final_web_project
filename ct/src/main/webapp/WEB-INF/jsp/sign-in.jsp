<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../static/css/font.css">
    <link rel="stylesheet" href="../../static/css/sign_old.css">
    <link rel="stylesheet" href="../../static/css/font-awesome.min.css">
    <title>Sign up</title>
</head>
<body>
<div id="container">
    <div id="main">
        <div class="line">
            <div id="window-name">Enter the account to enjoy all the features Cargo Trading!</div>
        </div>

        <form action="controller" method="post">
            <div class="line">
                <div class="top-floor">
                    <div class="block-for-icon icon icon-user"></div>
                    <div class="block-for-input">
                        <input type="text" name="user_login" placeholder="Login">
                    </div>
                </div>

                <div class="ground-floor">
                    <div class="message">You can use letters and numbers. Minimum number of characters - 6, maximum - 30.</div>
                </div>
            </div>

            <div class="line">
                <div class="top-floor">
                    <div class="block-for-icon icon icon-lock"></div>
                    <div class="block-for-input">
                        <input type="password" name="user_password" placeholder="Password">
                    </div>
                </div>

                <div class="ground-floor">
                    <div class="message">You can use any characters. Minimum number of characters - 8, maximum - 255.</div>
                </div>
            </div>

            <div class="line warn">
                ${incorrect_login_or_password_message}
            </div>

            <div class="line">
                <div class="top-floor position-space-between">
                    <button class="button-back" name="command" value="go_to_main_page">Main page</button>
                    <button class="button-confirm" name="command" value="sign_in">Confirm</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="../../static/js/password.js"></script>
</body>
</html>