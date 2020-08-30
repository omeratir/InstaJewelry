package com.example.instajewelry.Auth;

public class UserModel {
    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }
}
