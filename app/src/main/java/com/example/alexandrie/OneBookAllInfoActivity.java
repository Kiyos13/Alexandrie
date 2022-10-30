package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class OneBookAllInfoActivity extends AppCompatActivity {

    private View editLayout, seeLayout;
    private String mode, previousActivity;
    private android.widget.Button saveBookButton;
    private TextInputLayout titleTxtInputLyt, volumeTxtInputLyt, serieTxtInputLyt, authorTxtInputLyt, releaseDateTxtInputLyt, descriptionTxtInputLyt;
    private String title, volume, serie, author, releaseDate, description;
    private LinkedHashSet<String> bookHashSetValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book_all_info);

        editLayout = findViewById(R.id.oneBookAllLytEdit);
        seeLayout = findViewById(R.id.oneBookAllLytSee);
        saveBookButton = findViewById(R.id.saveOneBookBtnEdit);
        titleTxtInputLyt = findViewById(R.id.titleOneBookInfoTxtInputLytEdit);
        volumeTxtInputLyt = findViewById(R.id.volumeOneBookInfoTxtInputLytEdit);
        serieTxtInputLyt = findViewById(R.id.serieOneBookInfoTxtInputLytEdit);
        authorTxtInputLyt = findViewById(R.id.authorOneBookInfoTxtInputLytEdit);
        releaseDateTxtInputLyt = findViewById(R.id.releaseDateOneBookInfoTxtInputLytEdit);
        descriptionTxtInputLyt = findViewById(R.id.descriptionOneBookInfoTxtInputLytEdit);

        Bundle bundle = getIntent().getExtras();
        mode = bundle.getString("mode");
        previousActivity = bundle.getString("prevActivity");

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(OneBookAllInfoActivity.this, ScanQRCodeActivity.class);

        if (mode.equals("edit")) {
            editLayout.setVisibility(View.VISIBLE);
            seeLayout.setVisibility(View.GONE);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVEdit, new AppBarFragment(returnIntent)).commit();
        }
        else if (mode.equals("see")) {
            editLayout.setVisibility(View.GONE);
            seeLayout.setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVSee, new AppBarFragment(returnIntent)).commit();
        }

        // Save a book button click listener
        saveBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousActivity.equals("verticalList")) {
                    SetBookInfosConnection(); // Retrieve and update book infos

                    int nbBooksAlreadySaved = 0;
                    Map<String, ?> allEntries = sharedPrefBooks.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet())
                        nbBooksAlreadySaved++;
                    nbBooksAlreadySaved++;
                    bookHashSetValues = new LinkedHashSet<>(); // Initialize (empty) current set
                    bookHashSetValues.add("a_" + String.valueOf(nbBooksAlreadySaved)); // Add index to the current set
                    bookHashSetValues.add("b_" + title); // Add title
                    bookHashSetValues.add("c_" + volume); // Add volume
                    bookHashSetValues.add("d_" + serie); // Add author
                    bookHashSetValues.add("e_" + author); // Add author
                    bookHashSetValues.add("f_" + "t1"); // Add first tag
                    bookHashSetValues.add("g_" + "t2"); // Add second tag
                    bookHashSetValues.add("h_" + "t3"); // Add third tag
                    bookHashSetValues.add("i_" + "false"); // Add read status
                    bookHashSetValues.add("j_" + description); // Add description
                    bookHashSetValues.add("k_" + releaseDate); // Add releaseDate

                    SharedPreferences.Editor editor = sharedPrefBooks.edit();
                    editor.putStringSet(String.valueOf(nbBooksAlreadySaved), bookHashSetValues); // Add current set to SharedPreferences
                    editor.commit();

                    startActivity(new Intent(OneBookAllInfoActivity.this, ListBooksActivity.class));
                }
            }
        });
    }

    // Set book infos
    private void SetBookInfosConnection() {
        title = titleTxtInputLyt.getEditText().getText().toString(); // Set the title
        volume = volumeTxtInputLyt.getEditText().getText().toString(); // Set the volume
        serie = serieTxtInputLyt.getEditText().getText().toString(); // Set the serie
        author = authorTxtInputLyt.getEditText().getText().toString(); // Set the author
        releaseDate = releaseDateTxtInputLyt.getEditText().getText().toString(); // Set the release date
        description = descriptionTxtInputLyt.getEditText().getText().toString(); // Set the description
    }
}