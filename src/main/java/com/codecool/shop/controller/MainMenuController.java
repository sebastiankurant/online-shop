package com.codecool.shop.controller;

import java.util.Scanner;

/**
 * Created by diana on 04.05.17.
 */
public class MainMenuController {
    public void mainMenuAction() {
        ProductController controller = new ProductController();

        Scanner s = new Scanner(System.in);
        System.out.println("select options");
        System.out.println("1. List all products.");
        System.out.println("2. List products by category");
        System.out.println("Select option:");
        while(!s.hasNextInt()){
            System.out.println("Invalid input");
            s.next();
        }
        Integer input = s.nextInt();
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
