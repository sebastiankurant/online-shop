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
                    "   category_id INTEGER "+
                    ")");
        statement.execute("CREATE TABLE IF NOT EXISTS categories\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    description TEXT NOT NULL,\n" +
                "    department VARCHAR(255) NOT NULL\n" +
                ")");
    }
}
