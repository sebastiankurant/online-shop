package com.codecool.shop.view;

import java.util.Scanner;

/**
 * Created by monika on 04.05.17.
 */
public class UserInput {
    public static Integer getUserInput() {
        Scanner userInput = new Scanner(System.in);
        while (!userInput.hasNextInt()) {
            userInput.next();
        }
        return userInput.nextInt();
    }
}
