package com.example.instajewelry.Model;

import java.io.Serializable;
import java.util.Currency;

public class Jewelry implements Serializable {
    public String id;
    public String name;
    public String type; // silver , gold , rose gold
    public Boolean isSold; // true is sold and false if not
    public String imageUrl;

    public Jewelry(String id, String name, String type, Boolean isSold, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isSold = isSold;
        this.imageUrl = imageUrl;
    }
}
