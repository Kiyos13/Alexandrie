package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.sharedPrefLogs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FilterFragment extends Fragment {

    private Spinner spinnerType, spinnerGenre, spinnerSerie, spinnerAuthor;
    private ArrayList<String> listBookTypes, listBookGenres, listBookSeries, listBookAuthors;

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
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        listBookTypes = new ArrayList<String>();
        listBookGenres = new ArrayList<String>();
        listBookSeries = new ArrayList<String>();
        listBookAuthors = new ArrayList<String>();

        retrieveBooksSpinnersFromSharedPreferences(sharedPrefBooks);

        spinnerType = view.findViewById(R.id.spinnerType);
        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        spinnerSerie = view.findViewById(R.id.spinnerSerie);
        spinnerAuthor = view.findViewById(R.id.spinnerAuthor);

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookGenres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookSeries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSerie.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookAuthors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthor.setAdapter(adapter);

        return view;
    }

    // Retrieve books infos from SharedPreferences to fill spinnerLists
    private void retrieveBooksSpinnersFromSharedPreferences(SharedPreferences sharedPreferences) {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        String bookData;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            bookData = entry.getValue().toString();
            bookData = bookData.substring(1);
            bookData = bookData.substring(0, bookData.length() - 1);

            List<String> bookDataList = new ArrayList<String>(Arrays.asList(bookData.split(", ")));
            SortStringListByFirstChar(bookDataList);

            int bookDataListLength = bookDataList.size();
            String currentData;
            for (int i = 0; i < bookDataListLength; i++) {
                currentData = bookDataList.get(i).substring(2);
                bookDataList.set(i, currentData);
            }

            String currentElement;
            if (bookDataList.size() >= 7) {
                currentElement = bookDataList.get(3);
                if (!listBookSeries.contains(currentElement) && (currentElement.length() != 0))
                    listBookSeries.add(currentElement);

                currentElement = bookDataList.get(4);
                if (!listBookAuthors.contains(currentElement) && (currentElement.length() != 0))
                    listBookAuthors.add(currentElement);

                for (int i = 5; i < 8; i++) {
                    currentElement = bookDataList.get(5);
                    if (currentElement.length() != 0) {
                        if (!listBookTypes.contains(currentElement))
                            listBookTypes.add(currentElement);
                        if (!listBookGenres.contains(currentElement))
                            listBookGenres.add(currentElement);
                    }
                }
            }
        }
    }
}