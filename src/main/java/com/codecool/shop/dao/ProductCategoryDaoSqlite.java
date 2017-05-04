package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diana on 04.05.17.
 */
public class ProductCategoryDaoSqlite implements ProductCategoryDao {

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory p = null;
        try {
            Statement statement = SgliteJDSCConnector.makeConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from categories WHERE id = "
                    +Integer.toString(id));
            if(rs.next()) {
                p = new ProductCategory(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("department"));
                p.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return  p;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> productCategories = new ArrayList<>();

        try {
            Statement statement = SgliteJDSCConnector.makeConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from categories");
            while(rs.next()){
                ProductCategory category = new ProductCategory(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("department"));
                category.setId(rs.getInt("id"));
                productCategories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return  productCategories;
    }
}
