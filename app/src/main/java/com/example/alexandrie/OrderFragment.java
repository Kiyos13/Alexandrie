package com.example.alexandrie;

import static com.example.alexandrie.ConcurrentSort.concurrentSort;
import static com.example.alexandrie.ListBooksActivity.booksAdapter;
import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private RadioButton titleRadioButton, authorRadioButton, serieRadioButton, addDateRadioButton, releaseDateRadioButton;
    private Integer titleIndexInSharedPrefs = 1, authorIndexInSharedPrefs = 4, serieIndexInSharedPrefs = 3;
    private Integer addDateIndexInSharedPrefs = 10, releaseDateIndexInSharedPrefs = 11;
    public static Integer currentOrderIndexInSharedPrefs = 1;
    private String ascendingOrder = "ascending", descendingOrder = "descending";
    public static String currentWayOrder = "ascending";
    private ImageView orderIcon;

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
        orderIcon = view.findViewById(R.id.orderIcon);

        checkCurrentRadioButton();

        titleRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                titleRadioButton.setChecked(true);
                currentOrderIndexInSharedPrefs = titleIndexInSharedPrefs;
                listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
                booksAdapter.notifyDataSetChanged();
            }
        });

        authorRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                authorRadioButton.setChecked(true);
                currentOrderIndexInSharedPrefs = authorIndexInSharedPrefs;
                listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
                booksAdapter.notifyDataSetChanged();
            }
        });

        serieRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                serieRadioButton.setChecked(true);
                currentOrderIndexInSharedPrefs = serieIndexInSharedPrefs;
                listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
                booksAdapter.notifyDataSetChanged();
            }
        });

        addDateRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                addDateRadioButton.setChecked(true);
                currentOrderIndexInSharedPrefs = addDateIndexInSharedPrefs;
                listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
                booksAdapter.notifyDataSetChanged();
            }
        });

        releaseDateRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllRadioButtons();
                releaseDateRadioButton.setChecked(true);
                currentOrderIndexInSharedPrefs = releaseDateIndexInSharedPrefs;
                listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
                booksAdapter.notifyDataSetChanged();
            }
        });

        orderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentWayOrder = (currentWayOrder.equals(ascendingOrder)) ? descendingOrder : ascendingOrder;
                listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));
                booksAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    // Uncheck all the radio buttons of the fragment
    private void uncheckAllRadioButtons() {
        titleRadioButton.setChecked(false); // Unchecked the title radio button
        authorRadioButton.setChecked(false); // Unchecked the author radio button
        serieRadioButton.setChecked(false); // Unchecked the serie radio button
        addDateRadioButton.setChecked(false); // Unchecked the add date radio button
        releaseDateRadioButton.setChecked(false); // Unchecked the release date radio button
    }

    // Check the current radio button according to currentOrderIndexInSharedPrefs value
    private void checkCurrentRadioButton() {
        switch (currentOrderIndexInSharedPrefs) {
            case 1:
                uncheckAllRadioButtons();
                titleRadioButton.setChecked(true);
                break;
            case 4:
                uncheckAllRadioButtons();
                authorRadioButton.setChecked(true);
                break;
            case 3:
                uncheckAllRadioButtons();
                serieRadioButton.setChecked(true);
                break;
            case 10:
                uncheckAllRadioButtons();
                addDateRadioButton.setChecked(true);
                break;
            case 11:
                uncheckAllRadioButtons();
                releaseDateRadioButton.setChecked(true);
                break;
            default:
                break;
        }
    }

    public static ArrayList<String> SortBooksArrayListOfArrayLists(int indexList, String way, int totalNbLists) {
        ArrayList<ArrayList<String>> remainingLists = new ArrayList<ArrayList<String>>();
        remainingLists.add(listBooksInSharedPrefs.get(indexList));
        for (int i = 0; i < totalNbLists; i++) {
            if (i != indexList)
                remainingLists.add(listBooksInSharedPrefs.get(i));
        }

        concurrentSort(way, listBooksInSharedPrefs.get(indexList), remainingLists);

        return listBooksInSharedPrefs.get(indexList);
    }
}