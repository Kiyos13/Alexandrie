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
    private String[] strTitles, strVolumes, strAuthors, strTags1, strTags2, strTags3, strReadStatus;
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
    private ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            System.out.println("\t\tswipe !");

            int position = viewHolder.getAdapterPosition();
            viewHolder.getItemId();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    System.out.println("\t\tswipe left ! " + position);
                    strReadStatus[position] = "false";
                    recyclerViewBooks.getAdapter().notifyItemChanged(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    System.out.println("\t\tswipe right ! " + position);
                    strReadStatus[position] = "true";
                    recyclerViewBooks.getAdapter().notifyItemChanged(position);
                    break;
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX,
                    dY, actionState, isCurrentlyActive);

            System.out.println("\t\tkfhbevfh");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        colorSystemBarTop(getWindow(), getResources(), this);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.appBarIconsListBooksFragContainerV, new TopIconsFragment()).commit();

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);

        strTitles = getResources().getStringArray(R.array.titles);
        strVolumes = getResources().getStringArray(R.array.volumes);
        strAuthors = getResources().getStringArray(R.array.authors);
        strTags1 = getResources().getStringArray(R.array.tags1);
        strTags2 = getResources().getStringArray(R.array.tags2);
        strTags3 = getResources().getStringArray(R.array.tags3);
        strReadStatus = getResources().getStringArray(R.array.read_status);

        sharedPrefBooks = getSharedPreferences("SharedPrefs", MODE_PRIVATE);

        valuesSharedPrefs = new LinkedHashSet[images.length];
        for (int i = 0; i < images.length; i++) {
            currentHashSetValues = new LinkedHashSet<>();
            currentHashSetValues.add("0_" + String.valueOf(i));
            currentHashSetValues.add("1_" + strTitles[i]);
            currentHashSetValues.add("2_" + strVolumes[i]);
            currentHashSetValues.add("3_" + strAuthors[i]);
            currentHashSetValues.add("4_" + strTags1[i]);
            currentHashSetValues.add("5_" + strTags2[i]);
            currentHashSetValues.add("6_" + strTags3[i]);
            currentHashSetValues.add("7_" + strReadStatus[i]);
            valuesSharedPrefs[i] = currentHashSetValues;

            SharedPreferences.Editor editor = sharedPrefBooks.edit();
            editor.putStringSet(String.valueOf(i), currentHashSetValues);
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

        View selectAllItemsCheckboxView = findViewById(R.id.checkSelectAllBooks);
        TextView swipeTextTxtV = findViewById(R.id.swipeTextTxtV);
        TextView nbSelectedBooksTextTxtV = findViewById(R.id.nbBooksSelectedTxtV);

        booksAdapter = new BooksAdapter(this, strTitles, strVolumes, strAuthors,
                                                    strTags1, strTags2, strTags3,
                                                    strReadStatus, images, recyclerViewBooks,
                                                    selectAllItemsCheckboxView, swipeTextTxtV, nbSelectedBooksTextTxtV);
        recyclerViewBooks.setAdapter(booksAdapter);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper swipeITH = new ItemTouchHelper(callback);
        swipeITH.attachToRecyclerView(recyclerViewBooks);
    }
}