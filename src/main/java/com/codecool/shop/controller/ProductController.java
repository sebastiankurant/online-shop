package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductCategoryDaoSqlite;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ProductDaoSqlite;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.view.ProductView;
import com.codecool.shop.view.UserInput;

import java.util.List;


public class ProductController {
    private ProductView view = new ProductView();
    private ProductDao productDao = new ProductDaoSqlite();
    private ProductCategoryDao categoryDao = new ProductCategoryDaoSqlite();

    public void listProducts(){
        view.displayProductList(productDao.getAll());
    }

    public void listProductsByCategory() {
        view.displayCategories(categoryDao.getAll());
        Integer categoryId = UserInput.getUserInput("Select category: ");
        ProductCategory c = categoryDao.find(categoryId);
        List<Product> products = productDao.getBy(c);
        view.displayProductList(products);
    }
}
