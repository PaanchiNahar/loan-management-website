package com.loan.management.utils;

import com.loan.management.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanEligibilityChecker {
    public static boolean checkEligibility(String loanType, double creditScore, double monthlyIncome, double monthlyDebt, int employmentMonths) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM loan_eligibility_criteria WHERE loan_type = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, loanType);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double minIncome = rs.getDouble("min_income");
                        int minCreditScore = rs.getInt("min_credit_score");
                        double maxDti = rs.getDouble("max_dti");
                        int minEmploymentMonths = rs.getInt("min_employment_months");

                        if (creditScore < minCreditScore) {
                            return false;
                        }

                        if (monthlyIncome < minIncome) {
                            return false;
                        }

                        if (employmentMonths < minEmploymentMonths) {
                            return false;
                        }

                        double debtToIncomeRatio = monthlyDebt / monthlyIncome;
                        if (debtToIncomeRatio > maxDti) {
                            return false;
                        }

                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static double calculateMaxLoanAmount(String loanType, double monthlyIncome, double monthlyDebt, double creditScore) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "SELECT max_loan_amount FROM loan_eligibility_criteria WHERE loan_type = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, loanType);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double maxLoanAmount = rs.getDouble("max_loan_amount");
                        double baseAmount = monthlyIncome * 12; // Annual income
                        double creditScoreMultiplier = creditScore / 850; // Normalize credit score
                        
                        return Math.min(maxLoanAmount, baseAmount * creditScoreMultiplier);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
} 