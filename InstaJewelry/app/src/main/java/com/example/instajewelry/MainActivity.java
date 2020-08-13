package com.example.instajewelry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.Model;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Jewelry> jewelryList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         listView = findViewById(R.id.main_list);

        // get all the data
         jewelryList = Model.instance.getAllJewelry();

         MyAdapter myAdapter = new MyAdapter();
         listView.setAdapter(myAdapter);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 // get the position of the click on row in list
                 Log.d("TAG", "row click on: " + position);
             }
         });
    }

    class MyAdapter extends BaseAdapter {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Size of the list
        @Override
        public int getCount() {
            return jewelryList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if it null - we create the convert view.
            // convert view - recycle list row.

            Jewelry jewelry;

            if (convertView == null ) {
                convertView = inflater.inflate(R.layout.list_row, null);
                final CheckBox cb_sold = convertView.findViewById(R.id.row_sold_cb);

                // catch the click check box
                cb_sold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        Log.d("TAG", "check box click on row : " + pos);
                        Jewelry jewelry = jewelryList.get(pos);

                        jewelry.isSold = cb_sold.isChecked();
                    }
                });
            }

            TextView name = convertView.findViewById(R.id.row_name_textbox);
            TextView type = convertView.findViewById(R.id.row_type_textbox);
            CheckBox sold = convertView.findViewById(R.id.row_sold_cb);
            ImageView image = convertView.findViewById(R.id.row_image);

            jewelry = jewelryList.get(position);
            name.setText(jewelry.name);
            type.setText(jewelry.type);
            sold.setChecked(jewelry.isSold);
            sold.setTag(position);

            return convertView;
        }
    }
}