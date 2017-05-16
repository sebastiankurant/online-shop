package com.codecool.shop.view;

import com.codecool.shop.model.BasketItem;

import java.util.List;

public class BasketView {
    public void displayBasketItems(List<BasketItem> items) {
        for(BasketItem item: items) {
            System.out.println(item);
        }
    }
}