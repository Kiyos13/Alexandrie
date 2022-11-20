package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityContactUsForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_form);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top
    }
}