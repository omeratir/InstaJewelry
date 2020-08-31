package com.example.instajewelry.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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
                        if (!jewelry.isDeleted()) {
                            jewelryList.add(jewelry);
                        }
                    }
                }
                listener.onComplete(jewelryList);
            }
        });
    }

    public static void addJewelry(Jewelry jewelry, final JewelryModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(JEWELRY_COLLECTION).document();

        jewelry.setId(documentReference.getId());

        Log.d("TAG" , "add = " + jewelry.id + " " + documentReference.getId());

        documentReference.set(jewelry)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    public static void updateJewelry(Jewelry jewelry, final JewelryModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(JEWELRY_COLLECTION).document(jewelry.id);
        documentReference.update(
                "name" , jewelry.name , "cost" , jewelry.cost , "isSold" , jewelry.isSold, "type", jewelry.type)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (listener!=null){
                            listener.onComplete(task.isSuccessful());
                        }
                    }
                });
    }

    public static void deleteJewelry(Jewelry jewelry) {
        updateJewelryDeleted(jewelry);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("TAG" , "to delete firebase = " + jewelry.id + " " + jewelry.name);
        db.collection(JEWELRY_COLLECTION).document(jewelry.id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

    }

    public static void updateJewelryDeleted(Jewelry jewelry) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(JEWELRY_COLLECTION).document(jewelry.id)
                .update("deleted", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

    }
}
