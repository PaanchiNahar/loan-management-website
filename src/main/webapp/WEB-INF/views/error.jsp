<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .error-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #d9534f;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #337ab7;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Error ${requestScope['javax.servlet.error.status_code']}</h1>
        <p>${requestScope['javax.servlet.error.message']}</p>
        <a href="${pageContext.request.contextPath}/" class="btn">Go to Home</a>
    </div>
</body>
</html> 