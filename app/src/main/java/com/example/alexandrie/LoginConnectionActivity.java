package com.example.alexandrie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginConnectionActivity extends AppCompatActivity {

    private android.widget.Button connectButton;
    private Button forgottenPasswordButton, createAccountButton;
    private TextInputLayout userNameInputLyt, passwordInputLyt;
    private String userName, password;
    public static SharedPreferences sharedPrefLogs;
    private String errorTitle, errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_connection);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        connectButton = findViewById(R.id.connectBtn);
        forgottenPasswordButton = findViewById(R.id.forgotPasswordBtn);
        createAccountButton = findViewById(R.id.createAccountBtn);
        userNameInputLyt = findViewById(R.id.userNameInputLyt);
        passwordInputLyt = findViewById(R.id.passwordInputLyt);
        sharedPrefLogs = getSharedPreferences("SharedPrefLogs", MODE_PRIVATE);
        // sharedPrefLogs.edit().clear().commit(); // Clean SharedPreferences

        // Connect button click listener
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUserInfosConnection(); // Retrieve and update user infos
                boolean userNameAndOrPasswordEmpty = UserNameAndOrPasswordIsEmpty(); // Check user name and password not empty
                if (!userNameAndOrPasswordEmpty) {
                    // Retrieve accounts from SharedPreferences
                    List<String>[] accounts = RetrieveDataInSharedPrefs(sharedPrefLogs);
                    for (int i = 0; i < accounts.length; i++) {
                        SortStringListByFirstChar(accounts[i]);
                    }

                    // Check the account exists and the password is good
                    int hasAccount = HasAccountAndGoodPassword(accounts);
                    if (hasAccount == 1) {
                        // Connect to the user account and change activity to the horizontals lists
                        // TODO : change the activity to the horizontals lists
                        startActivity(new Intent(LoginConnectionActivity.this, ListBooksActivity.class));
                    }
                    else if (hasAccount == 0) {
                        System.out.println("\tDoesn't have an account !");
                        // Display a message error if the account doesn't exists
                        errorTitle = "Erreur de connexion";
                        errorText = "Aucun compte ne correspond au nom d'utilisateur que vous avez entré.";
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.popupCoFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
                    }
                }
            }
        });

        // Forgotten password button click listener
        forgottenPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change activity to LoginForgottenPasswordActivity
                startActivity(new Intent(LoginConnectionActivity.this, LoginForgottenPasswordActivity.class));
            }
        });

        // Create account button click listener
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change activity to LoginCreateAccountActivity
                startActivity(new Intent(LoginConnectionActivity.this, LoginCreateAccountActivity.class));
            }
        });
    }

    // Set the color of the system bar at the top
    public static void colorSystemBarTop(Window window, Resources resources, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(resources.getColor(R.color.first_dominant_color, context.getTheme()));
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(resources.getColor(R.color.first_dominant_color));
        }
    }

    // Set user infos
    private void SetUserInfosConnection() {
        userName = userNameInputLyt.getEditText().getText().toString(); // Set user name
        password = passwordInputLyt.getEditText().getText().toString(); // Set password
    }

    // Checks if user name and password are empty
    private boolean UserNameAndOrPasswordIsEmpty() {
        boolean emptyUserName = (userName.length() == 0);
        boolean emptyPassword = (password.length() == 0);
        boolean hasToDisplayPopup = false;
        errorTitle = "Erreur de connexion";
        errorText = "";
        if (emptyUserName && emptyPassword) {
            hasToDisplayPopup = true;
            errorText = "Votre nom d'utilisateur et votre mot de passe sont vide.";
        }
        else if (emptyUserName) {
            hasToDisplayPopup = true;
            errorText = "Votre nom d'utilisateur est vide.";
        }
        else if (emptyPassword) {
            hasToDisplayPopup = true;
            errorText = "Votre mot de passe est vide.";
        }

        if (hasToDisplayPopup) {
            // Display error message according to the errors
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.popupCoFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
        }

        return hasToDisplayPopup;
    }

    // Return length of SharedPreferences
    public static int SharedPrefLength(SharedPreferences sharedPrefs) {
        int nbAccountsAlreadyExisting = 0;
        Map<String, ?> allEntries = sharedPrefs.getAll(); // Retrieve all SharedPreferences datas
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            nbAccountsAlreadyExisting++;
        }
        return nbAccountsAlreadyExisting;
    }

    // Return accounts in SharedPreferences
    private List<String>[] RetrieveDataInSharedPrefs(SharedPreferences sharedPrefs) {
        int nbAccounts = 0;
        int totalNbAccounts = SharedPrefLength(sharedPrefs); // Number of accounts
        Set<String> currentSet;
        List<String> currentAccount;
        List<String>[] accounts = new List[totalNbAccounts]; // List of all the accounts
        Map<String, ?> allEntries = sharedPrefs.getAll(); // Retrieve all SharedPreferences datas
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            nbAccounts++;
            currentSet = sharedPrefs.getStringSet(String.valueOf(nbAccounts), new HashSet<>());

            currentAccount = new ArrayList<String>(currentSet); // Add current account to the list
            accounts[nbAccounts - 1] = currentAccount;
        }
        return accounts;
    }

    // Sort a list by its first char (alphabéticly or number)
    public static void SortStringListByFirstChar(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) { return s1.compareTo(s2); }
        });
    }

    // Check the password is good and the account exists
    private int HasAccountAndGoodPassword(List<String>[] accounts) {
        List<String> currentAccountInfos;
        String currentUserName, currentPassword;
        for (int i = 0; i < accounts.length; i++) {
            currentAccountInfos = accounts[i];
            currentUserName = currentAccountInfos.get(1); // Retrieve user name
            currentUserName = currentUserName.substring(2); // user name without 2 firsts char : "1_"
            currentPassword = currentAccountInfos.get(2); // Retrieve password
            currentPassword = currentPassword.substring(2); // password without 2 firsts char : "2_"

            if (currentUserName.equals(userName) && currentPassword.equals(password))
                return 1;
            else if (currentUserName.equals(userName) && !currentPassword.equals(password)) {
                System.out.println("\tWrong password !");
                // Display error message if the password is not good but the user name exists
                errorTitle = "Erreur de connexion";
                errorText = "Votre mot de passe ne correspond pas à votre nom d'utilisateur.";
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.popupCoFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
                return 2;
            }
        }
        return 0;
    }
}