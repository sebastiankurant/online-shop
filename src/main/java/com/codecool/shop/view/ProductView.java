package com.codecool.shop.view;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

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

    public void displaySuppliers(List<Supplier> suppliers) {
        System.out.println("Suppliers: ");
        for (Supplier c: suppliers){
            System.out.println(c);
        }
    }
}
