package com.example.instajewelry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_TITLE = "TITLE";
    private static final String ARG_MESSAGE = "MESSAGE";

    private String alertTitle;
    private String alertMessage;

    public AlertDialogFragment() {
        // Required empty public constructor
    }

    public static AlertDialogFragment newInstance(String alertTitle, String alertMessage) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, alertTitle);
        args.putString(ARG_MESSAGE, alertMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alertTitle = getArguments().getString(ARG_TITLE);
            alertMessage = getArguments().getString(ARG_MESSAGE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (alertTitle != null) {
            builder.setTitle(alertTitle);
        }
        if (alertMessage != null) {
            builder.setMessage(alertMessage);
        }

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                // dismiss close dialog
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        // Create the AlertDialog object and return it
        // builder create = create the dialog object
        // builder.setView(v)

        return builder.create();
    }


}