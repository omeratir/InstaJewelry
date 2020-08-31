package com.example.instajewelry;

import androidx.lifecycle.ViewModel;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.JewelryModel;

public class JewelryViewModel extends ViewModel {

    public void add(Jewelry jewelry, JewelryModel.Listener listener) {
        JewelryModel.instance.addJewelry(jewelry, listener);
    }

    public void update(Jewelry jewelry, JewelryModel.Listener listener) {
        JewelryModel.instance.updateJewelry(jewelry, listener);
    }

    public void delete(Jewelry jewelry) {
        JewelryModel.instance.deleteJewelry(jewelry);
    }
}
