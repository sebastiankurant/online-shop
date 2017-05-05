package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoSqlite implements SupplierDao{

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        try {
            Statement statement = SgliteJDSCConnector.makeConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from suppliers WHERE id = "
                    +Integer.toString(id));
            if(rs.next()) {
                supplier = new Supplier(
                        rs.getString("name"),
                        rs.getString("description"));
                supplier.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return  supplier;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            Statement statement = SgliteJDSCConnector.makeConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from suppliers");
            while(rs.next()){
                Supplier supplier = new Supplier(
                        rs.getString("name"),
                        rs.getString("description"));
                supplier.setId(rs.getInt("id"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return  suppliers;
    }
}
