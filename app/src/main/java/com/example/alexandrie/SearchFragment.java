package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.booksAdapter;
import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;
import static com.example.alexandrie.ListBooksActivity.retrieveBooksFromSharedPreferences;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAuthor;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIndex;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag1;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag2;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag3;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTitle;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksVolume;
import static com.example.alexandrie.OrderFragment.SortBooksArrayListOfArrayLists;
import static com.example.alexandrie.OrderFragment.currentOrderIndexInSharedPrefs;
import static com.example.alexandrie.OrderFragment.currentWayOrder;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private TextInputLayout searchTxtLyt;
    private ImageView searchImgV;
    private String currentSearch;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchTxtLyt = view.findViewById(R.id.searchTxtInputLyt);
        searchImgV = view.findViewById(R.id.searchImgV);

        searchTxtLyt.getEditText().addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                executeSearch();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        searchImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeSearch();
            }
        });

        return view;
    }

    // Update search text
    private void retrieveSearchText() {
        currentSearch = searchTxtLyt.getEditText().getText().toString(); // Set text to search
    }

    // Retrieve books depending on search text : in title, volume, author or tags
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
            String currentGenre1, currentGenre2, currentGenre3, currentTitle, currentVolume, currentAuthor;
            currentTitle = listBooksInSharedPrefs.get(indexInSharedPrefBooksTitle).get(i);
            currentVolume = listBooksInSharedPrefs.get(indexInSharedPrefBooksVolume).get(i);
            currentAuthor = listBooksInSharedPrefs.get(indexInSharedPrefBooksAuthor).get(i);
            currentGenre1 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag1).get(i);
            currentGenre2 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag2).get(i);
            currentGenre3 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag3).get(i);

            // We use upper cases to not have strict equality
            Boolean titleContainsSearchText = currentTitle.toUpperCase().contains(currentSearch.toUpperCase());
            Boolean volumeContainsSearchText = currentVolume.toUpperCase().contains(currentSearch.toUpperCase());
            Boolean authorContainsSearchText = currentAuthor.toUpperCase().contains(currentSearch.toUpperCase());
            Boolean genre1ContainsSearchText = currentGenre1.toUpperCase().contains(currentSearch.toUpperCase());
            Boolean genre2ContainsSearchText = currentGenre2.toUpperCase().contains(currentSearch.toUpperCase());
            Boolean genre3ContainsSearchText = currentGenre3.toUpperCase().contains(currentSearch.toUpperCase());
            Boolean genreContainsSearchText = genre1ContainsSearchText || genre2ContainsSearchText || genre3ContainsSearchText;

            if (titleContainsSearchText || volumeContainsSearchText || authorContainsSearchText || genreContainsSearchText) {
                for (int j = 0; j < nbFields; j++) {
                    newlistBooks.get(j).add(listBooksInSharedPrefs.get(j).get(i));
                }
            }
        }

        listBooksInSharedPrefs = newlistBooks;
        // Sort arraylists
        listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
        booksAdapter.notifyDataSetChanged();
    }

    // Update search text and retrieve books that contains the search text
    private void executeSearch() {
        retrieveSearchText();
        if (currentSearch.length() != 0)
            retrieveBooksToKeep();
        else
            retrieveBooksFromSharedPreferences(sharedPrefBooks);
    }
}