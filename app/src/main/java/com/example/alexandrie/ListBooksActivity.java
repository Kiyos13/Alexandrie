package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListBooksActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBooks;
    // String arrays for each book item field
    private ArrayList<String> strIndexesSharedPrefs, strTitles, strVolumes, strAuthors, strTags1, strTags2, strTags3, strReadStatus;
    // Covers of each book item
    private int images[] = {R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4};
    public static SharedPreferences sharedPrefBooks;
    private RecyclerView.Adapter booksAdapter;
    // Movements handler on book item
    private ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Position of the item in the recyclerView
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT: // Swipe LEFT
                    System.out.println("\t\tswipe left ! " + position);
                    strReadStatus.set(position, "false"); // Mark the book item has unread
                    recyclerViewBooks.getAdapter().notifyItemChanged(position); // Notify the change to the adapter
                    break;
                case ItemTouchHelper.RIGHT:
                    System.out.println("\t\tswipe right ! " + position);
                    strReadStatus.set(position, "true"); // Mark the book item has read
                    recyclerViewBooks.getAdapter().notifyItemChanged(position); // Notify the change to the adapter
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        // Display icons (filter, order, search) fragments on the top bar
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.topBarLBFragContainerV, new AppBarFragment(true)).commit();

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        strIndexesSharedPrefs = new ArrayList<String>();
        strTitles = new ArrayList<String>();
        strVolumes = new ArrayList<String>();
        strAuthors = new ArrayList<String>();
        strTags1 = new ArrayList<String>();
        strTags2 = new ArrayList<String>();
        strTags3 = new ArrayList<String>();
        strReadStatus = new ArrayList<String>();

        sharedPrefBooks = getSharedPreferences("SharedPrefsBooks", MODE_PRIVATE); // Retrieve SharedPreferences
        //sharedPrefBooks.edit().clear().commit(); // Clean SharedPreferences
        retrieveBooksFromSharedPreferences(sharedPrefBooks); // Fill ListArrays from SharedPreferences

        // View with the checkbox to select all book items (to pass to the adapter)
        View selectAllItemsCheckboxView = findViewById(R.id.checkSelectAllBooks);
        // TextView with swipe text (to pass to the adapter)
        TextView swipeTextTxtV = findViewById(R.id.swipeTextTxtV);
        // TextView with number of selected book items text (to pass to the adapter)
        TextView nbSelectedBooksTextTxtV = findViewById(R.id.nbBooksSelectedTxtV);

        // Creation of the books adapter
        booksAdapter = new BooksAdapter(this, strIndexesSharedPrefs, strTitles, strVolumes, strAuthors,
                                                    strTags1, strTags2, strTags3,
                                                    strReadStatus, images, recyclerViewBooks,
                                                    selectAllItemsCheckboxView, swipeTextTxtV, nbSelectedBooksTextTxtV);
        recyclerViewBooks.setAdapter(booksAdapter); // Link the adapter to the recyclerView
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper swipeITH = new ItemTouchHelper(callback); // Creation of the swipe ItemTouchHelper
        swipeITH.attachToRecyclerView(recyclerViewBooks); // Link the swipe ItemTouchHelper to the recyclerView
    }

    // Retrieve books infos from SharedPreferences to fill arrayLists
    private void retrieveBooksFromSharedPreferences(SharedPreferences sharedPreferences) {
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

            if (bookDataList.size() >= 8) {
                strIndexesSharedPrefs.add(bookDataList.get(0));
                strTitles.add(bookDataList.get(1));
                strVolumes.add(bookDataList.get(2));
                strAuthors.add(bookDataList.get(4));
                strTags1.add(bookDataList.get(5));
                strTags2.add(bookDataList.get(6));
                strTags3.add(bookDataList.get(7));
                strReadStatus.add(bookDataList.get(8));
            }
        }
    }
}