package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoSqlite extends Dao implements ProductDao {
    private ProductCategoryDao categoryDao = new ProductCategoryDaoSqlite();
    private SupplierDao supplierDao = new SupplierDaoSqlite();

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
    public List<Product> getAll() throws SQLException {
            ResultSet rs = getResultSet("SELECT * from products");
            List<Product> products = createProductList(rs);
        return  products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) throws SQLException {
        ResultSet rs = getResultSet("SELECT * from products where supplier_id =?",
                    supplier.getId());
        List<Product> products = createProductList(rs);
        return  products;

    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) throws SQLException {
        ResultSet rs = getResultSet("SELECT * from products where category_id = ?",
                productCategory.getId());
        List<Product> products = createProductList(rs);
        return  products;
    }

    private List<Product> createProductList(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while(rs.next()){
            Product product = new Product(
                    rs.getString("name"),
                    rs.getFloat("price"),
                    "PLN",
                    rs.getString("description"),
                    categoryDao.find(rs.getInt("category_id")),
                    supplierDao.find(rs.getInt("supplier_id")));
            product.setId(rs.getInt("id"));
            products.add(product);
        }
        return products;
    }
}
