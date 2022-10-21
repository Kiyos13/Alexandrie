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

        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                updateFragments();

                if (filterFragment instanceof FilterFragment) {
                    getParentFragmentManager().beginTransaction().remove(filterFragment).commit();
                    filterIsDisplayed = false;
                }
                else {
                    if (orderIsDisplayed) {
                        getParentFragmentManager().beginTransaction().remove(orderFragment).commit();
                        orderIsDisplayed = false;
                    }
                    if (searchIsDisplayed) {
                        getParentFragmentManager().beginTransaction().remove(searchFragment).commit();
                        searchIsDisplayed = false;
                    }

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
                    getParentFragmentManager().beginTransaction().remove(orderFragment).commit();
                    orderIsDisplayed = false;
                }
                else {
                    if (filterIsDisplayed) {
                        getParentFragmentManager().beginTransaction().remove(filterFragment).commit();
                        filterIsDisplayed = false;
                    }
                    if (searchIsDisplayed) {
                        getParentFragmentManager().beginTransaction().remove(searchFragment).commit();
                        searchIsDisplayed = false;
                    }

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
                    getParentFragmentManager().beginTransaction().remove(searchFragment).commit();
                    searchIsDisplayed = false;
                }
                else {
                    if (filterIsDisplayed) {
                        getParentFragmentManager().beginTransaction().remove(filterFragment).commit();
                        filterIsDisplayed = false;
                    }
                    if (orderIsDisplayed) {
                        getParentFragmentManager().beginTransaction().remove(orderFragment).commit();
                        orderIsDisplayed = false;
                    }

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

    private void updateFragments() {
        filterFragment = getParentFragmentManager().findFragmentById(R.id.filterFragContainerV);
        orderFragment = getParentFragmentManager().findFragmentById(R.id.orderFragContainerV);
        searchFragment = getParentFragmentManager().findFragmentById(R.id.searchFragContainerV);
    }
}