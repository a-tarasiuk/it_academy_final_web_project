<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Error page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/http-status.css">
</head>

<body>
    <table>
        <tr>
            <td rowspan="3"><img src="${pageContext.request.contextPath}/img/robot-error.png" alt="error"></td>
            <td colspan="2">
                <span style="font-size: 1200%">${errorStatusCode}</span>
            </td>
        </tr>
        <tr>
            <td><b>Method:</b></td>
            <td>${errorNameMethod}</td>

        </tr>
        <tr>
            <td><b>Message:</b></td>
            <td>${errorMessage}</td>
        </tr>
    </table>
</body>
</html>