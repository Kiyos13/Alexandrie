package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.booksAdapter;
import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;
import static com.example.alexandrie.ListBooksActivity.retrieveBooksFromSharedPreferences;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.sharedPrefLogs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FilterFragment extends Fragment {

    private Spinner spinnerGenre, spinnerSerie, spinnerAuthor;
    private ArrayList<String> listBookGenres, listBookSeries, listBookAuthors;
    private String listBookGenresLabel, listBookSeriesLabel, listBookAuthorsLabel;
    private String selectedItemSpinnerGenres, selectedItemSpinnerSeries, selectedItemSpinnerAuthors;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listBookGenresLabel = "Genre";
        listBookSeriesLabel = "Serie";
        listBookAuthorsLabel = "Auteur";
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        spinnerSerie = view.findViewById(R.id.spinnerSerie);
        spinnerAuthor = view.findViewById(R.id.spinnerAuthor);

        listBookGenres = new ArrayList<String>();
        listBookSeries = new ArrayList<String>();
        listBookAuthors = new ArrayList<String>();

        listBookGenres.add(listBookGenresLabel);
        listBookSeries.add(listBookSeriesLabel);
        listBookAuthors.add(listBookAuthorsLabel);

        retrieveBooksSpinnersFromSharedPreferences(sharedPrefBooks);

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookGenres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookSeries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSerie.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookAuthors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthor.setAdapter(adapter);


        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retrieveSpinnersItems();
                if (!selectedItemSpinnerGenres.equals(listBookGenresLabel)) {
                    retrieveBooksSpinnersFromListWithSpinners();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retrieveSpinnersItems();
                if (!selectedItemSpinnerSeries.equals(listBookSeriesLabel)) {
                    retrieveBooksSpinnersFromListWithSpinners();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retrieveSpinnersItems();
                if (!selectedItemSpinnerAuthors.equals(listBookAuthorsLabel)) {
                    retrieveBooksSpinnersFromListWithSpinners();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

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
                    currentElement = bookDataList.get(i);
                    if (!listBookGenres.contains(currentElement) && (currentElement.length() != 0))
                        listBookGenres.add(currentElement);
                }
            }
        }
    }

    // Update spinners current item selected
    private void retrieveSpinnersItems() {
        selectedItemSpinnerGenres = spinnerGenre.getSelectedItem().toString();
        selectedItemSpinnerSeries = spinnerSerie.getSelectedItem().toString();
        selectedItemSpinnerAuthors = spinnerAuthor.getSelectedItem().toString();
    }

    // Retrieve books infos from SharedPreferences to fill spinnerLists
    private void retrieveBooksSpinnersFromListWithSpinners() {
        // Retrieve all SharedPrefs to update listBooksInSharedPrefs
        retrieveBooksFromSharedPreferences(sharedPrefBooks);

        int nbFields = listBooksInSharedPrefs.size();
        int nbBooks = listBooksInSharedPrefs.get(0).size();

        ArrayList<ArrayList<String>> newlistBooks = new ArrayList<ArrayList<String>>();
        for (int i = 0; i <= nbFields; i++) {
            newlistBooks.add(i, new ArrayList<String>());
        };

        for (int i = 0; i < nbBooks; i++) {
            String currentGenre1, currentGenre2, currentGenre3, currentSerie, currentAuthor;
            currentSerie = listBooksInSharedPrefs.get(3).get(i);
            currentAuthor = listBooksInSharedPrefs.get(4).get(i);
            currentGenre1 = listBooksInSharedPrefs.get(5).get(i);
            currentGenre2 = listBooksInSharedPrefs.get(6).get(i);
            currentGenre3 = listBooksInSharedPrefs.get(7).get(i);

            Boolean genreNotSelected = selectedItemSpinnerGenres.equals(listBookGenresLabel);
            Boolean serieNotSelected = selectedItemSpinnerSeries.equals(listBookSeriesLabel);
            Boolean authorNotSelected = selectedItemSpinnerAuthors.equals(listBookAuthorsLabel);

            if (!(genreNotSelected && serieNotSelected && authorNotSelected)) {
                Boolean serieConditionIsOk = (currentSerie.equals(selectedItemSpinnerSeries)) || serieNotSelected;
                Boolean authorConditionIsOk = (currentAuthor.equals(selectedItemSpinnerAuthors)) || authorNotSelected;
                Boolean genre1ConditionIsOk = currentGenre1.equals(selectedItemSpinnerGenres);
                Boolean genre2ConditionIsOk = currentGenre2.equals(selectedItemSpinnerGenres);
                Boolean genre3ConditionIsOk = currentGenre3.equals(selectedItemSpinnerGenres);
                Boolean genreConditionIsOk = genre1ConditionIsOk || genre2ConditionIsOk || genre3ConditionIsOk || genreNotSelected;

                if (serieConditionIsOk && authorConditionIsOk && genreConditionIsOk) {
                    // Book check all filter conditions -> add the book to de list
                    for (int j = 0; j < nbFields; j++) {
                        newlistBooks.get(j).add(listBooksInSharedPrefs.get(j).get(i));
                    }
                }
            }
            else {
                // No filter -> add every book in SharedPrefs to the list
                for (int j = 0; j < nbFields; j++) {
                    newlistBooks.get(j).add(listBooksInSharedPrefs.get(j).get(i));
                }
            }
        }

        listBooksInSharedPrefs = newlistBooks;
        booksAdapter.notifyDataSetChanged();
    }
}