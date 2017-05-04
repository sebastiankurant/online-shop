package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductCategoryDaoSqlite;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ProductDaoSqlite;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.view.ProductView;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    ProductView view = new ProductView();

    public void listProducts(){
        ProductDao productDao = new ProductDaoSqlite();
        view.displayProductList(productDao.getAll());
    }

    public void listProductsByCategory() {
        ProductCategoryDaoSqlite categoryDaoSqlite = new ProductCategoryDaoSqlite();
        view.displayCategories(categoryDaoSqlite.getAll());
    }
}
