package com.example.instajewelry.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class StorageModel {
    public interface Listener{
        void onSuccess(String url);
        void onFail();
    }

    // Upload image to firebase
    // Listener if done
    // Name to save in firebase

    public static void uploadImage(Bitmap imageBmp, String name, final Listener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Folder to save + name that will save in the folder
        final StorageReference imagesRef = storage.getReference().child("images").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onFail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onSuccess(downloadUrl.toString());
                    }
                });
            }
        });
    }

}
