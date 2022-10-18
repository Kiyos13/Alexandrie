package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

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
    private String errorTitle, errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgotten_password);
        colorSystemBarTop(getWindow(), getResources(), this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(LoginForgottenPasswordActivity.this, LoginConnectionActivity.class);
        fragmentManager.beginTransaction().add(R.id.returnArrowFPFragContainerV, new ReturnArrowFragment(returnIntent)).commit();

        sendEmailButton = findViewById(R.id.sendEmailBtn);
        emailInputLyt = findViewById(R.id.emailInputLyt);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetEmail();
                boolean emailIsEmpty = EmailIsEmpty();
                if (!emailIsEmpty) {
                    // TODO : send email with reset password
                }
            }
        });
    }

    private void SetEmail() {
        email = emailInputLyt.getEditText().getText().toString();
    }

    private boolean EmailIsEmpty() {
        boolean emptyEmail = (email.length() == 0);
        if (emptyEmail) {
            errorTitle = "Erreur";
            errorText = "L'email que vous avez entré est vide.";
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.popupFPFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
            return true;
        }
        return false;
    }
}