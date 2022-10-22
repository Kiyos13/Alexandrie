package com.example.alexandrie;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ReturnArrowFragment extends Fragment {

    private ImageView returnArrowIcon;
    private Intent returnIntent;

    public ReturnArrowFragment() {
        // Required empty public constructor
    }

    public ReturnArrowFragment(Intent intent) {
        // Set activity to return to
        returnIntent = intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_arrow, container, false);

        returnArrowIcon = view.findViewById(R.id.returnArrowImgV);

        // Return arrow click listener
        returnArrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Replace current activity by the one given as parameter on return arrow creation
                startActivity(returnIntent);
            }
        });

        return view;
    }
}