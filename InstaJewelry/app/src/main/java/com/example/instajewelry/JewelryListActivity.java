package com.example.instajewelry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.Model;

import java.util.List;

public class JewelryListActivity extends AppCompatActivity {

    RecyclerView list;
    List<Jewelry> jewelryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jewelry_list);

        jewelryList = Model.instance.getAllJewelry();

        list = findViewById(R.id.jewelry_list_rc);
        list.setHasFixedSize(true);

        // linear - as list , grid - as gallery
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);

        JewelryListAdapter jewelryListAdapter = new JewelryListAdapter();
        list.setAdapter(jewelryListAdapter);

        jewelryListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "row was clicked = " + position);
            }
        });
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
            View v = LayoutInflater.from(JewelryListActivity.this).inflate(R.layout.list_row, viewGroup, false);
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
}