package codecool_shop.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


class Dao {

    protected Connection connection;

    ResultSet executeStatement(String query, Map<Integer, String> parameters) throws SQLException {
        ResultSet resultSet;
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        for (Integer index : parameters.keySet()) {
            statement.setString(index, parameters.get(index));
        }
        resultSet = statement.executeQuery();
        return resultSet;
    }

    ResultSet executeStatement(String query) throws SQLException {
        ResultSet resultSet;
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        return resultSet;
    }

    void executeStatementUpdate(String query, Map<Integer, String> parameters) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        for (Integer index : parameters.keySet()) {
            statement.setString(index, parameters.get(index));
        }
        statement.executeUpdate();
        statement.close();
    }
}
