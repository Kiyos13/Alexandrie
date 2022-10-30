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

        deleteBookButton = view.findViewById(R.id.deleteBookBtn);

        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                int nbSelectedBookItems = indexListSelectedItemBooks.size();

                int key;
                Map<String, ?> allEntries = sharedPrefBooks.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    key = Integer.parseInt(entry.getKey());
                    System.out.println("key = " + key + " data = " + entry.getValue().toString());


                    for (int i = 0; i < nbSelectedBookItems; i++) {
                        System.out.println("indexListSelectedItemBooks.get(i) = " + indexListSelectedItemBooks.get(i));
                        if (key == indexListSelectedItemBooks.get(i)) {
                            SharedPreferences.Editor editor = sharedPrefBooks.edit();
                            editor.remove(String.valueOf(key));
                            editor.apply();
                            Activity activity = getActivity();
                            activity.finish();
                            startActivity(activity.getIntent());
                        }
                    }
                }
            }
        });

        return view;
    }
}