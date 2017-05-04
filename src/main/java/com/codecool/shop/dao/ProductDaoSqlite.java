package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoSqlite implements ProductDao {
    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        Supplier supplier = new Supplier("dupa", "description");

        try {
            Statement statement = SgliteJDSCConnector.makeConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from products");
            while(rs.next()){
                Product product = new Product(
                        rs.getString("name"),
                        rs.getFloat("price"),
                        "PLN",
                        rs.getString("description"),
                        new ProductCategoryDaoSqlite().find(rs.getInt("category_id")),
                        supplier);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return  products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> products = new ArrayList<>();
        Supplier supplier = new Supplier("dupa", "description");

        try {
            Statement statement = SgliteJDSCConnector.makeConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from products where category_id = "+
                    productCategory.getId());
            while(rs.next()){
                Product product = new Product(
                        rs.getString("name"),
                        rs.getFloat("price"),
                        "PLN",
                        rs.getString("description"),
                        productCategory,
                        supplier);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return  products;
    }
}
