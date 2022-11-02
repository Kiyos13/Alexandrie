package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OneBookAllInfoActivity extends AppCompatActivity {

    private View editLayout, seeLayout;
    private String mode, previousActivity;
    private android.widget.Button saveBookButton, saveEditBookButton;
    private TextInputLayout titleTxtInputLytEdit, volumeTxtInputLytEdit, serieTxtInputLytEdit, authorTxtInputLytEdit, releaseDateTxtInputLytEdit, descriptionTxtInputLytEdit;
    private TextInputLayout titleTxtInputLytSee, volumeTxtInputLytSee, serieTxtInputLytSee, authorTxtInputLytSee, releaseDateTxtInputLytSee, descriptionTxtInputLytSee;
    private TextView addDateTxtVEdit, addDateTxtVSee, genresListTxtVSee, showcasesListTxtVSee;
    private ImageView readImgVEdit, notReadImgVEdit, readImgVSee, notReadImgVSee;
    private ImageView favoriteImgVEdit, notFavoriteImgVEdit, favoriteImgVSee, notFavoriteImgVSee;
    private String indexInSharedPrefs, title, volume, serie, author, addDate, releaseDate, description;
    private String[] tags;
    private Boolean isRead, isFavorite;
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
        saveEditBookButton = findViewById(R.id.saveEditOneBookBtnEdit);

        titleTxtInputLytEdit = findViewById(R.id.titleOneBookInfoTxtInputLytEdit);
        volumeTxtInputLytEdit = findViewById(R.id.volumeOneBookInfoTxtInputLytEdit);
        serieTxtInputLytEdit = findViewById(R.id.serieOneBookInfoTxtInputLytEdit);
        authorTxtInputLytEdit = findViewById(R.id.authorOneBookInfoTxtInputLytEdit);
        addDateTxtVEdit = findViewById(R.id.addDateOneBookInfoTxtInputLytEdit);
        releaseDateTxtInputLytEdit = findViewById(R.id.releaseDateOneBookInfoTxtInputLytEdit);
        descriptionTxtInputLytEdit = findViewById(R.id.descriptionOneBookInfoTxtInputLytEdit);
        spinnerGenre = findViewById(R.id.spinnerGenreOneBookInfoTxtInputLytEdit);
        readImgVEdit = findViewById(R.id.fullCandleImgVEdit);
        notReadImgVEdit = findViewById(R.id.emptyCandleImgVEdit);
        favoriteImgVEdit = findViewById(R.id.favoriteFullImgVEdit);
        notFavoriteImgVEdit = findViewById(R.id.favoriteEmptyImgVEdit);

        titleTxtInputLytSee = findViewById(R.id.titleOneBookInfoTxtInputLytSee);
        volumeTxtInputLytSee = findViewById(R.id.volumeOneBookInfoTxtInputLytSee);
        serieTxtInputLytSee = findViewById(R.id.serieOneBookInfoTxtInputLytSee);
        authorTxtInputLytSee = findViewById(R.id.authorOneBookInfoTxtInputLytSee);
        addDateTxtVSee = findViewById(R.id.addDateOneBookInfoTxtInputLytSee);
        releaseDateTxtInputLytSee = findViewById(R.id.releaseDateOneBookInfoTxtInputLytSee);
        descriptionTxtInputLytSee = findViewById(R.id.descriptionOneBookInfoTxtInputLytSee);
        genresListTxtVSee = findViewById(R.id.genresListOneBookTxtVSee);
        showcasesListTxtVSee = findViewById(R.id.showcasesListOneBookTxtVSee);
        tags = new String[3];

        isFavorite = false;
        isRead = false;

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
        Intent returnIntent;
        if (previousActivity.equals("scan"))
            returnIntent = new Intent(OneBookAllInfoActivity.this, ScanQRCodeActivity.class);
        else
            returnIntent = new Intent(OneBookAllInfoActivity.this, ListBooksActivity.class);
        // if (previousActivity.equals("verticalList"))

        if (mode.equals("create")) {
            editLayout.setVisibility(View.VISIBLE);
            saveBookButton.setVisibility(View.VISIBLE);
            saveEditBookButton.setVisibility(View.GONE);
            seeLayout.setVisibility(View.GONE);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(System.currentTimeMillis());
            addDate = formatter.format(date); // Set the add date
            addDateTxtVEdit.setText(addDate);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVEdit, new AppBarFragment(returnIntent)).commit();
        }
        else if (mode.equals("see")) {
            editLayout.setVisibility(View.GONE);
            seeLayout.setVisibility(View.VISIBLE);

            RetrieveBookInfosFromBundle(bundle);
            String[] strings = {
                    indexInSharedPrefs, title, volume, serie, author, addDate, releaseDate, description, tags[0], tags[1], tags[2],
            };
            Boolean[] booleans = { isRead, isFavorite };
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVSee, new AppBarFragment(returnIntent, "verticalList", strings, booleans, listBookGenres)).commit();
            SetBookInfosConnectionSee(bundle);
        }
        else if (mode.equals("edit")) {
            saveEditBookButton.setVisibility(View.VISIBLE);
            saveBookButton.setVisibility(View.GONE);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVEdit, new AppBarFragment(returnIntent)).commit();
            SetBookInfosConnectionEdit(bundle);
        }

        // Save a book button click listener
        saveBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousActivity.equals("verticalList")) {
                    UpdateBookInfosCreateAndEditModes(); // Retrieve and update book infos

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
                    indexInSharedPrefs = String.valueOf(indexBook);

                    Set<String> set = CreateAndFillSet(); // Create Set
                    SharedPreferences.Editor editor = sharedPrefBooks.edit();
                    editor.putStringSet(String.valueOf(indexBook), set); // Add current set to SharedPreferences
                    editor.commit();

                    startActivity(new Intent(OneBookAllInfoActivity.this, ListBooksActivity.class));
                }
            }
        });

        saveEditBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateBookInfosCreateAndEditModes();

                Set<String> set = CreateAndFillSet(); // Create Set
                // Edit SharedPrefs
                SharedPreferences.Editor editor = sharedPrefBooks.edit();
                editor.remove(indexInSharedPrefs).commit();
                editor.putStringSet(indexInSharedPrefs, set).commit();

                // Replace current activity by the same but in see mode
                Intent intent = new Intent(OneBookAllInfoActivity.this, OneBookAllInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "see");
                bundle.putString("prevActivity", previousActivity);
                bundle.putString("indexInSharedPrefs", indexInSharedPrefs);
                bundle.putString("title", title);
                bundle.putString("volume", volume);
                bundle.putString("serie", serie);
                bundle.putString("author", author);
                bundle.putString("addDate", addDate);
                bundle.putString("releaseDate", releaseDate);
                bundle.putString("description", description);
                bundle.putString("tag1", tags[0]);
                bundle.putString("tag2", tags[1]);
                bundle.putString("tag3", tags[2]);
                bundle.putBoolean("readStatus", isRead);
                bundle.putBoolean("isFavorite", isFavorite);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private Set<String> CreateAndFillSet() {
        Set<String> set = new HashSet<>();
        set.add("a_" + indexInSharedPrefs); // Add title
        set.add("b_" + title); // Add title
        set.add("c_" + volume); // Add volume
        set.add("d_" + serie); // Add serie
        set.add("e_" + author); // Add author
        set.add("f_" + "t1"); // Add first tag
        set.add("g_" + "t2"); // Add second tag
        set.add("h_" + "t3"); // Add third tag
        set.add("i_" + isRead.toString()); // Add isRead
        set.add("j_" + description); // Add description
        set.add("k_" + addDate); // Add releaseDate
        set.add("l_" + releaseDate); // Add releaseDate
        set.add("m_" + isFavorite.toString()); // Add isFavorite
        return set;
    }

    private void UpdateBookInfosCreateAndEditModes() {
        title = titleTxtInputLytEdit.getEditText().getText().toString(); // Set the title
        volume = volumeTxtInputLytEdit.getEditText().getText().toString(); // Set the volume
        serie = serieTxtInputLytEdit.getEditText().getText().toString(); // Set the serie
        author = authorTxtInputLytEdit.getEditText().getText().toString(); // Set the author
        releaseDate = releaseDateTxtInputLytEdit.getEditText().getText().toString(); // Set the release date
        description = descriptionTxtInputLytEdit.getEditText().getText().toString(); // Set the description
    }

    private void RetrieveBookInfosFromBundle(Bundle bundle) {
        indexInSharedPrefs = bundle.getString("indexInSharedPrefs");
        title = bundle.getString("title");
        volume = bundle.getString("volume");
        serie = bundle.getString("serie");
        author = bundle.getString("author");
        addDate = bundle.getString("addDate");
        releaseDate = bundle.getString("releaseDate");
        description = bundle.getString("description");
        tags[0] = bundle.getString("tag1");
        tags[1] = bundle.getString("tag2");
        tags[2] = bundle.getString("tag3");
        isRead = (bundle.getBoolean("readStatus"));
        isFavorite = (bundle.getBoolean("isFavorite"));
        // vitrines
    }

    // Set book infos (edit mode)
    private void SetBookInfosConnectionEdit(Bundle bundle) {
        RetrieveBookInfosFromBundle(bundle);

        titleTxtInputLytEdit.getEditText().setText(title); // Set the title
        volumeTxtInputLytEdit.getEditText().setText(volume); // Set the volume
        serieTxtInputLytEdit.getEditText().setText(serie); // Set the serie
        authorTxtInputLytEdit.getEditText().setText(author); // Set the author
        releaseDateTxtInputLytEdit.getEditText().setText(releaseDate); // Set the release date
        descriptionTxtInputLytEdit.getEditText().setText(description); // Set the description
        // showcasesListTxtVSee;

        readImgVSee = findViewById(R.id.fullCandleImgVEdit);
        notReadImgVSee = findViewById(R.id.emptyCandleImgVEdit);
        favoriteImgVSee = findViewById(R.id.favoriteFullImgVEdit);
        notFavoriteImgVSee = findViewById(R.id.favoriteEmptyImgVEdit);

        if (isRead) {
            readImgVEdit.setVisibility(View.VISIBLE);
            notReadImgVEdit.setVisibility(View.GONE);
        }
        else {
            readImgVEdit.setVisibility(View.GONE);
            notReadImgVEdit.setVisibility(View.VISIBLE);
        }
        if (isFavorite) {
            favoriteImgVEdit.setVisibility(View.VISIBLE);
            notFavoriteImgVEdit.setVisibility(View.GONE);
        }
        else {
            favoriteImgVEdit.setVisibility(View.GONE);
            notFavoriteImgVEdit.setVisibility(View.VISIBLE);
        }
    }

    // Set book infos (see mode)
    private void SetBookInfosConnectionSee(Bundle bundle) {
        RetrieveBookInfosFromBundle(bundle);

        titleTxtInputLytSee.getEditText().setText(title); // Set the title
        volumeTxtInputLytSee.getEditText().setText(volume); // Set the volume
        serieTxtInputLytSee.getEditText().setText(serie); // Set the serie
        authorTxtInputLytSee.getEditText().setText(author); // Set the author
        addDateTxtVSee.setText(addDate); // Set the add date
        releaseDateTxtInputLytSee.getEditText().setText(releaseDate); // Set the release date
        descriptionTxtInputLytSee.getEditText().setText(description); // Set the description
        genresListTxtVSee.setText(tags[0] + "\n" + tags[1] + "\n" + tags[2]); // Set the tags
        // showcasesListTxtVSee;

        readImgVSee = findViewById(R.id.fullCandleImgVSee);
        notReadImgVSee = findViewById(R.id.emptyCandleImgVSee);
        favoriteImgVSee = findViewById(R.id.favoriteFullImgVSee);
        notFavoriteImgVSee = findViewById(R.id.favoriteEmptyImgVSee);

        if (isRead) {
            readImgVSee.setVisibility(View.VISIBLE);
            notReadImgVSee.setVisibility(View.GONE);
        }
        else {
            readImgVSee.setVisibility(View.GONE);
            notReadImgVSee.setVisibility(View.VISIBLE);
        }
        if (isFavorite) {
            favoriteImgVSee.setVisibility(View.VISIBLE);
            notFavoriteImgVSee.setVisibility(View.GONE);
        }
        else {
            favoriteImgVSee.setVisibility(View.GONE);
            notFavoriteImgVSee.setVisibility(View.VISIBLE);
        }
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