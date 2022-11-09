package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.OneBookAllInfoActivity.genresListOneBookTxtVEdit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenresSelectorFragment extends Fragment {

    private RecyclerView genresSelectorRecyclerView;
    private android.widget.Button okButton;
    public static RecyclerView.Adapter genresSelectorAdapter;
    public static ArrayList<String> listBookGenres = new ArrayList<>();
    public static ArrayList<Boolean> listBookGenresSelected = new ArrayList<>();

    public GenresSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres_selector, container, false);


        String[] genresArray = getResources().getStringArray(R.array.all_book_genres);
        initListBookGenresAndListBookGenresSelected(genresArray);

        genresSelectorRecyclerView = view.findViewById(R.id.genresSelectorRecyclerView);
        okButton = view.findViewById(R.id.genresSelectorOkBtn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove genres selector fragment on click on Ok button
                getParentFragmentManager().beginTransaction().remove(GenresSelectorFragment.this).commit();

                // Set the list in genresListOneBookTxtVEdit
                genresListOneBookTxtVEdit.setText("");
                for (int i = 0; i < listBookGenresSelected.size(); i++) {
                    if (listBookGenresSelected.get(i)) {
                        if (genresListOneBookTxtVEdit.getText().length() != 0)
                            genresListOneBookTxtVEdit.setText(genresListOneBookTxtVEdit.getText() + "\n" + listBookGenres.get(i));
                        else
                            genresListOneBookTxtVEdit.setText(listBookGenres.get(i));
                    }
                }
            }
        });

        // Creation of the genre selector adapter
        genresSelectorAdapter = new GenresSelectorAdapter(getActivity());
        genresSelectorRecyclerView.setAdapter(genresSelectorAdapter); // Link the adapter to the recyclerView
        genresSelectorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public static void initListBookGenresAndListBookGenresSelected(String[] genresArray) {
        listBookGenres = new ArrayList<>(Arrays.asList(genresArray));
        for (int i = 0; i < listBookGenres.size(); i++)
            listBookGenresSelected.add(false);
    }
}