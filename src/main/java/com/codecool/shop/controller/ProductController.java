package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ProductDaoSqlite;
import com.codecool.shop.view.ProductView;

public class ProductController {

    public void listProducts(){
        ProductDao productDao = new ProductDaoSqlite();
        ProductView view = new ProductView();
        view.displayProductList(productDao.getAll());
    }
}
