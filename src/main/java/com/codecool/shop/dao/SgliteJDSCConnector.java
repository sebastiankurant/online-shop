package com.codecool.shop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by diana on 02.05.17.
 */
public class SgliteJDSCConnector {

    public static Connection makeConnection(){
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(
                    "jdbc:sqlite:src/main/resources/database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void createTables() throws SQLException {
            Statement statement = makeConnection().createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS products\n" +
                    "(\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description TEXT,\n" +
                    "    price DOUBLE DEFAULT 0\n" +
                    ")");
    }


    public static void makeSeeds(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:sqlite:src/main/resources/database.db");
            Statement statement = connection.createStatement();
            statement.execute("INSERT Into products (name, description, " +
                    "price) VALUES (\" dupa\", \"gbvgh\", 3434)");
            statement.execute("INSERT Into products (name, description, " +
                    "price) VALUES (\" dupa1\", \"gbvgh\", 3434)");
            statement.execute("INSERT Into products (name, description, " +
                    "price) VALUES (\" dupa2\", \"gbvgh\", 3434)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
