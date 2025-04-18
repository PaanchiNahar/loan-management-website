package com.loan.management.services;

import com.loan.management.models.User;
import com.loan.management.dao.UserDAO;
import com.loan.management.utils.PasswordUtils;
import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(String username, String password, String fullName, String email, String phoneNumber) {
        try {
            // Check if username already exists
            if (userDAO.getUserByUsername(username) != null) {
                throw new RuntimeException("Username already exists");
            }

            // Generate salt and hash password
            String salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(password, salt);

            // Create new user
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);

            // Save user to database
            return userDAO.createUser(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    public User loginUser(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                throw new RuntimeException("Invalid username or password");
            }

            if (!PasswordUtils.verifyPassword(password, user.getSalt(), user.getPassword())) {
                throw new RuntimeException("Invalid username or password");
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error logging in", e);
        }
    }

    public User getUserByUsername(String username) {
        try {
            return userDAO.getUserByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
        }
    }

    public User createUser(User user) {
        try {
            String salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(user.getPassword(), salt);
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            return userDAO.createUser(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    public boolean verifyUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }
        return PasswordUtils.verifyPassword(password, user.getPassword(), user.getSalt());
    }
} 