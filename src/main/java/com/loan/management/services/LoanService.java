package com.loan.management.services;

import com.loan.management.models.LoanApplication;
import com.loan.management.dao.LoanDAO;
import com.loan.management.utils.LoanEligibilityChecker;
import java.sql.SQLException;
import java.util.List;

public class LoanService {
    private LoanDAO loanDAO;

    public LoanService() {
        this.loanDAO = new LoanDAO();
    }

    public void createLoanApplication(LoanApplication application) throws SQLException {
        loanDAO.createLoanApplication(application);
    }

    public List<LoanApplication> getLoanApplicationsByUserId(int userId) throws SQLException {
        return loanDAO.getLoanApplicationsByUserId(userId);
    }

    public LoanApplication getLoanApplicationById(int id) throws SQLException {
        return loanDAO.getLoanApplicationById(id);
    }

    public void updateLoanStatus(int id, String status) throws SQLException {
        loanDAO.updateLoanStatus(id, status);
    }

    private double calculateInterestRate(int creditScore) {
        // Base interest rate
        double baseRate = 8.0;

        // Adjust based on credit score
        if (creditScore >= 800) {
            return baseRate - 2.0;
        } else if (creditScore >= 700) {
            return baseRate - 1.0;
        } else if (creditScore >= 600) {
            return baseRate;
        } else {
            return baseRate + 2.0;
        }
    }
} 