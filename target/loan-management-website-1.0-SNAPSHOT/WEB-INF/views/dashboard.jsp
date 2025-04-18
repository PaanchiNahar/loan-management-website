<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Loan Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #333;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }
        .welcome-message {
            margin-bottom: 2rem;
        }
        .card {
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 1.5rem;
            margin-bottom: 1rem;
        }
        .btn {
            display: inline-block;
            padding: 0.5rem 1rem;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-danger {
            background-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .loan-list {
            margin-top: 2rem;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        th, td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
    <div class="header">
        <h2>Loan Management System</h2>
        <div>
            <span>Welcome, ${sessionScope.user.username}</span>
            <a href="logout" class="btn btn-danger" style="margin-left: 1rem;">Logout</a>
        </div>
    </div>

    <div class="container">
        <div class="welcome-message">
            <h3>Welcome to your Dashboard</h3>
            <p>Manage your loans and applications here.</p>
        </div>

        <div class="card">
            <h3>Quick Actions</h3>
            <a href="apply-loan" class="btn">Apply for a New Loan</a>
        </div>

        <div class="loan-list">
            <h3>Your Loan Applications</h3>
            <table>
                <thead>
                    <tr>
                        <th>Loan ID</th>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${loans}" var="loan">
                        <tr>
                            <td>${loan.id}</td>
                            <td>${loan.loanType}</td>
                            <td>$${loan.amount}</td>
                            <td>${loan.status}</td>
                            <td>${loan.applicationDate}</td>
                            <td>
                                <a href="view-loan?id=${loan.id}" class="btn">View</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html> 