package com.codecool.shop.view;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

public class ProductView {
    public void displayProductList(List<Product> products) {
        for(Product product: products) {
            System.out.println(product.getName());
        }
    }

    public void displayCategories(List<ProductCategory> categories) {
        System.out.println("Categories: ");
        for (ProductCategory c: categories){
            System.out.println(c);
        }
    }

}
