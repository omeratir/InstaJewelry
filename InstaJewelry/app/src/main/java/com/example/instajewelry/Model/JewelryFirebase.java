package com.example.instajewelry.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class JewelryFirebase {
    final static String JEWELRY_COLLECTION = "jewelries";

    List<Jewelry> jewelries;

    public static void getAllJewelries(final JewelryModel.Listener<List<Jewelry>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JEWELRY_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Jewelry> jewelryList = null;
                if (task.isSuccessful()){
                    jewelryList = new LinkedList<Jewelry>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Jewelry jewelry = doc.toObject(Jewelry.class);
                        jewelryList.add(jewelry);
                    }
                }
                Log.d("TAG" , "db get");
                listener.onComplete(jewelryList);
            }
        });
    }

    public static void addJewelry(Jewelry jewelry, final JewelryModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JEWELRY_COLLECTION).document(jewelry.getId()).set(jewelry).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }
}
