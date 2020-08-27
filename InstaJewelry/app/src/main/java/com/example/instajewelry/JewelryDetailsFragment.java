package com.example.instajewelry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.instajewelry.Model.Jewelry;

public class JewelryDetailsFragment extends Fragment {

    private Jewelry jewelry;
    TextView name;
    TextView type;
    CheckBox isSoldcb;
    TextView cost;


    public JewelryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jewelry_details, container, false);

        name = view.findViewById(R.id.jewelry_details_name_tv);
        type = view.findViewById(R.id.jewelry_details_type_tv);
        isSoldcb = view.findViewById(R.id.jewelry_details_isSold_cb);
        cost = view.findViewById(R.id.jewelry_details_cost_tv);

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

}

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;


//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment JewelryDetailsFragment.
//     */
//    public static JewelryDetailsFragment newInstance(String param1, String param2) {
//        JewelryDetailsFragment fragment = new JewelryDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
