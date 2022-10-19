package com.example.alexandrie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopIconsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopIconsFragment extends Fragment implements View.OnClickListener {

    private ImageView filterIcon, orderIcon, searchIcon;
    private Fragment filterFragment, orderFragment, searchFragment;
    private FragmentManager fragmentManager;
    private Boolean filterIsDisplayed, orderIsDisplayed, searchIsDisplayed;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopIconsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopIconsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopIconsFragment newInstance(String param1, String param2) {
        TopIconsFragment fragment = new TopIconsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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