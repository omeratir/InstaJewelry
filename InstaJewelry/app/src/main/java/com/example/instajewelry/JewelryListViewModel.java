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
    List<Jewelry> list;

    public LiveData<List<Jewelry>> getData() {
        Log.d("TAG" , "get data - view model");
        if (liveData == null) {
            Log.d("TAG" , "live data at view model = null");
            liveData = JewelryModel.instance.getAllJewelries();
        }
        return liveData;
    }

    public List<Jewelry> getDataList() {
         JewelryModel.instance.getAllJewelry(new JewelryModel.Listener<List<Jewelry>>() {
                @Override
                public void onComplete(List<Jewelry> data) {
                    list = data;
                }
            });
        return list;
    }

    public void refresh(JewelryModel.CompListener listener) {
        JewelryModel.instance.refreshJewelryList(listener);
    }
}
