package com.example.instajewelry.Model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();
    List<Jewelry> jewelryList = new LinkedList<>();

    private Model() {
        for (int i=0 ; i<10; i++) {
            jewelryList.add(new Jewelry(""+i,"name " + i,"type " + i, false, null));
        }

    }

    public List<Jewelry> getAllJewelry() {
        return jewelryList;
    }

    public Jewelry getJewelry(String id) {
        return null;
    }

    public void updateJewelry(String id) {

    }

    public void deleteJewelry(String id) {

    }
}
