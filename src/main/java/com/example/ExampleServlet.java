package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/example")
public class ExampleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // JDBC connection to MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/twotierdb";
            String dbUser = "root";
            String dbPassword = "root";
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // Perform a simple query
            String sql = "SELECT * FROM example_table";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Display results in the web page
            out.println("<html><body>");
            while (resultSet.next()) {
                out.println("ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name") + "<br>");
            }
            out.println("</body></html>");

            // Close JDBC resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
