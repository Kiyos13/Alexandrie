package com.example.alexandrie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BottomFragment extends Fragment implements View.OnClickListener {

    private ImageView menuIcon, switchIcon, shwocaseIcon;

    public BottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

        menuIcon = view.findViewById(R.id.menuImgV);
        switchIcon = view.findViewById(R.id.switchImgV);
        shwocaseIcon = view.findViewById(R.id.showcaseImgV);

        // Menu click listener
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Put a new MenuFragment in the menuFragContainerV or menuDisplayMenuFragContainerV in BottomFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                Activity currentActivity = getActivity();

                if (currentActivity instanceof ListBooksActivity)
                    fragmentManager.beginTransaction().replace(R.id.menuFragContainerV, new MenuFragment()).commit();
                else if (currentActivity instanceof ActivityDisplayMenu)
                    fragmentManager.beginTransaction().replace(R.id.menuDisplayMenuFragContainerV, new MenuFragment()).commit();
                else if (currentActivity instanceof FavoritesShowcaseActivity)
                    fragmentManager.beginTransaction().replace(R.id.menuFavoritesShowcaseFragContainerV, new MenuFragment()).commit();
            }
        });

        // Switch click listener
        switchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Switch activity between ListBooksActivity and ActivityDisplayMenu
                Activity currentActivity = getActivity();

                if (currentActivity instanceof ActivityDisplayMenu)
                    startActivity(new Intent(currentActivity, ListBooksActivity.class));
                else
                    startActivity(new Intent(currentActivity, ActivityDisplayMenu.class));
            }
        });

        // Menu click listener
        shwocaseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Switch activity to Favorites showcase
                Activity currentActivity = getActivity();

                if (!(currentActivity instanceof FavoritesShowcaseActivity))
                    startActivity(new Intent(currentActivity, FavoritesShowcaseActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) { }
}