package com.android.nanden.newyorktimesarticle.fragments;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.nanden.newyorktimesarticle.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nanden on 9/19/17.
 */

public class FilterDialogFragment extends DialogFragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    @BindView(R.id.cbArts)
    CheckBox cbArt;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbSports) CheckBox cbSports;
    @BindView(R.id.tvSave) TextView tvSave;
    @BindView(R.id.spinnerSort)
    Spinner spinnerSort;
    @BindView(R.id.tvDateInput)
    TextView tvDateInput;
    private Unbinder unbinder;
    private Map<String, String> filterValue;

    public interface FilterDialogListener {
        void onFilterDialog(Map<String, String> filterValue);
    }
    public FilterDialogFragment() {
        // an empty constructor is required for dialogFragment
        // use newInstance to add argument instead of this constructor
    }

    public static FilterDialogFragment newInstance(String category) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        filterValue = new HashMap<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSave.setOnClickListener(this);
        tvDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        if (cbArt.isChecked()) {
            filterValue.put("new_desk", "art");
        }
        if (cbFashion.isChecked()) {
            filterValue.put("new_desk1", "fashion");
        }
        if (cbSports.isChecked()) {
            filterValue.put("new_desk2", "sports");
        }
        filterValue.put("sort", spinnerSort.getSelectedItem().toString());
        FilterDialogListener listener = (FilterDialogListener) getActivity();
        listener.onFilterDialog(filterValue);
        dismiss();
    }

    private void showDatePickerDialog() {
        FragmentManager fm = getFragmentManager();
        DateDialogFragment dateDialogFragment = DateDialogFragment.newInstance();
        // 300 is request code
        dateDialogFragment.setTargetFragment(FilterDialogFragment.this, 300);
        dateDialogFragment.show(fm, "date_dialog_fragment");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        //TODO: need to set to the correct date into text view
    }


}
