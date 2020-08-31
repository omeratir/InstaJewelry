package com.example.instajewelry;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.JewelryModel;

import java.util.LinkedList;
import java.util.List;

public class JewelryListViewModel extends ViewModel {
    LiveData<List<Jewelry>> liveData;

    public LiveData<List<Jewelry>> getData() {
        liveData = JewelryModel.instance.getAllJewelries();
        return liveData;
    }

    public void deleteJewelryVM(Jewelry jewelry) {
        JewelryModel.instance.deleteJewelry(jewelry);
    }

    public void updateJewelryVM(Jewelry jewelry) {
//        JewelryModel.instance.updateJewelry(jewelry);
    }

    public void refresh(JewelryModel.CompListener listener) {
        JewelryModel.instance.refreshJewelryList(listener);
    }
}
