<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 8/24/20
  Time: 3:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/utils/error.css">
    <script src="fontawesome-free-5.12.1-web/js/all.js" data-auto-a11y="true"></script>
</head>
<body>
<div class="error_container">
    <div class="content">
        <div class="title">
            <p>OOPS </p>
        </div>

        <div class="message">
            <p>Error 404: Page not found</p>
        </div>

        <div class="btn_back" >
            <button onclick="history.back()"> GO BACK</button>
        </div>
    </div>
</div>
</body>
</html>