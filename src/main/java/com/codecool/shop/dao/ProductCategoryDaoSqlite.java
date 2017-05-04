package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

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
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> productCategories = new ArrayList<>();
        for (int i = 0; i <=3 ; i++) {
            String name = "Category"+1;
            ProductCategory p = new ProductCategory(name, "jh", "jhjh");
            productCategories.add(p);
        }
        return  productCategories;
    }
}
