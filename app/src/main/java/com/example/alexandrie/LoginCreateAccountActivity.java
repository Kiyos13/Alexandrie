package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.SharedPrefLength;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;
import static com.example.alexandrie.LoginConnectionActivity.sharedPrefLogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginCreateAccountActivity extends AppCompatActivity {

    private android.widget.Button createAccountButton;
    private TextInputLayout userNameInputLyt, passwordInputLyt, repeatPasswordInputLyt, emailInputLyt;
    private String userName, password, secondPassword, email;
    private String errorTitle, errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create_account);
        colorSystemBarTop(getWindow(), getResources(), this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(LoginCreateAccountActivity.this, LoginConnectionActivity.class);
        fragmentManager.beginTransaction().add(R.id.returnArrowCrFragContainerV, new ReturnArrowFragment(returnIntent)).commit();

        createAccountButton = findViewById(R.id.createAcountBtn);
        userNameInputLyt = findViewById(R.id.userNameInputLyt);
        passwordInputLyt = findViewById(R.id.passwordInputLyt);
        repeatPasswordInputLyt = findViewById(R.id.repeatPasswordInputLyt);
        emailInputLyt = findViewById(R.id.emailInputLyt);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUserInfos();
                boolean infosAreValid = UserNameEmailPasswordAreValid();

                if (infosAreValid) {
                    if (UserNameIsFree(sharedPrefLogs, userName))
                        AddAccountToSharedPrefs(sharedPrefLogs);
                    else {
                        System.out.println("\tUser name already taken !");
                        errorTitle = "Erreur de création de compte";
                        errorText = "Malheureusement, le nom d'utilisateur que vous avez entré est déjà pris. Veuillez en enter un autre différent.";
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.popupCrFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
                    }
                }
            }
        });
    }

    private void SetUserInfos() {
        userName = userNameInputLyt.getEditText().getText().toString();
        password = passwordInputLyt.getEditText().getText().toString();
        secondPassword = repeatPasswordInputLyt.getEditText().getText().toString();
        email = emailInputLyt.getEditText().getText().toString();
    }

    private boolean FirstAndSecondPasswordsAreEquals() {
        return password.equals(secondPassword);
    }

    private boolean UserNameEmailPasswordAreValid() {
        boolean emptyUserName = (userName.length() == 0);
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
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.popupCrFragContainerV, new MessagePopupFragment(errorTitle, errorText)).commit();
        }

        return infosAreValid;
    }

    private boolean UserNameIsFree(SharedPreferences sharedPrefs, String name) {
        int nbAccounts = 0;
        Set<String> currentSet;
        List<String> currentAccount;
        String currentUserName;
        Map<String, ?> allEntries = sharedPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            nbAccounts++;
            currentSet = sharedPrefs.getStringSet(String.valueOf(nbAccounts), new HashSet<>());
            currentAccount = new ArrayList<String>(currentSet);
            SortStringListByFirstChar(currentAccount);
            currentUserName = currentAccount.get(1);
            currentUserName = currentUserName.substring(2);
            if (currentUserName.equals(name))
                return false;
        }
        return true;
    }

    private void AddAccountToSharedPrefs(SharedPreferences sharedPrefs) {
        LinkedHashSet<String> hashSetAccount = new LinkedHashSet<>();

        int nbAccountsAlreadyExisting = SharedPrefLength(sharedPrefs);
        nbAccountsAlreadyExisting++;

        hashSetAccount.add("0_" + String.valueOf(nbAccountsAlreadyExisting));
        hashSetAccount.add("1_" + userName);
        hashSetAccount.add("2_" + password);
        hashSetAccount.add("3_" + email);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putStringSet(String.valueOf(nbAccountsAlreadyExisting), hashSetAccount);
        editor.commit();
    }
}