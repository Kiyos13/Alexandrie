package com.example.alexandrie;

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

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ListBooksActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBooks;
    // String arrays for each book item field
    private String[] strTitles, strVolumes, strAuthors, strTags1, strTags2, strTags3, strReadStatus;
    // Covers of each book item
    private int images[] = {R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4,
            R.drawable.hp4, R.drawable.hp4, R.drawable.hp4};
    public static SharedPreferences sharedPrefBooks;
    private LinkedHashSet<String>[] valuesSharedPrefs;
    private LinkedHashSet<String> currentHashSetValues;
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
                    strReadStatus[position] = "false"; // Mark the book item has unread
                    recyclerViewBooks.getAdapter().notifyItemChanged(position); // Notify the change to the adapter
                    break;
                case ItemTouchHelper.RIGHT:
                    System.out.println("\t\tswipe right ! " + position);
                    strReadStatus[position] = "true"; // Mark the book item has read
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
        strTitles = getResources().getStringArray(R.array.titles);
        strVolumes = getResources().getStringArray(R.array.volumes);
        strAuthors = getResources().getStringArray(R.array.authors);
        strTags1 = getResources().getStringArray(R.array.tags1);
        strTags2 = getResources().getStringArray(R.array.tags2);
        strTags3 = getResources().getStringArray(R.array.tags3);
        strReadStatus = getResources().getStringArray(R.array.read_status);

        sharedPrefBooks = getSharedPreferences("SharedPrefs", MODE_PRIVATE); // Retrieve SharedPreferences

        valuesSharedPrefs = new LinkedHashSet[images.length];
        for (int i = 0; i < images.length; i++) { // Fill SharedPreferences
            currentHashSetValues = new LinkedHashSet<>(); // Initialize (empty) current set
            currentHashSetValues.add("0_" + String.valueOf(i)); // Add index to the current set
            currentHashSetValues.add("1_" + strTitles[i]); // Add title to the current set
            currentHashSetValues.add("2_" + strVolumes[i]); // Add volume to the current set
            currentHashSetValues.add("3_" + strAuthors[i]); // Add author to the current set
            currentHashSetValues.add("4_" + strTags1[i]); // Add first tag to the current set
            currentHashSetValues.add("5_" + strTags2[i]); // Add second tag to the current set
            currentHashSetValues.add("6_" + strTags3[i]); // Add third tag to the current set
            currentHashSetValues.add("7_" + strReadStatus[i]); // Add read status to the current set
            valuesSharedPrefs[i] = currentHashSetValues;

            SharedPreferences.Editor editor = sharedPrefBooks.edit();
            editor.putStringSet(String.valueOf(i), currentHashSetValues); // Add current set to SharedPreferences
            editor.commit();
        }
        /*
        for (int i = 0; i < images.length; i++) {
            System.out.println("valuesSharedPrefs " + i + " - length = " + valuesSharedPrefs[i].size() + " - content = " + valuesSharedPrefs[i]);
        }
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            System.out.println("key " + entry.getKey() + " : " + entry.getValue().toString());
        }
         */

        // View with the checkbox to select all book items (to pass to the adapter)
        View selectAllItemsCheckboxView = findViewById(R.id.checkSelectAllBooks);
        // TextView with swipe text (to pass to the adapter)
        TextView swipeTextTxtV = findViewById(R.id.swipeTextTxtV);
        // TextView with number of selected book items text (to pass to the adapter)
        TextView nbSelectedBooksTextTxtV = findViewById(R.id.nbBooksSelectedTxtV);

        // Creation of the books adapter
        booksAdapter = new BooksAdapter(this, strTitles, strVolumes, strAuthors,
                                                    strTags1, strTags2, strTags3,
                                                    strReadStatus, images, recyclerViewBooks,
                                                    selectAllItemsCheckboxView, swipeTextTxtV, nbSelectedBooksTextTxtV);
        recyclerViewBooks.setAdapter(booksAdapter); // Link the adapter to the recyclerView
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper swipeITH = new ItemTouchHelper(callback); // Creation of the swipe ItemTouchHelper
        swipeITH.attachToRecyclerView(recyclerViewBooks); // Link the swipe ItemTouchHelper to the recyclerView
    }
}