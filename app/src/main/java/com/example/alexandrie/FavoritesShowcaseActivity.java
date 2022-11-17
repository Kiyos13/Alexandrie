package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;
import static com.example.alexandrie.ListBooksActivity.retrieveBooksFromSharedPreferences;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIsFavorite;
import static com.example.alexandrie.OneBookAllInfoActivity.nbFieldsInSharedPrefBooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FavoritesShowcaseActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private RecyclerView.Adapter favoritesAdapter;
    public static ArrayList<ArrayList<String>> listFavoriteBooksInSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_showcase);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);

        retrieveFavoriteBooks();

        int images[] = { R.drawable.hp4 }; // TODO : change for real covers

        // Creation of the books adapter
        favoritesAdapter = new FavoritesAdapter(this, images);
        recyclerViewFavorites.setAdapter(favoritesAdapter); // Link the adapter to the recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                FavoritesShowcaseActivity.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerViewFavorites.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstItemVisible != 0 && firstItemVisible % listFavoriteBooksInSharedPrefs.get(0).size() == 0)
                    recyclerView.getLayoutManager().scrollToPosition(0);
            }
        });
        recyclerViewFavorites.setLayoutManager(linearLayoutManager);
    }

    // Init global list favorite books
    public static void initGlobalListFavoriteBooks() {
        listFavoriteBooksInSharedPrefs = new ArrayList<ArrayList<String>>();
        for (int i = 0; i <= nbFieldsInSharedPrefBooks; i++) {
            listFavoriteBooksInSharedPrefs.add(i, new ArrayList<String>());
        }
    }

    private void retrieveFavoriteBooks() {
        initGlobalListFavoriteBooks();

        // Retrieve all SharedPrefs to update listBooksInSharedPrefs
        retrieveBooksFromSharedPreferences(sharedPrefBooks);

        for (int i = 0; i < listBooksInSharedPrefs.get(0).size(); i++) {
            if (listBooksInSharedPrefs.get(indexInSharedPrefBooksIsFavorite).get(i).equals("true")) {
                for (int j = 0; j < listBooksInSharedPrefs.size(); j++) {
                    listFavoriteBooksInSharedPrefs.get(j).add(listBooksInSharedPrefs.get(j).get(i));
                }
            }
        }
    }
}