package com.example.alexandrie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FilterFragment extends Fragment {

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*
        Spinner spinnerType = (Spinner) getView().findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getView().getContext(),
                R.array.all_book_types, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        Spinner spinnerGenre = (Spinner) getView().findViewById(R.id.spinnerGenre);
        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(getView().getContext(),
                R.array.all_book_genres, android.R.layout.simple_spinner_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapterGenre);

        Spinner spinnerSerie = (Spinner) getView().findViewById(R.id.spinnerSerie);
        ArrayAdapter<CharSequence> adapterSerie = ArrayAdapter.createFromResource(getView().getContext(),
                R.array.all_series, android.R.layout.simple_spinner_item);
        adapterSerie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSerie.setAdapter(adapterSerie);

        Spinner spinnerAuthor = (Spinner) getView().findViewById(R.id.spinnerAuthor);
        ArrayAdapter<CharSequence> adapterAuthor = ArrayAdapter.createFromResource(getView().getContext(),
                R.array.all_authors, android.R.layout.simple_spinner_item);
        adapterAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthor.setAdapter(adapterAuthor);
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }
}