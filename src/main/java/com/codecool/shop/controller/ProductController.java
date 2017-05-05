package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductCategoryDaoSqlite;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ProductDaoSqlite;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.SupplierDaoSqlite;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.view.ProductView;
import com.codecool.shop.view.UserInput;

import java.sql.SQLException;
import java.util.List;


public class ProductController {
    private ProductView view = new ProductView();
    private ProductDao productDao = new ProductDaoSqlite();
    private ProductCategoryDao categoryDao = new ProductCategoryDaoSqlite();
    private SupplierDao supplierDao = new SupplierDaoSqlite();

    public void listProducts(){
        try {
            view.displayProductList(productDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listProductsByCategory() {
        view.displayCategories(categoryDao.getAll());
        Integer categoryId = UserInput.getUserInput("Select category: ");
        ProductCategory c = categoryDao.find(categoryId);
        List<Product> products = null;
        try {
            products = productDao.getBy(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        view.displayProductList(products);
    }

    public void listProductsBySupplier() {
        view.displaySuppliers(supplierDao.getAll());
        Integer supplierId = UserInput.getUserInput("Select supplier: ");
        Supplier s = supplierDao.find(supplierId);
        List<Product> products = null;
        try {
            products = productDao.getBy(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        view.displayProductList(products);

    }
}
