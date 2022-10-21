package com.example.alexandrie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private View semitransparentView;
    private Button accountButton, logoutButton, homePageButton;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        semitransparentView = view.findViewById(R.id.semitransparentView);
        accountButton = view.findViewById(R.id.accountBtn);
        logoutButton = view.findViewById(R.id.logoutBtn);
        homePageButton = view.findViewById(R.id.homePageBtn);

        semitransparentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
                startActivity(new Intent(getActivity(), LoginConnectionActivity.class));
            }
        });

        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
                startActivity(new Intent(getActivity(), ListBooksActivity.class)); // TODO : change horizontal lists
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) { }
}