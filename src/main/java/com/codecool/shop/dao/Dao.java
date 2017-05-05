package com.codecool.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Dao {
    private Connection conn = SgliteJDSCConnector.makeConnection();

    public ResultSet getResultSet(String query) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet getResultSet(String query, Integer param) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, param);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
