package com.codecool.shop.controller;

import com.codecool.shop.view.UserInput;

import java.util.Scanner;

/**
 * Created by diana on 04.05.17.
 */
public class MainMenuController {
    BasketController basket = new BasketController();
    ProductController controller = new ProductController();

    public void mainMenuAction() {
        System.out.println("select options");
        System.out.println("1. List all products.");
        System.out.println("2. List products by category");
        System.out.println("3. List products by supplier");
        System.out.println("4. Add product to basket");
        Integer input = UserInput.getUserInput("Select option:");
        switch (input){
            case 1:
                controller.listProducts();
                break;
            case 2:
                controller.listProductsByCategory();
                break;
            case 3:
                controller.listProductsBySupplier();
                break;
            case 4:
                basket.addToCardAction();
                break;
            default:
                System.out.println("Unknown option. Try again.");

        }
    }
}
