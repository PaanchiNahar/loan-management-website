package com.loan.management.dao;

import com.loan.management.models.LoanApplication;
import com.loan.management.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private Connection connection;

    public LoanDAO() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LoanApplication> getAllApplications() {
        List<LoanApplication> applications = new ArrayList<>();
        String sql = "SELECT * FROM loan_applications";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                LoanApplication application = new LoanApplication();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                application.setFirstName(rs.getString("first_name"));
                application.setLastName(rs.getString("last_name"));
                application.setEmail(rs.getString("email"));
                application.setPhone(rs.getString("phone"));
                application.setLoanType(rs.getString("loan_type"));
                application.setAmount(rs.getDouble("amount"));
                application.setTenure(rs.getInt("tenure"));
                application.setPurpose(rs.getString("purpose"));
                application.setStatus(rs.getString("status"));
                application.setApplicationDate(rs.getTimestamp("application_date"));
                applications.add(application);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving loan applications", e);
        }
        
        return applications;
    }

    public LoanApplication getApplicationById(int id) {
        String sql = "SELECT * FROM loan_applications WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                LoanApplication application = new LoanApplication();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                application.setFirstName(rs.getString("first_name"));
                application.setLastName(rs.getString("last_name"));
                application.setEmail(rs.getString("email"));
                application.setPhone(rs.getString("phone"));
                application.setLoanType(rs.getString("loan_type"));
                application.setAmount(rs.getDouble("amount"));
                application.setTenure(rs.getInt("tenure"));
                application.setPurpose(rs.getString("purpose"));
                application.setStatus(rs.getString("status"));
                application.setApplicationDate(rs.getTimestamp("application_date"));
                return application;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving loan application", e);
        }
        
        return null;
    }

    public List<LoanApplication> getUserLoanApplications(int userId) {
        List<LoanApplication> applications = new ArrayList<>();
        String sql = "SELECT * FROM loan_applications WHERE user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                LoanApplication application = new LoanApplication();
                application.setId(rs.getInt("id"));
                application.setUserId(rs.getInt("user_id"));
                application.setFirstName(rs.getString("first_name"));
                application.setLastName(rs.getString("last_name"));
                application.setEmail(rs.getString("email"));
                application.setPhone(rs.getString("phone"));
                application.setLoanType(rs.getString("loan_type"));
                application.setAmount(rs.getDouble("amount"));
                application.setTenure(rs.getInt("tenure"));
                application.setPurpose(rs.getString("purpose"));
                application.setStatus(rs.getString("status"));
                application.setApplicationDate(rs.getTimestamp("application_date"));
                applications.add(application);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user's loan applications", e);
        }
        
        return applications;
    }

    public void createApplication(LoanApplication application) {
        String sql = "INSERT INTO loan_applications (user_id, first_name, last_name, email, phone, loan_type, amount, tenure, purpose, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, application.getUserId());
            pstmt.setString(2, application.getFirstName());
            pstmt.setString(3, application.getLastName());
            pstmt.setString(4, application.getEmail());
            pstmt.setString(5, application.getPhone());
            pstmt.setString(6, application.getLoanType());
            pstmt.setDouble(7, application.getAmount());
            pstmt.setInt(8, application.getTenure());
            pstmt.setString(9, application.getPurpose());
            pstmt.setString(10, application.getStatus());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    application.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating loan application", e);
        }
    }

    public void updateApplication(LoanApplication application) {
        String sql = "UPDATE loan_applications SET status = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, application.getStatus());
            pstmt.setInt(2, application.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating loan application", e);
        }
    }

    public void deleteApplication(int id) {
        String sql = "DELETE FROM loan_applications WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting loan application", e);
        }
    }

    public void createLoanApplication(LoanApplication application) throws SQLException {
        String sql = "INSERT INTO loan_applications (user_id, first_name, last_name, email, phone, loan_type, amount, tenure, purpose, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, application.getUserId());
            statement.setString(2, application.getFirstName());
            statement.setString(3, application.getLastName());
            statement.setString(4, application.getEmail());
            statement.setString(5, application.getPhone());
            statement.setString(6, application.getLoanType());
            statement.setDouble(7, application.getAmount());
            statement.setInt(8, application.getTenure());
            statement.setString(9, application.getPurpose());
            statement.setString(10, application.getStatus());
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    application.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<LoanApplication> getLoanApplicationsByUserId(int userId) throws SQLException {
        List<LoanApplication> applications = new ArrayList<>();
        String sql = "SELECT * FROM loan_applications WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    applications.add(mapResultSetToLoanApplication(resultSet));
                }
            }
        }
        return applications;
    }

    public LoanApplication getLoanApplicationById(int id) throws SQLException {
        String sql = "SELECT * FROM loan_applications WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToLoanApplication(resultSet);
                }
            }
        }
        return null;
    }

    public void updateLoanStatus(int id, String status) throws SQLException {
        String sql = "UPDATE loan_applications SET status = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    private LoanApplication mapResultSetToLoanApplication(ResultSet resultSet) throws SQLException {
        LoanApplication application = new LoanApplication();
        application.setId(resultSet.getInt("id"));
        application.setUserId(resultSet.getInt("user_id"));
        application.setFirstName(resultSet.getString("first_name"));
        application.setLastName(resultSet.getString("last_name"));
        application.setEmail(resultSet.getString("email"));
        application.setPhone(resultSet.getString("phone"));
        application.setLoanType(resultSet.getString("loan_type"));
        application.setAmount(resultSet.getDouble("amount"));
        application.setTenure(resultSet.getInt("tenure"));
        application.setPurpose(resultSet.getString("purpose"));
        application.setStatus(resultSet.getString("status"));
        return application;
    }
} 