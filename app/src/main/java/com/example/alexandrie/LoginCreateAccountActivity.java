package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.SharedPrefLength;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;
import static com.example.alexandrie.LoginConnectionActivity.sharedPrefLogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginCreateAccountActivity extends AppCompatActivity {

    private android.widget.Button createAccountButton;
    private TextInputLayout identifierInputLyt, passwordInputLyt, repeatPasswordInputLyt, emailInputLyt;
    private String identifier, password, secondPassword, email;
    private String errorTitle, errorText;
    public static int indexInSharedPrefLogsIndex = 0, indexInSharedPrefLogsIdentifier = 1;
    public static int indexInSharedPrefLogsPassword = 2, indexInSharedPrefLogsEmail = 3;
    public static int nbFieldsInSharedPrefLogs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create_account);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        // Put a new ReturnArrowFragment in the returnArrowCrFragContainerV in LoginConnectionActivity
        // Link the ReturnArrowFragment to the LoginConnectionActivity
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(LoginCreateAccountActivity.this, LoginConnectionActivity.class);
        fragmentManager.beginTransaction().add(R.id.topBarLogCrFragContainerV, new AppBarFragment(returnIntent)).commit();

        createAccountButton = findViewById(R.id.createAcountBtn);
        identifierInputLyt = findViewById(R.id.identifierInputLyt);
        passwordInputLyt = findViewById(R.id.passwordInputLyt);
        repeatPasswordInputLyt = findViewById(R.id.repeatPasswordInputLyt);
        emailInputLyt = findViewById(R.id.emailInputLyt);

        // Create account button click listener
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUserInfos(); // Retrieve and update user infos from TextInputLayouts
                boolean infosAreValid = UserNameEmailPasswordAreValid(); // Checks infos are valid

                if (infosAreValid) {
                    if (UserNameIsFree(sharedPrefLogs, identifier)) {
                        // If the infos are valid and the user name is not already taken, creation of a new account in SharedPreferences
                        AddAccountToSharedPrefs(sharedPrefLogs);
                        // Return to the LoginConnectionActivity
                        startActivity(new Intent(LoginCreateAccountActivity.this, LoginConnectionActivity.class));
                    }
                    else {
                        System.out.println("\tUser name already taken !");
                        // Display error message with MessagePopupFragment
                        errorTitle = "Erreur de création de compte";
                        errorText = "Malheureusement, le nom d'utilisateur que vous avez entré est déjà pris. Veuillez en enter un autre différent.";
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.popupCrFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
                    }
                }
            }
        });
    }

    // Set the user information
    private void SetUserInfos() {
        identifier = identifierInputLyt.getEditText().getText().toString(); // Set user name
        password = passwordInputLyt.getEditText().getText().toString(); // Set password
        secondPassword = repeatPasswordInputLyt.getEditText().getText().toString(); // Set password confirmation
        email = emailInputLyt.getEditText().getText().toString(); // Set email
    }

    // Checks that the password and its confirmation are the same
    private boolean FirstAndSecondPasswordsAreEquals() {
        return password.equals(secondPassword);
    }

    // Checks that the user name, the email and the password are valid (not empty)
    private boolean UserNameEmailPasswordAreValid() {
        boolean emptyUserName = (identifier.length() == 0);
        boolean emptyPassword = (password.length() == 0);
        boolean emptyEmail = (email.length() == 0);
        boolean passwordsAreEquals = FirstAndSecondPasswordsAreEquals();
        boolean infosAreValid = true;
        errorTitle = "Erreur de création de compte";
        errorText = "";

        if (emptyUserName || emptyPassword || emptyEmail || !passwordsAreEquals)
            infosAreValid = false;
        if (emptyUserName && emptyPassword && emptyEmail)
            errorText = "Votre nom d'utilisateur, votre mot de passe et votre email sont vide.";
        else if (emptyUserName && emptyPassword)
            errorText = "Votre nom d'utilisateur et votre mot de passe sont vide.";
        else if (emptyUserName && emptyEmail)
            errorText = "Votre nom d'utilisateur et votre email sont vide.";
        else if (emptyPassword && emptyEmail)
            errorText = "Votre mot de passe et votre email sont vide.";
        else if (emptyUserName)
            errorText = "Votre nom d'utilisateur est vide.";
        else if (emptyPassword)
            errorText = "Votre mot de passe est vide.";
        else if (emptyEmail)
            errorText = "Votre email est vide.";

        if (!passwordsAreEquals)
            errorText += "Votre mot de passe et sa confirmation sont différents.";

        if (!infosAreValid) {
            // Display error message if information is not valid
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.popupCrFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
        }

        return infosAreValid;
    }

    // Checks that the user name is free or not (doesn't already exists)
    private boolean UserNameIsFree(SharedPreferences sharedPrefs, String name) {
        int nbAccounts = 0;
        Set<String> currentSet;
        List<String> currentAccount;
        String currentUserName;
        Map<String, ?> allEntries = sharedPrefs.getAll(); // Retrieve the accounts from SharedPreferences
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            nbAccounts++;
            // Retrieve current set in SharedPreferences
            currentSet = sharedPrefs.getStringSet(String.valueOf(nbAccounts), new HashSet<>());
            currentAccount = new ArrayList<String>(currentSet);
            SortStringListByFirstChar(currentAccount); // Sort the set
            currentUserName = currentAccount.get(indexInSharedPrefLogsIdentifier); // Retrieve user name from set
            currentUserName = currentUserName.substring(2); // Removing 2 firsts char from user name : "1_"
            // If the current user name equals the input user name then it's already taken and the user will have to choose another one
            if (currentUserName.equals(name))
                return false;
        }
        return true;
    }

    // Add an account to the SharedPreferences
    private void AddAccountToSharedPrefs(SharedPreferences sharedPrefs) {
        LinkedHashSet<String> hashSetAccount = new LinkedHashSet<>(); // New hashSet to add to the SharedPreferences

        // Retrieve the number of already existing accounts
        int nbAccountsAlreadyExisting = SharedPrefLength(sharedPrefs);
        nbAccountsAlreadyExisting++;

        // Add information to the hashSet
        // Add numbers before info to sort it later on retrieve
        hashSetAccount.add("0_" + String.valueOf(nbAccountsAlreadyExisting));
        hashSetAccount.add("1_" + identifier);
        hashSetAccount.add("2_" + password);
        hashSetAccount.add("3_" + email);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(String.valueOf(nbAccountsAlreadyExisting), hashSetAccount); // Add set to SharedPreferences
        editor.commit();
    }
}