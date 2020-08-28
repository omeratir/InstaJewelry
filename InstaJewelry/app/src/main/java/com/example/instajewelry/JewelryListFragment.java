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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instajewelry.Model.AppLocalDb;
import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.JewelryModel;

import java.util.LinkedList;
import java.util.List;


public class JewelryListFragment extends Fragment {

    private String title;
    private TextView titleTextView;

    RecyclerView list;
    List<Jewelry> jewelryList = new LinkedList<Jewelry>();
    JewelryListAdapter jewelryListAdapter;
    JewelryListViewModel viewModel;
    LiveData<List<Jewelry>> liveData;
    SwipeRefreshLayout swipeRefresh;

    interface Delegate {
        void onItemSelected(Jewelry jewelry);
    }

    Delegate parent;

    public JewelryListFragment() {
//        JewelryModel.instance.getAllJewelriesLocal(new JewelryModel.Listener<List<Jewelry>>() {
//            @Override
//            public void onComplete(List<Jewelry> _data) {
//                jewelryList = _data;
//                if (jewelryListAdapter != null){
//                    jewelryListAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }

    // Connect the activity to the fragment
    // context = activity
    // Call one time
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  Delegate) {
            parent = (Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString() + "parent must implement Delegate - Jewelry List Fragment");
        }

        setHasOptionsMenu(true);

        // after the connect to the activity
        viewModel = new ViewModelProvider(this).get(JewelryListViewModel.class);
    }

    // Call multi times
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_jewelry_list, container, false);

        list = view.findViewById(R.id.fragment_jewelry_list_list);
        list.setHasFixedSize(true);

        // linear - as list , grid - as gallery
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);

        jewelryListAdapter = new JewelryListAdapter();
        list.setAdapter(jewelryListAdapter);

        jewelryListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "row was clicked = " + position);
                Jewelry jewelry = jewelryList.get(position);
                parent.onItemSelected(jewelry);
//                NavGraphDirections.ActionGlobalJewelryDetailsFragment direction = JewelryListFragmentDirections.actionGlobalJewelryDetailsFragment(jewelry);
//                Navigation.findNavController(view).navigate(direction);
            }
        });

//        jewelryList = viewModel.getDataList();

        Log.d("TAG" , "get live data");


        Log.d("TAG" , "refresh");
        swipeRefresh = view.findViewById(R.id.jewelry_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // call db to refresh.
                viewModel.refresh(new JewelryModel.CompListener() {
                    @Override
                    public void onComplete() {
                        swipeRefresh.setRefreshing(true);
                    }
                });
            }
        });

        liveData = viewModel.getData();

        Log.d("TAG" , "observe live data");
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Jewelry>>() {
            @Override
            public void onChanged(List<Jewelry> jewelries) {
                // when the data on live data changed
                jewelryList = jewelries;
                Log.d("TAG" , "get data success");
                jewelryListAdapter.notifyDataSetChanged();
                Log.d("TAG" , "adapter success");
                swipeRefresh.setRefreshing(false);
            }

        });

        return view;
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
        OnItemClickListener listener;

        public JewelryRowViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
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

//            this.listener = listener;

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
    class JewelryListAdapter extends  RecyclerView.Adapter<JewelryRowViewHolder> {
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        // create row object
        @NonNull
        @Override
        public JewelryRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            // context = this activity
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_row, viewGroup, false);
            JewelryRowViewHolder rowViewHolder = new JewelryRowViewHolder(v,listener);
            return rowViewHolder;
        }

        // bind data of item to row
        @Override
        public void onBindViewHolder(@NonNull JewelryRowViewHolder holder, int position) {
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
                Log.d("TAG" , "fragment handle add menu click -> navigate to add new jewelry");
                NavController navController = Navigation.findNavController(list);
                NavDirections direction = NewJewelryFragmentDirections.actionGlobalNewJewelryFragment();
                navController.navigate(direction);
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