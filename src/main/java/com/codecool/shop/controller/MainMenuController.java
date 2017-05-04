package com.codecool.shop.controller;

import com.codecool.shop.view.UserInput;

import java.util.Scanner;

/**
 * Created by diana on 04.05.17.
 */
public class MainMenuController {
    public void mainMenuAction() {
        ProductController controller = new ProductController();

        System.out.println("select options");
        System.out.println("1. List all products.");
        System.out.println("2. List products by category");
        Integer input = UserInput.getUserInput("Select option:");
        switch (input){
            case 1:
                controller.listProducts();
                break;
            case 2:
                controller.listProductsByCategory();
                break;
            default:
                System.out.println("Unknown option. Try again.");

        }
    }
}
