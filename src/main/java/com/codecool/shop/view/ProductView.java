package com.codecool.shop.view;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;


import java.util.List;
import java.util.Locale;

public class ProductView {

    public void displayProductsList(List<Product> products) {
        for(Product product: products) {
            System.out.println(product.getName());
        }
    }

    public void displayCategoriesList(List<ProductCategory> categories) {
        for (ProductCategory productCategory: categories) {
            System.out.println(productCategory.getName());
        }
    }

    public void displaySuppliersList(List<Supplier> suppliers) {
        for (Supplier supplier: suppliers) {
            System.out.println(supplier.getName());
        }
    }

}
