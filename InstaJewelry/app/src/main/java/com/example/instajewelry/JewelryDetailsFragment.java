package com.example.instajewelry;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.JewelryFirebase;
import com.example.instajewelry.Model.JewelryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class JewelryDetailsFragment extends Fragment {

    private Jewelry jewelry;
    TextView name;
    TextView type;
    CheckBox isSoldcb;
    TextView cost;
    String userId;
    JewelryListViewModel viewModel;


    public JewelryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jewelry_details, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        name = view.findViewById(R.id.jewelry_details_name_tv);
        type = view.findViewById(R.id.jewelry_details_type_tv);
        isSoldcb = view.findViewById(R.id.jewelry_details_isSold_cb);
        cost = view.findViewById(R.id.jewelry_details_cost_tv);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        jewelry = JewelryDetailsFragmentArgs.fromBundle(getArguments()).getJewelry();

        if (jewelry != null) {
            update_display();
        }

        View closeButton = view.findViewById(R.id.jewelry_details_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to list by pop back stack
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
        return view;

    }

    private void update_display() {
        name.setText(jewelry.name);
        type.setText(jewelry.type);
        cost.setText(jewelry.cost);
        isSoldcb.setChecked(jewelry.isSold);
    }

    // Connect the activity to the fragment
    // context = activity
    // Call one time
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(JewelryListViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (jewelry.getUserId().equals(userId)) {
            inflater.inflate(R.menu.jewelry_deatlis_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_jewelry_details_edit:
                onEditClicked();
                return true;
            case R.id.menu_jewelry_details_delete:
                onDeleteClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEditClicked() {
        NavController navController = Navigation.findNavController(getView());
        NavDirections direction = EditJewelryFragmentDirections.actionGlobalEditJewelryFragment(jewelry);
        navController.navigate(direction);
    }

    public void onDeleteClicked() {
        viewModel.deleteJewelryVM(jewelry);
        NavController navController = Navigation.findNavController(getView());
        NavDirections direction = JewelryListFragmentDirections.actionGlobalJewelryListFragment();
        navController.navigate(direction);
    }

}

