package com.example.alexandrie;


import static com.example.alexandrie.LoginConnectionActivity.enableDisableView;
import static com.example.alexandrie.MenuFragment.isRightHand;
import static com.example.alexandrie.OneBookAllInfoActivity.genresListTxtVEdit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Arrays;

public class GenresSelectorFragment extends Fragment {

    private RecyclerView genresSelectorRecyclerView;
    private ScrollView scrollViewToEnableDisable;
    private android.widget.Button okButton;
    public static RecyclerView.Adapter genresSelectorAdapter;
    public static ArrayList<String> listBookGenres = new ArrayList<>(), listBookKeptGenres = new ArrayList<>();
    public static ArrayList<Boolean> listBookGenresSelected = new ArrayList<>();
    public static ObservableInteger hasToExecuteFilter = new ObservableInteger();
    private View viewToEnableDisable;
    private String modeFilterOrNot;

    public GenresSelectorFragment(View view, String mode) {
        viewToEnableDisable = view;
        scrollViewToEnableDisable = null;
        modeFilterOrNot = mode;
    }

    public GenresSelectorFragment(View view, ScrollView scrollView, String mode) {
        viewToEnableDisable = view;
        scrollViewToEnableDisable = scrollView;
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

        /*
        genresSelectorRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isMaxScrollReached(genresSelectorRecyclerView))
                    genresSelectorRecyclerView.smoothScrollToPosition(0);
            }
        });
        */

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

                    // Enable scrolling
                    if (scrollViewToEnableDisable != null)
                        scrollViewToEnableDisable.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
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
                    if (isRightHand == null)
                        fragmentManager.beginTransaction().replace(R.id.addDelFragContainerVRight, new AddFragment()).commit();
                    else {
                        if (isRightHand)
                            fragmentManager.beginTransaction().replace(R.id.addDelFragContainerVRight, new AddFragment()).commit();
                        else
                            fragmentManager.beginTransaction().replace(R.id.addDelFragContainerVLeft, new AddFragment()).commit();
                    }
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

    static private boolean isMaxScrollReached(RecyclerView recyclerView) {
        int maxScroll = recyclerView.computeVerticalScrollRange();
        int currentScroll = recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent();
        return currentScroll >= maxScroll;
    }

    public static void initListBookGenresAndListBookGenresSelected(String[] genresArray) {
        listBookGenres = new ArrayList<>(Arrays.asList(genresArray));
        for (int i = 0; i < listBookGenres.size(); i++)
            listBookGenresSelected.add(false);
    }
}