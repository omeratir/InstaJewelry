package com.example.instajewelry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.instajewelry.Model.Jewelry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements JewelryListFragment.Delegate {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Connect action bar
        navController = Navigation.findNavController(this, R.id.home_nav_host);
        NavigationUI.setupActionBarWithNavController(this,navController);

        // connect the bottom navigation - tab bar
        BottomNavigationView bottomNavigation = findViewById(R.id.home_bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigation, navController);

    }

    @Override
    public void onItemSelected(Jewelry jewelry) {
        NavController navController = Navigation.findNavController(this, R.id.home_nav_host);
//        navController.navigate(R.id.action_jewelryListFragment_to_jewelryDetailsFragment);

//        // send the item by thr action

//        JewelryListFragmentDirections.ActionJewelryListFragmentToJewelryDetailsFragment direction = JewelryListFragmentDirections.actionJewelryListFragmentToJewelryDetailsFragment(jewelry);
//        navController.navigate(direction);

        NavGraphDirections.ActionGlobalJewelryDetailsFragment direction = JewelryListFragmentDirections.actionGlobalJewelryDetailsFragment(jewelry);
        navController.navigate(direction);

    }

    // catch the menu click
    // item that clicked
    // android.R.id.home == back button on action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navController.navigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // every click on view will start the onOption function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
       // getMenuInflater().inflate(R.menu.jewelry_list_menu, menu);
        return true;
    }
}