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

@WebServlet("/loan-application")
public class LoanApplicationController extends HttpServlet {
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
        request.getRequestDispatcher("/loan-application.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = userService.getUserByUsername(username);
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            LoanApplication application = new LoanApplication();
            application.setUserId(user.getId());
            application.setFirstName(request.getParameter("firstName"));
            application.setLastName(request.getParameter("lastName"));
            application.setEmail(request.getParameter("email"));
            application.setPhone(request.getParameter("phone"));
            application.setLoanType(request.getParameter("loanType"));
            application.setAmount(Double.parseDouble(request.getParameter("amount")));
            application.setTenure(Integer.parseInt(request.getParameter("tenure")));
            application.setPurpose(request.getParameter("purpose"));
            application.setStatus("PENDING");

            loanService.createLoanApplication(application);
            response.sendRedirect("dashboard");
        } catch (Exception e) {
            request.setAttribute("error", "Error submitting loan application: " + e.getMessage());
            request.getRequestDispatcher("/loan-application.jsp").forward(request, response);
        }
    }
} 