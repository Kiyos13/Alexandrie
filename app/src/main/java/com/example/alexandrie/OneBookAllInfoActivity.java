package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Spinner spinnerGenre;
    private ArrayList<String> listBookGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book_all_info);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        editLayout = findViewById(R.id.oneBookAllLytEdit);
        seeLayout = findViewById(R.id.oneBookAllLytSee);
        saveBookButton = findViewById(R.id.saveOneBookBtnEdit);
        titleTxtInputLyt = findViewById(R.id.titleOneBookInfoTxtInputLytEdit);
        volumeTxtInputLyt = findViewById(R.id.volumeOneBookInfoTxtInputLytEdit);
        serieTxtInputLyt = findViewById(R.id.serieOneBookInfoTxtInputLytEdit);
        authorTxtInputLyt = findViewById(R.id.authorOneBookInfoTxtInputLytEdit);
        releaseDateTxtInputLyt = findViewById(R.id.releaseDateOneBookInfoTxtInputLytEdit);
        descriptionTxtInputLyt = findViewById(R.id.descriptionOneBookInfoTxtInputLytEdit);
        spinnerGenre = findViewById(R.id.spinnerGenreOneBookEdit);

        listBookGenres = new ArrayList<String>();
        retrieveBooksGenresFromSharedPreferences(sharedPrefBooks);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listBookGenres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

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

                    int indexBook = 0, maxIndexBooks = 0;
                    String bookData;
                    Map<String, ?> allEntries = sharedPrefBooks.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        bookData = entry.getValue().toString();
                        bookData = bookData.substring(1);
                        bookData = bookData.substring(0, bookData.length() - 1);

                        List<String> bookDataList = new ArrayList<String>(Arrays.asList(bookData.split(", ")));
                        SortStringListByFirstChar(bookDataList);

                        String currentData = bookDataList.get(0).substring(2);
                        int currentDataInt = Integer.parseInt(currentData);
                        if (currentDataInt > maxIndexBooks)
                            maxIndexBooks = currentDataInt;
                    }
                    indexBook = maxIndexBooks;
                    indexBook++;
                    System.out.println("nbBooksAlreadySaved = " + indexBook);
                    bookHashSetValues = new LinkedHashSet<>(); // Initialize (empty) current set
                    bookHashSetValues.add("a_" + String.valueOf(indexBook)); // Add index to the current set
                    bookHashSetValues.add("b_" + title); // Add title
                    bookHashSetValues.add("c_" + volume); // Add volume
                    bookHashSetValues.add("d_" + serie); // Add serie
                    bookHashSetValues.add("e_" + author); // Add author
                    bookHashSetValues.add("f_" + "t1"); // Add first tag
                    bookHashSetValues.add("g_" + "t2"); // Add second tag
                    bookHashSetValues.add("h_" + "t3"); // Add third tag
                    bookHashSetValues.add("i_" + "false"); // Add read status
                    bookHashSetValues.add("j_" + description); // Add description
                    bookHashSetValues.add("k_" + releaseDate); // Add releaseDate

                    SharedPreferences.Editor editor = sharedPrefBooks.edit();
                    editor.putStringSet(String.valueOf(indexBook), bookHashSetValues); // Add current set to SharedPreferences
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

    // Retrieve books genres from SharedPreferences to fill spinnerList
    private void retrieveBooksGenresFromSharedPreferences(SharedPreferences sharedPreferences) {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        String bookData;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            bookData = entry.getValue().toString();
            bookData = bookData.substring(1);
            bookData = bookData.substring(0, bookData.length() - 1);

            List<String> bookDataList = new ArrayList<String>(Arrays.asList(bookData.split(", ")));
            SortStringListByFirstChar(bookDataList);

            int bookDataListLength = bookDataList.size();
            String currentData;
            for (int i = 0; i < bookDataListLength; i++) {
                currentData = bookDataList.get(i).substring(2);
                bookDataList.set(i, currentData);
            }

            String currentElement;
            if (bookDataList.size() >= 7) {
                for (int i = 5; i < 8; i++) {
                    currentElement = bookDataList.get(i);
                    if (!listBookGenres.contains(currentElement) && (currentElement.length() != 0))
                        listBookGenres.add(currentElement);
                }
            }
        }
    }
}