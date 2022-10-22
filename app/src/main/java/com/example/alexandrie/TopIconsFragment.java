package com.example.alexandrie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TopIconsFragment extends Fragment implements View.OnClickListener {

    private ImageView filterIcon, orderIcon, searchIcon;
    private Fragment filterFragment, orderFragment, searchFragment;
    private FragmentManager fragmentManager;
    // Booleans to display one fragment at a time (filter or order or search)
    private Boolean filterIsDisplayed, orderIsDisplayed, searchIsDisplayed;

    public TopIconsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_icons, container, false);

        filterIcon = view.findViewById(R.id.filterImgV);
        orderIcon = view.findViewById(R.id.orderImgV);
        searchIcon = view.findViewById(R.id.searchImgV);
        filterIsDisplayed = false;
        orderIsDisplayed = false;
        searchIsDisplayed = false;

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