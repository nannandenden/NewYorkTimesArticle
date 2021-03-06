package com.android.nanden.newyorktimesarticle.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by nanden on 9/21/17.
 */

public class DateDialogFragment extends DialogFragment {

    public DateDialogFragment() {
    }

    public static DateDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DateDialogFragment fragment = new DateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener)
                getTargetFragment();
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

}
