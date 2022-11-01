package com.example.alexandrie;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AddFragment extends Fragment {

    private ImageButton addBookButton;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        addBookButton = view.findViewById(R.id.addBookBtn);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Change activity to ScanQRCodeActivity
                // startActivity(new Intent(getActivity(), ScanQRCodeActivity.class));

                // Bypass scan activity
                // /*
                Intent intent = new Intent(getActivity(), OneBookAllInfoActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putString("mode", "see");
                bundle.putString("mode", "edit");
                //bundle.putString("prevActivity", "horizontalList");
                bundle.putString("prevActivity", "verticalList");
                intent.putExtras(bundle); //Put your id to your next Intent
                startActivity(intent);
                getActivity().finish();
                // */
            }
        });

        return view;
    }
}