package com.codecool.shop.dao;


import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monika on 04.05.17.
 */


public class SupplierDaoSqlite implements SupplierDao {
    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from supplier where id = " + Integer.toString(id));

            if(rs.next()) {
                supplier = new Supplier(
                        rs.getString("name"),
                        rs.getString("description")
                );
                supplier.setId(rs.getInt("id"));
            }

        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }

        return supplier;
    }



    @Override
    public List<Supplier> getAll() {
        List<Supplier> listSupplier = new ArrayList<>();

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from supplier");
            while(rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getString("name"),
                        rs.getString("description")
                );
                listSupplier.add(supplier);
            }
        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }


        return listSupplier;
    }

}

