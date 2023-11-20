package com.example.servlets;

import com.example.model.User;
import com.example.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signUp.jsp"); // Redirect to sign-up page on error
        }
    }
}
