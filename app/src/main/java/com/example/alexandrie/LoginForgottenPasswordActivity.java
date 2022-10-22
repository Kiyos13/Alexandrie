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
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        // Put a new ReturnArrowFragment in the returnArrowFPFragContainerV in LoginForgottenPasswordActivity
        // Link the ReturnArrowFragment to the LoginConnectionActivity
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(LoginForgottenPasswordActivity.this, LoginConnectionActivity.class);
        fragmentManager.beginTransaction().add(R.id.returnArrowFPFragContainerV, new ReturnArrowFragment(returnIntent)).commit();

        sendEmailButton = findViewById(R.id.sendEmailBtn);
        emailInputLyt = findViewById(R.id.emailInputLyt);

        // Send email button click listener
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetEmail(); // Retrieve email from its TextInputLayout
                boolean emailIsEmpty = EmailIsEmpty(); // Check email is not empty
                // Add other validity tests
                if (!emailIsEmpty) {
                    // Send a new password (unique use) by email if it's valid
                    // TODO : send email with reset password
                }
            }
        });
    }

    // Update email attribute from its TextInputLayout
    private void SetEmail() {
        // Retrieve email text from its TextInputLayout
        email = emailInputLyt.getEditText().getText().toString();
    }

    // Boolean check : email is empty or not
    private boolean EmailIsEmpty() {
        boolean emptyEmail = (email.length() == 0);
        if (emptyEmail) {
            // If email is empty : display error message with MessagePopupFragment
            errorTitle = "Erreur";
            errorText = "L'email que vous avez entré est vide.";
            // Put a new MessagePopupFragment in the popupFPFragContainerV in LoginForgottenPasswordActivity
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.popupFPFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
            return true;
        }
        return false;
    }
}