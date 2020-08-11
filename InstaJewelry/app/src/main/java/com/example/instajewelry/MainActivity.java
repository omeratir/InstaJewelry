package com.example.instajewelry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.Model;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Jewelry> jewelryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        jewelryList = Model.instance.getAllJewelry();
    }
}