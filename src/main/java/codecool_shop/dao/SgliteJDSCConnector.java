package codecool_shop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by pgurdek on 09.05.17.
 */
public class SgliteJDSCConnector {

    public void createTables() throws SQLException {

        Connection connection = connection();
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS product\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    description TEXT,\n" +
                "    product_date TEXT \n" +
                ")");

        statement.execute("CREATE TABLE IF NOT EXISTS product_category\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    description TEXT,\n" +
                "    slug VARCHAR(2355) NOT NULL" +
                ")");

        statement.execute("CREATE TABLE IF NOT EXISTS product_meta\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    post_id INTEGER NOT NULL,\n" +
                "    category_id INTEGER NOT NULL,\n" +
                "    FOREIGN KEY (post_id) REFERENCES product (id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "    FOREIGN KEY (category_id) REFERENCES product_category (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ")");

        statement.execute("CREATE TABLE IF NOT EXISTS users\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT NOT NULL,\n" +
                "    firstname TEXT ,\n" +
                "    lastname TEXT,\n" +
                "    password TEXT NOT NULL,\n" +
                "    type TEXT NOT NULL,\n" +
                "    UNIQUE(username)" +
                ")");

        statement.execute("INSERT OR IGNORE INTO  users (username,firstname,lastname,password,type) VALUES ('admin','admin','gurdek','admin','admin')");
        statement.execute("INSERT OR IGNORE INTO  users (username,firstname,lastname,password,type) VALUES ('customer','monika','plocica','dupa','customer')");

        statement.close();
        connection.close();
    }

    public Connection connection() {
        Connection connection = null;
        try {
            Properties properties = new Properties();
            properties.setProperty("PRAGMA foreign_keys", "ON");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:src/main/resources/database.db", properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

