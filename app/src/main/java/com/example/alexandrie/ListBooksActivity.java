package com.example.alexandrie;

import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;
import static com.example.alexandrie.MenuFragment.isRightHand;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIndex;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReadStatus;
import static com.example.alexandrie.OneBookAllInfoActivity.nbFieldsInSharedPrefBooks;
import static com.example.alexandrie.OrderFragment.SortBooksArrayListOfArrayLists;
import static com.example.alexandrie.OrderFragment.currentOrderIndexInSharedPrefs;
import static com.example.alexandrie.OrderFragment.currentWayOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListBooksActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBooks;
    // Array list of string arrays for each book item field
    public static ArrayList<ArrayList<String>> listBooksInSharedPrefs;
    // Covers of each book item
    private int images[] = { R.drawable.hp4 }; // TODO : change to real covers
    public static SharedPreferences sharedPrefBooks;
    public static RecyclerView.Adapter booksAdapter;
    private String keyBookInSharedPrefs;
    private Set<String> set;
    private SharedPreferences.Editor editor;
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

                    // Edit Set
                    keyBookInSharedPrefs = listBooksInSharedPrefs.get(indexInSharedPrefBooksIndex).get(position);
                    set = sharedPrefBooks.getStringSet(keyBookInSharedPrefs, null);
                    for (String obj : set) {
                        if (obj.charAt(0) == 'i') {
                            set.remove(obj);
                            set.add("i_false");
                            break;
                        }
                    }
                    // Edit SharedPrefs
                    editor = sharedPrefBooks.edit();
                    editor.remove(keyBookInSharedPrefs).commit();
                    editor.putStringSet(keyBookInSharedPrefs, set).commit();

                    listBooksInSharedPrefs.get(indexInSharedPrefBooksReadStatus).set(position, "false"); // Mark the book item has unread
                    recyclerViewBooks.getAdapter().notifyItemChanged(position); // Notify the change to the adapter
                    break;
                case ItemTouchHelper.RIGHT:
                    System.out.println("\t\tswipe right ! " + position);

                    // Edit Set
                    keyBookInSharedPrefs = listBooksInSharedPrefs.get(indexInSharedPrefBooksIndex).get(position);
                    set = sharedPrefBooks.getStringSet(keyBookInSharedPrefs, null);
                    for (String obj : set) {
                        if (obj.charAt(0) == 'i') {
                            set.remove(obj);
                            set.add("i_true");
                            break;
                        }
                    }
                    // Edit SharedPrefs
                    editor = sharedPrefBooks.edit();
                    editor.remove(keyBookInSharedPrefs).commit();
                    editor.putStringSet(keyBookInSharedPrefs, set).commit();

                    listBooksInSharedPrefs.get(indexInSharedPrefBooksReadStatus).set(position, "true"); // Mark the book item has read
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


        FragmentManager fragmentManager = getSupportFragmentManager();
        // Display icons (filter, order, search) fragments on the top bar
        fragmentManager.beginTransaction().add(R.id.topBarLBFragContainerV, new AppBarFragment(true)).commit();
        // Display add fragment
        if (isRightHand == null)
            fragmentManager.beginTransaction().replace(R.id.addDelFragContainerVRight, new AddFragment()).commit();
        else {
            if (isRightHand)
                fragmentManager.beginTransaction().replace(R.id.addDelFragContainerVRight, new AddFragment()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.addDelFragContainerVLeft, new AddFragment()).commit();
        }

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);

        sharedPrefBooks = getSharedPreferences("SharedPrefsBooks", MODE_PRIVATE); // Retrieve SharedPreferences
        //sharedPrefBooks.edit().clear().commit(); // Clean SharedPreferences
        retrieveBooksFromSharedPreferences(sharedPrefBooks); // Fill ListArrays from SharedPreferences
        // Sort arraylists
        listBooksInSharedPrefs.set(currentOrderIndexInSharedPrefs, SortBooksArrayListOfArrayLists(currentOrderIndexInSharedPrefs, currentWayOrder, listBooksInSharedPrefs.size()));

        // View with the checkbox to select all book items (to pass to the adapter)
        View selectAllItemsCheckboxView = findViewById(R.id.checkSelectAllBooks);
        // TextView with swipe text (to pass to the adapter)
        TextView swipeTextTxtV = findViewById(R.id.swipeTextTxtV);
        // TextView with number of selected book items text (to pass to the adapter)
        TextView nbSelectedBooksTextTxtV = findViewById(R.id.nbBooksSelectedTxtV);

        // Creation of the books adapter
        booksAdapter = new BooksAdapter(this, images, recyclerViewBooks,
                                        selectAllItemsCheckboxView, swipeTextTxtV, nbSelectedBooksTextTxtV);
        recyclerViewBooks.setAdapter(booksAdapter); // Link the adapter to the recyclerView
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper swipeITH = new ItemTouchHelper(callback); // Creation of the swipe ItemTouchHelper
        swipeITH.attachToRecyclerView(recyclerViewBooks); // Link the swipe ItemTouchHelper to the recyclerView
    }

    // Init global list books
    public static void initGlobalListBooks() {
        listBooksInSharedPrefs = new ArrayList<ArrayList<String>>();
        for (int i = 0; i <= nbFieldsInSharedPrefBooks; i++) {
            listBooksInSharedPrefs.add(i, new ArrayList<String>());
        }
    }

    // Retrieve books infos from SharedPreferences to fill arrayLists
    public static void retrieveBooksFromSharedPreferences(SharedPreferences sharedPreferences) {
        initGlobalListBooks();
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

            int nbFields = listBooksInSharedPrefs.size();
            if (bookDataList.size() >= nbFields) {
                for (int i = 0; i < nbFields; i++)
                    listBooksInSharedPrefs.get(i).add(bookDataList.get(i));
            }
        }
    }
}