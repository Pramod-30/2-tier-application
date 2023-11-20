package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try (InputStream input = DbUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return null;
            }
            //load a properties file from class path, inside static method
            prop.load(input);

            // get the property value and print it out
            String jdbcUrl = prop.getProperty("jdbc.url");
            String jdbcUser = prop.getProperty("jdbc.user");
            String jdbcPassword = prop.getProperty("jdbc.password");

            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
