package com.example.instajewelry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class FragmentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);

        // get fragment
        JewelryListFragment jewelryList_fragment = (JewelryListFragment) getSupportFragmentManager().findFragmentById(R.id.home_jewelry_list_fragment);
        jewelryList_fragment.setTitle("activity update this");

        JewelryDetailsFragment jewelryDetailsFragment = new JewelryDetailsFragment();

        // Change fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.home_fragment_container,jewelryDetailsFragment,"TAG");
        // add the transaction to view
        transaction.commit();
    }
}