package com.example.instajewelry.Model;

import java.util.Currency;

public class Jewelry {
    String id;
    String name;
    Integer price; // price
    String type; // silver , gold , rose gold
    Boolean isSold; // true is sold and false if not
    String imageUrl;

    public Jewelry(String id, String name, Integer price, String type, Boolean isSold, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.isSold = isSold;
        this.imageUrl = imageUrl;
    }
}
