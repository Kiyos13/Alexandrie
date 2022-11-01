package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.hasToUpdateListBooksRecyclerView;
import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class OrderFragment extends Fragment {

    private RadioButton titleRadioButton, authorRadioButton, serieRadioButton, addDateRadioButton, releaseDateRadioButton;
    private Integer titleIndexInSharedPrefs = 1, authorIndexInSharedPrefs = 4, serieIndexInSharedPrefs = 3;
    private Integer addDateIndexInSharedPrefs = 10, releaseDateIndexInSharedPrefs = 11, currentIndexInSharedPrefs = titleIndexInSharedPrefs;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        titleRadioButton = view.findViewById(R.id.titleRadioBtn);
        authorRadioButton = view.findViewById(R.id.authorRadioBtn);
        serieRadioButton = view.findViewById(R.id.serieRadioBtn);
        addDateRadioButton = view.findViewById(R.id.addDateRadioBtn);
        releaseDateRadioButton = view.findViewById(R.id.releaseDateRadioBtn);

        titleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                titleRadioButton.setChecked(true);
                currentIndexInSharedPrefs = titleIndexInSharedPrefs;

                int nbTitles = listBooksInSharedPrefs.get(1).size();
                for (int i = 0; i < nbTitles; i++) {
                    System.out.println("Title " + i + " = " + listBooksInSharedPrefs.get(1).get(i));
                }

                hasToUpdateListBooksRecyclerView = true;
            }
        });

        authorRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                authorRadioButton.setChecked(true);
                currentIndexInSharedPrefs = authorIndexInSharedPrefs;

                int nbAuthors = listBooksInSharedPrefs.get(4).size();
                for (int i = 0; i < nbAuthors; i++) {
                    System.out.println("Author " + i + " = " + listBooksInSharedPrefs.get(4).get(i));
                }

                hasToUpdateListBooksRecyclerView = true;
            }
        });

        serieRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                serieRadioButton.setChecked(true);
                currentIndexInSharedPrefs = serieIndexInSharedPrefs;
                hasToUpdateListBooksRecyclerView = true;
            }
        });

        addDateRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                addDateRadioButton.setChecked(true);
                currentIndexInSharedPrefs = addDateIndexInSharedPrefs;
                hasToUpdateListBooksRecyclerView = true;
            }
        });

        releaseDateRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                releaseDateRadioButton.setChecked(true);
                currentIndexInSharedPrefs = releaseDateIndexInSharedPrefs;
                hasToUpdateListBooksRecyclerView = true;
            }
        });

        return view;
    }

    private void uncheckAllRadioButtons() {
        titleRadioButton.setChecked(false); // Unchecked the title radio button
        authorRadioButton.setChecked(false); // Unchecked the author radio button
        serieRadioButton.setChecked(false); // Unchecked the serie radio button
        addDateRadioButton.setChecked(false); // Unchecked the add date radio button
        releaseDateRadioButton.setChecked(false); // Unchecked the release date radio button
    }
}