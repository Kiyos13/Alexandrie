package com.example.alexandrie;

import static com.example.alexandrie.GenresSelectorFragment.hasToExecuteFilter;
import static com.example.alexandrie.GenresSelectorFragment.listBookGenres;
import static com.example.alexandrie.GenresSelectorFragment.listBookGenresSelected;
import static com.example.alexandrie.GenresSelectorFragment.listBookKeptGenres;
import static com.example.alexandrie.ListBooksActivity.booksAdapter;
import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;
import static com.example.alexandrie.ListBooksActivity.retrieveBooksFromSharedPreferences;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.enableDisableView;
import static com.example.alexandrie.LoginConnectionActivity.sharedPrefLogs;
import static com.example.alexandrie.MenuFragment.isRightHand;
import static com.example.alexandrie.OneBookAllInfoActivity.genresListTxtVEdit;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAuthor;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIndex;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReadStatus;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksSerie;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag1;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag2;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag3;
import static com.example.alexandrie.OrderFragment.SortBooksArrayListOfArrayLists;
import static com.example.alexandrie.OrderFragment.currentOrderIndexInSharedPrefs;
import static com.example.alexandrie.OrderFragment.currentWayOrder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FilterFragment extends Fragment {

    private Spinner spinnerSerie, spinnerAuthor;
    private ArrayList<String> listBookSeries, listBookAuthors;
    private String listBookSeriesLabel, listBookAuthorsLabel;
    private String selectedItemSpinnerSeries, selectedItemSpinnerAuthors;
    private CheckBox readCheckBox, notReadCheckBox;
    private Boolean readChecked, notReadChecked;
    private TextView genresTxtV;
    private View globalLyt;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listBookSeriesLabel = "Serie";
        listBookAuthorsLabel = "Auteur";
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        globalLyt = view.findViewById(R.id.filterFragGlobalLyt);

        spinnerSerie = view.findViewById(R.id.spinnerSerie);
        spinnerAuthor = view.findViewById(R.id.spinnerAuthor);
        genresTxtV = view.findViewById(R.id.genresInFilter);

        readCheckBox = view.findViewById(R.id.readCheckBox);
        notReadCheckBox = view.findViewById(R.id.notReadCheckBox);

        readCheckBox.setChecked(true);
        notReadCheckBox.setChecked(true);

        listBookSeries = new ArrayList<String>();
        listBookAuthors = new ArrayList<String>();

        listBookSeries.add(listBookSeriesLabel);
        listBookAuthors.add(listBookAuthorsLabel);

        retrieveBooksSpinnersFromSharedPreferences(sharedPrefBooks);

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookSeries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSerie.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listBookAuthors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthor.setAdapter(adapter);

        genresTxtV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display genres selector fragment
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.genresSelectorInFilterFragContainerV, new GenresSelectorFragment(globalLyt, "filter")).commit();
                enableDisableView(globalLyt, false);

                // Remove AddFragment
                Fragment addFragment;
                if (isRightHand == null)
                    addFragment = getParentFragmentManager().findFragmentById(R.id.addDelFragContainerVRight);
                else {
                    if (isRightHand)
                        addFragment = getParentFragmentManager().findFragmentById(R.id.addDelFragContainerVRight);
                    else
                        addFragment = getParentFragmentManager().findFragmentById(R.id.addDelFragContainerVLeft);
                }
                getParentFragmentManager().beginTransaction().remove(addFragment).commit();
            }
        });

        hasToExecuteFilter.setOnIntegerChangeListener(new OnIntegerChangeListener() {
            @Override
            public void onIntegerChanged(int newValue) {
                setGenresTxtVText();
                executeFilter();
            }
        });

        spinnerSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                executeFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                executeFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        readCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                executeFilter();
            }
        });

        notReadCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                executeFilter();
            }
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
            if (bookDataList.size() >= indexInSharedPrefBooksTag3) {
                currentElement = bookDataList.get(indexInSharedPrefBooksSerie);
                if (!listBookSeries.contains(currentElement) && (currentElement.length() != 0))
                    listBookSeries.add(currentElement);

                currentElement = bookDataList.get(indexInSharedPrefBooksAuthor);
                if (!listBookAuthors.contains(currentElement) && (currentElement.length() != 0))
                    listBookAuthors.add(currentElement);
            }
        }
    }

    // Update spinners current item selected
    private void retrieveSpinnersItems() {
        selectedItemSpinnerSeries = spinnerSerie.getSelectedItem().toString();
        selectedItemSpinnerAuthors = spinnerAuthor.getSelectedItem().toString();
        readChecked = readCheckBox.isChecked();
        notReadChecked = notReadCheckBox.isChecked();
    }

    // Retrieve books depending on spinners
    private void retrieveBooksToKeep() {
        // Retrieve all SharedPrefs to update listBooksInSharedPrefs
        retrieveBooksFromSharedPreferences(sharedPrefBooks);

        int nbFields = listBooksInSharedPrefs.size();
        int nbBooks = listBooksInSharedPrefs.get(indexInSharedPrefBooksIndex).size();

        ArrayList<ArrayList<String>> newlistBooks = new ArrayList<ArrayList<String>>();
        for (int i = 0; i <= nbFields; i++) {
            newlistBooks.add(i, new ArrayList<String>());
        };

        for (int i = 0; i < nbBooks; i++) {
            String currentSerie, currentAuthor, currentReadStatus;
            currentSerie = listBooksInSharedPrefs.get(indexInSharedPrefBooksSerie).get(i);
            currentAuthor = listBooksInSharedPrefs.get(indexInSharedPrefBooksAuthor).get(i);
            currentReadStatus = listBooksInSharedPrefs.get(indexInSharedPrefBooksReadStatus).get(i);

            Boolean serieNotSelected, authorNotSelected, genresNotSelected, readStatusIsOkRead, readStatusIsOkNotRead, readStatusIsOk;
            serieNotSelected = selectedItemSpinnerSeries.equals(listBookSeriesLabel);
            authorNotSelected = selectedItemSpinnerAuthors.equals(listBookAuthorsLabel);
            genresNotSelected = (listBookKeptGenres.size() == 0);
            readStatusIsOkRead = readChecked && (currentReadStatus.equals("true"));
            readStatusIsOkNotRead = notReadChecked && (!currentReadStatus.equals("true"));
            readStatusIsOk = readStatusIsOkRead || readStatusIsOkNotRead;

            if (!(serieNotSelected && authorNotSelected && genresNotSelected)) {
                String currentGenre1, currentGenre2, currentGenre3;
                currentGenre1 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag1).get(i);
                currentGenre2 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag2).get(i);
                currentGenre3 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag3).get(i);

                Boolean serieConditionIsOk, authorConditionIsOk;
                Boolean genre1ConditionIsOk, genre2ConditionIsOk, genre3ConditionIsOk, genreConditionIsOk;
                serieConditionIsOk = (currentSerie.equals(selectedItemSpinnerSeries)) || serieNotSelected;
                authorConditionIsOk = (currentAuthor.equals(selectedItemSpinnerAuthors)) || authorNotSelected;
                genre1ConditionIsOk = listBookKeptGenres.contains(currentGenre1);
                genre2ConditionIsOk = listBookKeptGenres.contains(currentGenre2);
                genre3ConditionIsOk = listBookKeptGenres.contains(currentGenre3);
                genreConditionIsOk = genre1ConditionIsOk || genre2ConditionIsOk || genre3ConditionIsOk;

                if (serieConditionIsOk && authorConditionIsOk && genreConditionIsOk && readStatusIsOk) {
                    // Book check all filter conditions -> add the book to de list
                    for (int j = 0; j < nbFields; j++) {
                        newlistBooks.get(j).add(listBooksInSharedPrefs.get(j).get(i));
                    }
                }
            }
            else {
                if (readStatusIsOk) {
                    // No filter -> add every book in SharedPrefs to the list
                    for (int j = 0; j < nbFields; j++) {
                        newlistBooks.get(j).add(listBooksInSharedPrefs.get(j).get(i));
                    }
                }
            }
        }

        listBooksInSharedPrefs = newlistBooks;
        // Sort arraylists
        listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
        booksAdapter.notifyDataSetChanged();
    }

    // Update filters and retrieve books that fill the conditions
    private void executeFilter() {
        retrieveSpinnersItems();
        retrieveBooksToKeep();
    }

    private void setGenresTxtVText() {
        String genreTxtVText;
        if (listBookKeptGenres.size() != 0) {
            genreTxtVText = "";
            for (int i = 0; i < listBookKeptGenres.size(); i++) {
                if (i == 0)
                    genreTxtVText += listBookKeptGenres.get(i);
                else
                    genreTxtVText += ", " + listBookKeptGenres.get(i);
            }
        }
        else
            genreTxtVText = "Genres";
        genresTxtV.setText(genreTxtVText);
    }
}