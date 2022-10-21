package com.example.alexandrie;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessagePopupFragment extends Fragment implements View.OnClickListener {

    private android.widget.Button okButton;
    private TextView titleTxtV, textTxtV;
    private String titleTxt, textTxt;

    public MessagePopupFragment() {
        // Required empty public constructor
    }

    public MessagePopupFragment(String errorTitle, String errorText) {
        titleTxt = errorTitle;
        textTxt = errorText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_popup, container, false);

        okButton = view.findViewById(R.id.okBtn);
        titleTxtV = view.findViewById(R.id.titleError);
        textTxtV = view.findViewById(R.id.textError);
        titleTxtV.setText(titleTxt);
        textTxtV.setText(textTxt);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                getParentFragmentManager().beginTransaction().remove(MessagePopupFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) { }
}