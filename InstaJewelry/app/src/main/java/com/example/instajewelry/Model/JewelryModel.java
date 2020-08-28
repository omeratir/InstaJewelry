package com.example.instajewelry.Model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
//        for (int i=0;i<10;i++){
//            Jewelry jewelry = new Jewelry(""+i,"name"+1,"type ++ " + i,"" + i*2, true, null);
//            addJewelry(jewelry,null);
//        }
    }

    public void getAllJewelriesLocal(final Listener<List<Jewelry>> listener){
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, String, List<Jewelry>> taskA = new AsyncTask<String, String, List<Jewelry>>(){
            @Override
            protected List<Jewelry> doInBackground(String... strings) {
                for (int i = 0 ; i < 20 ; i++) {
                    Log.d("TAG", "INSERT");
                    AppLocalDb.db.jewelryDao().insertAll(new Jewelry(""+i,"name"+i,"type of " + i,"" + i*2, true, null));
                }
                return AppLocalDb.db.jewelryDao().getAllList();
            }
            @Override
            protected void onPostExecute(List<Jewelry> jewelries) {
                super.onPostExecute(jewelries);
                listener.onComplete(jewelries);
            }
        };
        taskA.execute();
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
                        Log.d("TAG" , "do in background refresh");
                        for(Jewelry jewelry : data){
                            AppLocalDb.db.jewelryDao().insertAll(jewelry);
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

    public LiveData<List<Jewelry>> getAllJewelries(){
        Log.d("TAG" , "get all");
        LiveData<List<Jewelry>> liveData = null;
        liveData = AppLocalDb.db.jewelryDao().getAll();
        refreshJewelryList(null);
        Log.d("TAG" , "get all from app local db");
        if (liveData == null) {
            Log.d("TAG", "live data = null ");
        }

        return liveData;
//        return null;
    }


    public Jewelry getStudent(String id){
        return null;
    }

    public void update(Jewelry student){

    }

    public void delete(Jewelry student){

    }
}
