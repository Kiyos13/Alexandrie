package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AccountSettingsActivity extends AppCompatActivity
{
    private TextView userNameTxtV, emailTxtV, passwordTxtV;
    private TextInputLayout userNameTxtLyt, emailTxtLyt, passwordTxtLyt;
    private android.widget.Button editAccountButton, saveAccountButton;
    private String activityToReturnStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        userNameTxtV = findViewById(R.id.userNameAccountSettingsTxtV);
        emailTxtV = findViewById(R.id.emailAccountSettingsTxtV);
        passwordTxtV = findViewById(R.id.passwordAccountSettingsTxtV);
        userNameTxtLyt = findViewById(R.id.userNameAccountInputLyt);
        emailTxtLyt = findViewById(R.id.emailAccountInputLyt);
        passwordTxtLyt = findViewById(R.id.passwordAccountInputLyt);
        editAccountButton = findViewById(R.id.editAccountSettingsBtn);
        saveAccountButton = findViewById(R.id.saveAccountSettingsBtn);

        Bundle bundle = getIntent().getExtras();
        activityToReturnStr = bundle.getString("activity");

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent;
        if (activityToReturnStr.equals("ListBooksActivity")) {
            intent = new Intent(AccountSettingsActivity.this, ListBooksActivity.class);
            fragmentManager.beginTransaction().add(R.id.topBarAccSetFragContainerV, new AppBarFragment(intent)).commit();
        }
        else if (activityToReturnStr.equals("ActivityDisplayMenu")) {
            intent = new Intent(AccountSettingsActivity.this, ActivityDisplayMenu.class);
            fragmentManager.beginTransaction().add(R.id.topBarAccSetFragContainerV, new AppBarFragment(intent)).commit();
        }

        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccountButton.setVisibility(View.GONE);
                saveAccountButton.setVisibility(View.VISIBLE);
                userNameTxtV.setVisibility(View.GONE);
                emailTxtV.setVisibility(View.GONE);
                passwordTxtV.setVisibility(View.GONE);
                userNameTxtLyt.setVisibility(View.VISIBLE);
                emailTxtLyt.setVisibility(View.VISIBLE);
                passwordTxtLyt.setVisibility(View.VISIBLE);

            }
        });

        saveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccountButton.setVisibility(View.VISIBLE);
                saveAccountButton.setVisibility(View.GONE);
                userNameTxtV.setVisibility(View.VISIBLE);
                emailTxtV.setVisibility(View.VISIBLE);
                passwordTxtV.setVisibility(View.VISIBLE);
                userNameTxtLyt.setVisibility(View.GONE);
                emailTxtLyt.setVisibility(View.GONE);
                passwordTxtLyt.setVisibility(View.GONE);

            }
        });
    }
}