package com.codecool.shop.controller;

import com.codecool.shop.view.UserInput;

import java.util.Scanner;

public class MainMenuController {
    ProductController productController = new ProductController();
    BasketController basketController = new BasketController();

    public void mainMenuAction() {
        System.out.println("1. List all products");
        System.out.println("2. List products by Category");
        System.out.println("3. List products by Supplier");
        System.out.println("4. Add to cart");
        System.out.print("Select option: ");

        Integer option = UserInput.getUserInput();
        switch (option) {
            case 1:
                this.productController.listProducts();
                break;
            case 2:
                this.productController.listProductsByCategory();
                break;
            case 3:
                this.productController.listProductsBySupplier();
                break;
            case 4:
                this.basketController.addToCartAction();
                break;
            default:
                System.out.println("Option not found");
        }
    }
}
