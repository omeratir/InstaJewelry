package com.example.instajewelry.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Jewelry implements Serializable {
    @PrimaryKey
    @NonNull
    public String  id;
    public String name;
    public String type; // silver , gold , rose gold
    public String cost;
    public Boolean isSold; // true is sold and false if not
    public String imageUrl;
//    double lastUpdated;

    public Jewelry(String id, String name, String type, String cost, Boolean isSold, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.isSold = isSold;
        this.imageUrl = imageUrl;
    }

//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("id", id);
//        result.put("name", name);
//        result.put("type", type);
//        result.put("cost", cost);
//        result.put("isSold", isSold);
//        result.put("imageUrl", imageUrl);
//        result.put("lastUpdated", FieldValue.serverTimestamp());
//        return result;
//    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
