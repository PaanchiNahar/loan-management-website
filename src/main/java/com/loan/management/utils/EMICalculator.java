package com.loan.management.utils;

public class EMICalculator {
    public static double calculateEMI(double principal, double annualInterestRate, int tenureInMonths) {
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        return (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureInMonths)) 
               / (Math.pow(1 + monthlyInterestRate, tenureInMonths) - 1);
    }

    public static double calculateTotalInterest(double principal, double emi, int tenureInMonths) {
        return (emi * tenureInMonths) - principal;
    }

    public static double calculateTotalPayment(double principal, double emi, int tenureInMonths) {
        return emi * tenureInMonths;
    }
} 