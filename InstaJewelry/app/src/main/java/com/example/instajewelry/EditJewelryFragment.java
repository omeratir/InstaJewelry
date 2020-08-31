package com.example.instajewelry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instajewelry.Auth.SignUpActivity;
import com.example.instajewelry.Model.Jewelry;
import com.example.instajewelry.Model.JewelryModel;
import com.example.instajewelry.Model.StorageModel;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class EditJewelryFragment extends Fragment {
    View view;
    ImageView imageView;
    TextView nameTv;
    TextView typeTv;
    TextView costTv;
    CheckBox soldCb;
    Button takePhotoBtn;
    Button saveChangesBtn;
    Bitmap imageBitmap;
    ProgressBar progressBar;
    Jewelry jewelry;

    public EditJewelryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_jewelry, container, false);

        takePhotoBtn = view.findViewById(R.id.edit_jewelry_take_photo_btn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        nameTv = view.findViewById(R.id.edit_jewelry_name_et);
        typeTv = view.findViewById(R.id.edit_jewelry_type_et);
        costTv = view.findViewById(R.id.edit_jewelry_cost_et);
        soldCb = view.findViewById(R.id.edit_jewelry_sold_cb);
        imageView = view.findViewById(R.id.edit_jewelry_image_v);
        saveChangesBtn = view.findViewById(R.id.edit_jewelry_saveeditjewelry_btn);
        progressBar = view.findViewById(R.id.edit_jewelry_progressBar);

        jewelry = EditJewelryFragmentArgs.fromBundle(getArguments()).getJewelry();

        nameTv.setText(jewelry.name.toString());
        typeTv.setText(jewelry.type.toString());
        costTv.setText(jewelry.cost.toString());
        soldCb.setChecked(jewelry.isSold);

        if ((jewelry.imageUrl != null) && (jewelry.imageUrl != "")) {
            Picasso.get().load(jewelry.imageUrl).placeholder(R.drawable.jewelryicon).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.jewelryicon);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditJewelry();
            }
        });

        return view;
    }

    void saveEditJewelry() {
        progressBar.setVisibility(View.VISIBLE);

        final String name = nameTv.getText().toString();
        final String type = typeTv.getText().toString();
        final String cost = costTv.getText().toString();
        final Boolean ifSold = soldCb.isChecked();

        Log.d("TAG", "name = " + name + " type = " + type + " cost = " + cost + " sold = " + ifSold);

        if ((name.equals(jewelry.name.toString())) && (type.equals(jewelry.type.toString())) && (cost.equals(jewelry.cost.toString())) && (ifSold == jewelry.isSold )) {
            NavController navController = Navigation.findNavController(getView());
            NavDirections direction = JewelryDetailsFragmentDirections.actionGlobalJewelryDetailsFragment(jewelry);
            navController.navigate(direction);
        }

        java.util.Date d = new Date();

        if (imageBitmap != null) {
            StorageModel.uploadImage(imageBitmap, "my_photo" + d.getTime(), new StorageModel.Listener() {
                @Override
                public void onSuccess(String url) {
                    Log.d("TAG", "image url = " + url);

                    // create object
                    Jewelry jewelry1 = new Jewelry(jewelry.id,name,type,cost,ifSold,url);
                    JewelryModel.instance.updateJewelry(jewelry1, new JewelryModel.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            Log.d("TAG", "update new jewelry success");
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
            final Jewelry jewelry1 = new Jewelry(jewelry.id,name,type,cost,ifSold,null);
            JewelryModel.instance.updateJewelry(jewelry1, new JewelryModel.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    Log.d("TAG", "update new jewelry success");
                    NavController navController = Navigation.findNavController(view);
                    NavDirections direction = JewelryDetailsFragmentDirections.actionGlobalJewelryDetailsFragment(jewelry1);
                    navController.navigate(direction);
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