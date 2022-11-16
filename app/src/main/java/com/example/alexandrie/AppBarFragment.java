package com.example.alexandrie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppBarFragment extends Fragment implements View.OnClickListener {

    private ImageView returnArrowIcon;
    private Intent returnIntent;
    private ImageView filterIcon, orderIcon, searchIcon, returnArrow;
    private Fragment filterFragment, orderFragment, searchFragment;
    private FragmentManager fragmentManager;
    // Booleans to display one fragment at a time (filter or order or search)
    private Boolean filterIsDisplayed, orderIsDisplayed, searchIsDisplayed, hasFilterIcons, hasReturnArrow, hasEditMode;
    private View iconsLyt;
    private TextView editModeTxtV;
    private String previousActivity;
    private String[] bookStrings;
    private Boolean[] bookBooleans;

    public AppBarFragment() {
        // Required empty public constructor
        hasFilterIcons = false;
        hasReturnArrow = false;
        hasEditMode = false;
    }

    public AppBarFragment(Intent intent) {
        // Set activity to return to
        returnIntent = intent;
        hasReturnArrow = true;
        hasFilterIcons = false;
        hasEditMode = false;
    }

    public AppBarFragment(Boolean hasIcons) {
        // Set activity to return to
        hasReturnArrow = false;
        hasFilterIcons = hasIcons;
        hasEditMode = false;
    }

    public AppBarFragment(Intent intent, String edit, String[] strings, Boolean[] booleans) {
        // Set activity to return to
        returnIntent = intent;
        hasReturnArrow = true;
        hasFilterIcons = false;
        hasEditMode = true;
        previousActivity = edit;
        bookStrings = strings;
        bookBooleans = booleans;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar, container, false);

        returnArrowIcon = view.findViewById(R.id.returnArrowImgV);
        filterIcon = view.findViewById(R.id.filterImgV);
        orderIcon = view.findViewById(R.id.orderImgV);
        searchIcon = view.findViewById(R.id.searchImgV);
        editModeTxtV = view.findViewById(R.id.editModeAppBar);

        filterIsDisplayed = false;
        orderIsDisplayed = false;
        searchIsDisplayed = false;

        if (hasFilterIcons) {
            iconsLyt = view.findViewById(R.id.topBarFilterIconsLyt);
            iconsLyt.setVisibility(View.VISIBLE);
        }
        if (hasReturnArrow) {
            returnArrow = view.findViewById(R.id.returnArrowImgV);
            returnArrow.setVisibility(View.VISIBLE);
        }
        if (hasEditMode) {
            editModeTxtV.setVisibility(View.VISIBLE);
        }

        // Return arrow click listener
        returnArrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Replace current activity by the one given as parameter on return arrow creation
                startActivity(returnIntent);
            }
        });

        // Filter click listener
        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                updateFragments();

                if (filterFragment instanceof FilterFragment) {
                    // Remove filterFragment from current activity if it's already displayed (second click remove fragment)
                    getParentFragmentManager().beginTransaction().remove(filterFragment).commit();
                    filterIsDisplayed = false;
                }
                else {
                    if (orderIsDisplayed) {
                        // Remove orderFragment from current activity
                        getParentFragmentManager().beginTransaction().remove(orderFragment).commit();
                        orderIsDisplayed = false;
                    }
                    if (searchIsDisplayed) {
                        // Remove searchFragment from current activity
                        getParentFragmentManager().beginTransaction().remove(searchFragment).commit();
                        searchIsDisplayed = false;
                    }

                    // Display filter fragment
                    fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.filterFragContainerV, new FilterFragment()).commit();
                    filterIsDisplayed = true;
                }
            }
        });

        orderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                updateFragments();

                if (orderFragment instanceof OrderFragment) {
                    // Remove orderFragment from current activity if it's already displayed (second click remove fragment)
                    getParentFragmentManager().beginTransaction().remove(orderFragment).commit();
                    orderIsDisplayed = false;
                }
                else {
                    if (filterIsDisplayed) {
                        // Remove filterFragment from current activity
                        getParentFragmentManager().beginTransaction().remove(filterFragment).commit();
                        filterIsDisplayed = false;
                    }
                    if (searchIsDisplayed) {
                        // Remove searchFragment from current activity
                        getParentFragmentManager().beginTransaction().remove(searchFragment).commit();
                        searchIsDisplayed = false;
                    }

                    // Display order fragment
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.orderFragContainerV, new OrderFragment()).commit();
                    orderIsDisplayed = true;
                }
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                updateFragments();

                if (searchFragment instanceof SearchFragment) {
                    // Remove searchFragment from current activity if it's already displayed (second click remove fragment)
                    getParentFragmentManager().beginTransaction().remove(searchFragment).commit();
                    searchIsDisplayed = false;
                }
                else {
                    if (filterIsDisplayed) {
                        // Remove filterFragment from current activity
                        getParentFragmentManager().beginTransaction().remove(filterFragment).commit();
                        filterIsDisplayed = false;
                    }
                    if (orderIsDisplayed) {
                        // Remove orderFragment from current activity
                        getParentFragmentManager().beginTransaction().remove(orderFragment).commit();
                        orderIsDisplayed = false;
                    }

                    // Display search fragment
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.searchFragContainerV, new SearchFragment()).commit();
                    searchIsDisplayed = true;
                }
            }
        });

        editModeTxtV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                Activity activity = getActivity();
                if (activity instanceof OneBookAllInfoActivity) {
                    // Replace current activity by the same but in edit mode
                    Intent intent = new Intent(activity, OneBookAllInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mode", "edit");
                    bundle.putString("prevActivity", previousActivity);
                    bundle.putString("indexInSharedPrefs", bookStrings[0]);
                    bundle.putString("title", bookStrings[1]);
                    bundle.putString("volume", bookStrings[2]);
                    bundle.putString("serie", bookStrings[3]);
                    bundle.putString("author", bookStrings[4]);
                    bundle.putString("addDate", bookStrings[5]);
                    bundle.putString("releaseDate", bookStrings[6]);
                    bundle.putString("description", bookStrings[7]);
                    bundle.putString("summary", bookStrings[8]);
                    bundle.putString("mark", bookStrings[9]);
                    bundle.putString("tag1", bookStrings[10]);
                    bundle.putString("tag2", bookStrings[11]);
                    bundle.putString("tag3", bookStrings[12]);
                    bundle.putBoolean("readStatus", bookBooleans[0]);
                    bundle.putBoolean("isFavorite", bookBooleans[1]);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) { }

    // Update fragments from fragmentContainers
    private void updateFragments() {
        filterFragment = getParentFragmentManager().findFragmentById(R.id.filterFragContainerV);
        orderFragment = getParentFragmentManager().findFragmentById(R.id.orderFragContainerV);
        searchFragment = getParentFragmentManager().findFragmentById(R.id.searchFragContainerV);
    }
}