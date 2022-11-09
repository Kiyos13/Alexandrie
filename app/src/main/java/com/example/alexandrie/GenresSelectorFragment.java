package com.example.alexandrie;


import static com.example.alexandrie.LoginConnectionActivity.enableDisableView;
import static com.example.alexandrie.OneBookAllInfoActivity.genresListTxtVEdit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class GenresSelectorFragment extends Fragment {

    private RecyclerView genresSelectorRecyclerView;
    private android.widget.Button okButton;
    public static RecyclerView.Adapter genresSelectorAdapter;
    public static ArrayList<String> listBookGenres = new ArrayList<>(), listBookKeptGenres = new ArrayList<>();
    public static ArrayList<Boolean> listBookGenresSelected = new ArrayList<>();
    public static ObservableInteger hasToExecuteFilter = new ObservableInteger();
    private View viewToEnableDisable;
    private String modeFilterOrNot;

    public GenresSelectorFragment(View view, String mode) {
        viewToEnableDisable = view;
        modeFilterOrNot = mode;
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

                if (modeFilterOrNot.equals("nonFilter")) {
                    // Set the list in genresListOneBookTxtVEdit
                    genresListTxtVEdit.setText("");
                    for (int i = 0; i < listBookGenresSelected.size(); i++) {
                        if (listBookGenresSelected.get(i)) {
                            if (genresListTxtVEdit.getText().length() != 0)
                                genresListTxtVEdit.setText(genresListTxtVEdit.getText() + "\n" + listBookGenres.get(i));
                            else
                                genresListTxtVEdit.setText(listBookGenres.get(i));
                        }
                    }
                }
                else if (modeFilterOrNot.equals("filter")) {
                    listBookKeptGenres = new ArrayList<>();
                    for (int i = 0; i < listBookGenresSelected.size(); i++) {
                        if (listBookGenresSelected.get(i)) {
                            listBookKeptGenres.add(listBookGenres.get(i));
                        }
                    }
                    // Notify the filter fragment that it has to execute the filters
                    hasToExecuteFilter.set(1);

                    // Add / Display AddFragment
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.addDelFragContainerV, new AddFragment()).commit();
                }

                // Enable view
                if (viewToEnableDisable != null)
                    enableDisableView(viewToEnableDisable, true);
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