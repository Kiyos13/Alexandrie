package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginForgottenPasswordActivity extends AppCompatActivity {

    private Button sendEmailButton;
    private TextInputLayout emailInputLyt;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgotten_password);
        colorSystemBarTop(getWindow(), getResources(), this);

        sendEmailButton = findViewById(R.id.sendEmailBtn);
        emailInputLyt = findViewById(R.id.emailInputLyt);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetEmail();
                boolean emailIsEmpty = EmailIsEmpty();
                if (!emailIsEmpty) {

                }
            }
        });
    }

    private void SetEmail() {
        email = emailInputLyt.getEditText().getText().toString();
    }

    private boolean EmailIsEmpty() {
        boolean emptyEmail = (email.length() == 0);
        String errorTitle = "Erreur", errorText = "L'email que vous avez entré est vide.";
        if (emptyEmail) {
            FragmentContainerView menuFragContainerV = findViewById(R.id.popupFPFragContainerV);
            TextView titleTxtV = menuFragContainerV.findViewById(R.id.titleError);
            TextView textTxtV = menuFragContainerV.findViewById(R.id.textError);
            titleTxtV.setText(errorTitle);
            textTxtV.setText(errorText);
            menuFragContainerV.setVisibility(View.VISIBLE);
            return true;
        }

        return false;
    }
}