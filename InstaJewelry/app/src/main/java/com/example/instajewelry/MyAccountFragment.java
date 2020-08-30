package com.example.instajewelry;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.instajewelry.Auth.AuthFirebase;
import com.example.instajewelry.Auth.User;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccountFragment extends Fragment {

    User user;
    Button logout_btn;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Log.d("TAG", "get user");
//        user = AuthFirebase.getUser();
//        Log.d("TAG", "user name = " + user.name);
//        Log.d("TAG", "user email = " + user.email);

        logout_btn = view.findViewById(R.id.home_logout_btn);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyApplication.context, MainActivity.class));
            }
        });


        return view;
    }
}