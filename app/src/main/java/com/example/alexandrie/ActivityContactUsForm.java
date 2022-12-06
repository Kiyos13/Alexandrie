package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ActivityContactUsForm extends AppCompatActivity {

    private Spinner spinner;
    private TextInputLayout first_name_textview, last_name_textview, mail_textview, message_textview;
    private String first_name_user, last_name_user, mail_user, contact_reason_user, message_user;
    private Button SendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_form);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent;
        intent = new Intent(ActivityContactUsForm.this, ListBooksActivity.class);
        fragmentManager.beginTransaction().add(R.id.topBarContactUs, new AppBarFragment(intent)).commit();


        SendMessageButton = findViewById(R.id.button_send_message);
        /*
        first_name_textview = findViewById(R.id.first_name_contact_form);
        last_name_textview = findViewById(R.id.last_name_contact_form);
        mail_textview = findViewById(R.id.mail_contact_form);
        message_textview = findViewById(R.id.message_contact_form);
        */
        spinner = (Spinner)findViewById(R.id.spinner_contact_form);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                contact_reason_user = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        /*
        first_name_user = first_name_textview.getEditText().getText().toString();
        last_name_user = first_name_textview.getEditText().getText().toString();
        mail_user = first_name_textview.getEditText().getText().toString();
        message_user = first_name_textview.getEditText().getText().toString();
        */

        /*
        SendMessageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String UriText = "mailto:" + Uri.encode(mail_user)
                        + "?subject=" + Uri.encode(contact_reason_user) + "$body=" + Uri.encode(message_user);
                Uri uri = Uri.parse(UriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "send mail"));
            }
        });
        */
    }
}