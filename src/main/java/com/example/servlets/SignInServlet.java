package com.example.servlets;

import com.example.model.User;
import com.example.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Valid user, create a session
                HttpSession session = request.getSession();
                session.setAttribute("username", user.getUsername());
                response.sendRedirect("welcome.jsp");
            } else {
                // Invalid user, redirect to sign-in page
                response.sendRedirect("signIn.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signIn.jsp"); // Redirect to sign-in page on error
        }
    }
}
