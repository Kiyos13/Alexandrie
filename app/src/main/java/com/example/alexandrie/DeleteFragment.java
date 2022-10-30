package com.example.alexandrie;

import static com.example.alexandrie.BooksAdapter.indexListSelectedItemBooks;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DeleteFragment extends Fragment {

    private Activity activity;
    private ImageButton deleteBookButton;

    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        activity = getActivity();
        deleteBookButton = view.findViewById(R.id.deleteBookBtn);

        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                if (activity instanceof ListBooksActivity) {
                    int nbSelectedBookItems = indexListSelectedItemBooks.size();
                    int key;
                    Map<String, ?> allEntries = sharedPrefBooks.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        key = Integer.parseInt(entry.getKey());
                        for (int i = 0; i < nbSelectedBookItems; i++) {
                            if (key == indexListSelectedItemBooks.get(i)) {
                                SharedPreferences.Editor editor = sharedPrefBooks.edit();
                                editor.remove(String.valueOf(key));
                                editor.apply();
                                activity.finish();
                                startActivity(activity.getIntent());
                            }
                        }
                    }
                }
            }
        });

        return view;
    }
}