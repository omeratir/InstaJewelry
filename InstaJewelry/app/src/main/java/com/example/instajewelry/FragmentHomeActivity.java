package com.example.instajewelry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.instajewelry.Model.Jewelry;

public class FragmentHomeActivity extends AppCompatActivity implements JewelryListFragment.Delegate {
    JewelryDetailsFragment jewelryDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);

        // create new fragment object
        JewelryListFragment jewelryList_fragment = new JewelryListFragment();

        // Change fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container, jewelryList_fragment,"TAG");
        transaction.addToBackStack("TAG");
        // add the transaction to view
        transaction.commit();
    }

    void openJewelryDetails(Jewelry jewelry) {

        JewelryDetailsFragment jewelryDetailsFragment = new JewelryDetailsFragment();
        jewelryDetailsFragment.setJewelry(jewelry);
        // Change fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container,jewelryDetailsFragment,"TAG");
        transaction.addToBackStack("TAG");
        // add the transaction to view
        transaction.commit();
    }

    @Override
    public void onItemSelected(Jewelry jewelry) {
        openJewelryDetails(jewelry);
    }
}