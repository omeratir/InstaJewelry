package com.example.instajewelry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.JewelryModel;
import com.example.instajewelry.Model.StorageModel;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class NewJewelryFragment extends Fragment {
    View view;
    ImageView imageView;
    TextView nameTv;
    TextView typeTv;
    TextView costTv;
    CheckBox soldCb;
    Button takePhotoBtn;
    Button saveBtn;
    Bitmap imageBitmap;
    ProgressBar progressBar;

    public NewJewelryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_jewelry, container, false);

        takePhotoBtn = view.findViewById(R.id.new_jewelry_take_photo_btn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        nameTv = view.findViewById(R.id.new_jewelry_name_et);
        typeTv = view.findViewById(R.id.new_jewelry_type_et);
        costTv = view.findViewById(R.id.new_jewelry_cost_et);
        soldCb = view.findViewById(R.id.new_jewelry_sold_cb);
        imageView = view.findViewById(R.id.new_jewelry_image_v);
        saveBtn = view.findViewById(R.id.new_jewelry_savenewjewelry_btn);
        progressBar = view.findViewById(R.id.new_jewelry_progressBar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTv.getText().toString().trim();
                String type = typeTv.getText().toString().trim();
                String cost = costTv.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameTv.setError("Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(type)) {
                    typeTv.setError("Color is required.");
                    return;
                }

                if (TextUtils.isEmpty(cost)) {
                    costTv.setError("Cost is required.");
                    return;
                }

                saveNewJewelry();
            }
        });

        return view;
    }

    void saveNewJewelry() {
        progressBar.setVisibility(View.VISIBLE);

        final String name = nameTv.getText().toString();
        Log.d("TAG", "name = " + name);

        final String type = typeTv.getText().toString();
        final String cost = costTv.getText().toString();

        final Boolean ifSold = soldCb.isChecked();

        Log.d("TAG", "cost = " + cost);

        java.util.Date d = new Date();

        if (imageBitmap != null) {
            StorageModel.uploadImage(imageBitmap, "my_photo" + d.getTime(), new StorageModel.Listener() {
                @Override
                public void onSuccess(String url) {
                    Log.d("TAG", "image url = " + url);

                    // create object
                    Jewelry jewelry = new Jewelry(name,name,type,cost,ifSold,url);
                    JewelryModel.instance.addJewelry(jewelry, new JewelryModel.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            Log.d("TAG", "save new jewelry success");
                            NavController navController = Navigation.findNavController(view);
                            NavDirections direction = JewelryListFragmentDirections.actionGlobalJewelryListFragment();
                            navController.navigate(direction);
                        }
                    });
                }

                @Override
                public void onFail() {
                    Log.d("TAG", "image url upload failed ");
                }
            });
        } else {
            // create object
            Jewelry jewelry = new Jewelry(name,name,type,cost,ifSold,null);
            JewelryModel.instance.addJewelry(jewelry, new JewelryModel.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    Log.d("TAG", "save new jewelry success");
                    NavController navController = Navigation.findNavController(view);
                    // Back to list
                    navController.navigateUp();
                }
            });
        }





    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    void takePhoto() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // callback after camera
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = rotateImage((Bitmap) extras.get("data"));
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public static Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}