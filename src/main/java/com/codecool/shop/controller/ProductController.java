package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDaoSqlite;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ProductDaoSqlite;
import com.codecool.shop.view.ProductView;

public class ProductController {
    private ProductView view = new ProductView();

    public void listProducts(){
        ProductDao productDao = new ProductDaoSqlite();
        view.displayProductList(productDao.getAll());
    }

    public void listProductsByCategory() {
        ProductCategoryDaoSqlite categoryDaoSqlite = new ProductCategoryDaoSqlite();
        view.displayCategories(categoryDaoSqlite.getAll());
    }
}
