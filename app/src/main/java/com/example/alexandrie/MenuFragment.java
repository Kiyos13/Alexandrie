package com.example.alexandrie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private View semitransparentView; // View to the right of the menu
    private Button accountButton, logoutButton, homePageButton;
    private com.google.android.material.button.MaterialButton lightThemeBtn, darkThemeBtn, leftHandBtn, rightHandBtn;
    public static Boolean isLightTheme, isRightHand;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        semitransparentView = view.findViewById(R.id.semitransparentView);
        accountButton = view.findViewById(R.id.accountBtn);
        logoutButton = view.findViewById(R.id.logoutBtn);
        homePageButton = view.findViewById(R.id.homePageBtn);
        lightThemeBtn = view.findViewById(R.id.lightThemeBtn);
        darkThemeBtn = view.findViewById(R.id.darkThemeBtn);
        leftHandBtn = view.findViewById(R.id.leftHandBtn);
        rightHandBtn = view.findViewById(R.id.rightHandBtn);

        // View to the right of the menu click listener : to close the menu
        semitransparentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Remove MenuFragment from activity
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
            }
        });

        // Account click listener
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Remove MenuFragment
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
                // Change activity by AccountSettingsActivity
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                Bundle bundle = new Bundle();
                List<String> activityStringList = new ArrayList<String>(Arrays.asList(getActivity().toString().split("@")));
                String activityWithoutEnd = activityStringList.get(0);
                activityStringList = new ArrayList<String>(Arrays.asList(activityWithoutEnd.split("\\.")));
                String activityToPass = activityStringList.get(3);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Logout click listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Remove MenuFragment
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
                // Change activity by LoginConnectionActivity (starting activity)
                startActivity(new Intent(getActivity(), LoginConnectionActivity.class));
            }
        });

        // Home page click listener
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Remove MenuFragment
                getParentFragmentManager().beginTransaction().remove(MenuFragment.this).commit();
                // Change activity by ... (horizontals lists activity)
                startActivity(new Intent(getActivity(), ListBooksActivity.class)); // TODO : change horizontal lists
            }
        });

        // Light theme click listener
        lightThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLightTheme == null)
                    setLightTheme();
                else {
                    if (!isLightTheme)
                        setLightTheme();
                }
            }
        });

        // Dark theme click listener
        darkThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLightTheme == null)
                    setDarkTheme();
                else {
                    if (isLightTheme)
                        setDarkTheme();
                }
            }
        });

        // Left hand click listener
        leftHandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRightHand == null)
                    setLeftHand();
                else {
                    if (isRightHand)
                        setLeftHand();
                }
            }
        });

        // Right hand click listener
        rightHandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRightHand == null)
                    setRightHand();
                else {
                    if (!isRightHand)
                        setRightHand();
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view)
    { }

    /******** THEME ********/

    private void setLightTheme() {
        isLightTheme = true;

        lightThemeBtn.setTextColor(getResources().getColor(R.color.foreground_color));
        lightThemeBtn.setBackgroundColor(getResources().getColor(R.color.background_color));
        darkThemeBtn.setTextColor(getResources().getColor(R.color.background_color));
        darkThemeBtn.setBackgroundColor(getResources().getColor(R.color.foreground_color));

        switchToLightColors();
    }

    private void setDarkTheme() {
        isLightTheme = false;

        lightThemeBtn.setTextColor(getResources().getColor(R.color.background_color));
        lightThemeBtn.setBackgroundColor(getResources().getColor(R.color.foreground_color));
        darkThemeBtn.setTextColor(getResources().getColor(R.color.foreground_color));
        darkThemeBtn.setBackgroundColor(getResources().getColor(R.color.background_color));

        switchToDarkColors();
    }

    private void switchToLightColors() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void switchToDarkColors() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    /******** HAND ********/

    private void setLeftHand() {
        isRightHand = false;

        leftHandBtn.setTextColor(getResources().getColor(R.color.foreground_color));
        leftHandBtn.setBackgroundColor(getResources().getColor(R.color.background_color));
        rightHandBtn.setTextColor(getResources().getColor(R.color.background_color));
        rightHandBtn.setBackgroundColor(getResources().getColor(R.color.foreground_color));
    }

    private void setRightHand() {
        isRightHand = true;

        leftHandBtn.setTextColor(getResources().getColor(R.color.background_color));
        leftHandBtn.setBackgroundColor(getResources().getColor(R.color.foreground_color));
        rightHandBtn.setTextColor(getResources().getColor(R.color.foreground_color));
        rightHandBtn.setBackgroundColor(getResources().getColor(R.color.background_color));
    }
}