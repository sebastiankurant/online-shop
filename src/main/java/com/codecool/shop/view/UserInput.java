package com.codecool.shop.view;

import java.util.Scanner;

/**
 * Created by diana on 04.05.17.
 */
public class UserInput {

    public static Integer getUserInput(String message){
        System.out.println(message);
        Scanner s = new Scanner(System.in);
        while(!s.hasNextInt()){
            System.out.println("Invalid input");
            s.next();
        }
        Integer input = s.nextInt();
        return input;
    }
}
