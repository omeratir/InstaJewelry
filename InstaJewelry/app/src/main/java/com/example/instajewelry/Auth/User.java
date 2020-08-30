package com.example.instajewelry.Auth;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String  uid;
    public String name;
    public String email;

    public User() {

    }

    public User(@NonNull String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }


    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
