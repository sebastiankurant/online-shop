package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monika on 04.05.17.
 */
public class ProductCategoryDaoSqlite implements ProductCategoryDao {
    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory category = null;

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from categories where id = " + Integer.toString(id));

            if(rs.next()) {
                category = new ProductCategory(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("departament")
                );
                category.setId(rs.getInt("id"));
            }

        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }

        return category;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> listProductCategory = new ArrayList<>();
//        ProductCategory productCategory = new ProductCategory("category1", "department1", "blah blah blah");
//        listProductCategory.add(productCategory);

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from categories");
            while(rs.next()) {
                ProductCategory productCategory = new ProductCategory(
                        rs.getString("name"),
                        rs.getString("departament"),
                        rs.getString("description")
                );
                listProductCategory.add(productCategory);
            }
        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }


        return listProductCategory;
    }

}
