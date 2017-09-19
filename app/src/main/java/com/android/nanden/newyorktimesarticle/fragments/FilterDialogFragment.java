package com.android.nanden.newyorktimesarticle.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.android.nanden.newyorktimesarticle.R;

/**
 * Created by nanden on 9/19/17.
 */

public class FilterDialogFragment extends DialogFragment {

    public FilterDialogFragment() {
    }

    public static FilterDialogFragment newInstance(String category) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogBuilder
                .setTitle("Category")
                .setView(inflater.inflate(R.layout.fragment_filter_dialog, null))
                .setTitle("Sort")
                .setItems(R.array.sort, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("position " + i + " clicked");
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

            return dialogBuilder.create();
    }

}
