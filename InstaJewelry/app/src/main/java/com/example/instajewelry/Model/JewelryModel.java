package com.example.instajewelry.Model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseAppLifecycleListener;

import java.util.LinkedList;
import java.util.List;

public class JewelryModel {
    public static final JewelryModel instance = new JewelryModel();

    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }
    public JewelryModel(){

    }

    public void addJewelry(Jewelry jewelry,Listener<Boolean> listener) {
        JewelryFirebase.addJewelry(jewelry,listener);
    }

    public void refreshJewelryList(final CompListener listener){
        JewelryFirebase.getAllJewelries(new Listener<List<Jewelry>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            // Thread
            public void onComplete(final List<Jewelry> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        Log.d("TAG" , "Backgroud");
                        for(Jewelry jewelry : data) {
                            if (!jewelry.isDeleted()) {
                                Log.d("TAG" , "Insert = " + jewelry.name);
                                AppLocalDb.db.jewelryDao().insertAll(jewelry);
                            }
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (listener != null)  {
                            listener.onComplete();
                        }
                    }
                }.execute("");
            }
        });
    }

    public void getAllJewelry(final  Listener<List<Jewelry>> listener) {
        JewelryFirebase.getAllJewelries(listener);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteJewelry(final Jewelry jewelry) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                    AppLocalDb.db.jewelryDao().deleteJewelry(jewelry);
                return "";
            }
        }.execute("");
        JewelryFirebase.deleteJewelry(jewelry);
        refreshJewelryList(null);
    }

    public void updateJewelry(Jewelry jewelry, Listener<Boolean> listener) {
        JewelryFirebase.updateJewelry(jewelry,listener);
        refreshJewelryList(null);
    }

    public LiveData<List<Jewelry>> getAllJewelries(){
        LiveData<List<Jewelry>> liveData = null;
        liveData = AppLocalDb.db.jewelryDao().getAll();
        refreshJewelryList(null);
        return liveData;
    }



}
