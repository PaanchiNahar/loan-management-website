package com.loan.management.controllers;

import com.loan.management.models.LoanApplication;
import com.loan.management.models.User;
import com.loan.management.services.LoanService;
import com.loan.management.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
    private LoanService loanService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        loanService = new LoanService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = userService.getUserByUsername(username);
        if (user != null) {
            try {
                List<LoanApplication> applications = loanService.getLoanApplicationsByUserId(user.getId());
                request.setAttribute("applications", applications);
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", "Error retrieving loan applications: " + e.getMessage());
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("new".equals(action)) {
            request.getRequestDispatcher("/loan-application.jsp").forward(request, response);
        } else if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("login.jsp");
        }
    }
} 