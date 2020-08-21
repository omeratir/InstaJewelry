package com.example.instajewelry;

import android.content.Context;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.Model;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JewelryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JewelryListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String title;
    private TextView titleTextView;

    RecyclerView list;
    List<Jewelry> jewelryList;

    interface Delegate {
        void onItemSelected(Jewelry jewelry);

    }

    Delegate parent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JewelryListFragment() {
        // get the data
        jewelryList = Model.instance.getAllJewelry();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JewelryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JewelryListFragment newInstance(String param1, String param2) {
        JewelryListFragment fragment = new JewelryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // Default
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jewelry_list, container, false);

        list = view.findViewById(R.id.fragment_jewelry_list_list);
        list.setHasFixedSize(true);

        // linear - as list , grid - as gallery
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);

        JewelryListAdapter jewelryListAdapter = new JewelryListAdapter();
        list.setAdapter(jewelryListAdapter);

        jewelryListAdapter.setOnItemClickListener(new JewelryListActivity.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "row was clicked = " + position);
                Jewelry jewelry = jewelryList.get(position);
                parent.onItemSelected(jewelry);
            }
        });

        return view;
    }

    // Connect the activity to the fragment
    // context = activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  Delegate) {
            parent = (Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString() + "parent must implement Delegate - Jewelry List Fragment");
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    /* static = class didn't match to the parent = can not access to list */
    static class JewelryRowViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        CheckBox sold_cb;
        ImageView image;
        Jewelry jewelry;
        JewelryListActivity.OnItemClickListener listener;

        public JewelryRowViewHolder(@NonNull View itemView, final JewelryListActivity.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name_textbox);
            type = itemView.findViewById(R.id.row_type_textbox);
            sold_cb = itemView.findViewById(R.id.row_sold_cb);
            image = itemView.findViewById(R.id.row_image);

            // catch the click on the cb
            sold_cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jewelry.isSold = sold_cb.isChecked();
                }
            });

            this.listener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Jewelry j) {
            jewelry = j;
            name.setText(j.name);
            type.setText(j.type);
            sold_cb.setChecked(j.isSold);
        }
    }

    interface OnItemClickListener {
        void onClick(int position);
    }

    // Adapter
    class JewelryListAdapter extends  RecyclerView.Adapter<JewelryListActivity.JewelryRowViewHolder> {
        private JewelryListActivity.OnItemClickListener listener;

        void setOnItemClickListener(JewelryListActivity.OnItemClickListener listener) {
            this.listener = listener;
        }

        // create row object
        @NonNull
        @Override
        public JewelryListActivity.JewelryRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            // context = this activity
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_row, viewGroup, false);
            JewelryListActivity.JewelryRowViewHolder rowViewHolder = new JewelryListActivity.JewelryRowViewHolder(v,listener);
            return rowViewHolder;
        }

        // bind data of item to row
        @Override
        public void onBindViewHolder(@NonNull JewelryListActivity.JewelryRowViewHolder holder, int position) {
            Jewelry jewelry = jewelryList.get(position);
            holder.bind(jewelry);
        }

        // count of rows
        @Override
        public int getItemCount() {
            return jewelryList.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.jewelry_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_jewelry_list_add:
                Log.d("TAG" , "fragment handle add menu click");
                MyDatePickerFragment picker = new MyDatePickerFragment();
                picker.show(getParentFragmentManager(), "TAG");
                return true;
            case R.id.menu_jewelry_list_info:
                Log.d("TAG" , "fragment handle info menu click");
                AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance("Jewelry app info", "welcome to info page!!!");

                dialogFragment.show(getParentFragmentManager(),"TAG");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}