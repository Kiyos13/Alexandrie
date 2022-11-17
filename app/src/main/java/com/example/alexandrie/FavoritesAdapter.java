package com.example.alexandrie;

import static com.example.alexandrie.FavoritesShowcaseActivity.listFavoriteBooksInSharedPrefs;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAddDate;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAuthor;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksDescription;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIndex;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIsFavorite;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksMark;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReadStatus;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReleaseDate;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksSummary;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag1;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag2;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag3;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTitle;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksVolume;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private Context context;
    private int[] favoriteBooksCovers;

    public FavoritesAdapter(Context ctx, int[] favorites) {
        context = ctx;
        favoriteBooksCovers = favorites;
    }

                            @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_one_favorite_in_favorites_showcase, parent, false);
        return new FavoritesAdapter.FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cover.setImageResource(favoriteBooksCovers[0]);
        holder.titleTxtV.setText(listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksTitle).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
        holder.sharedPrefIndexTxt = listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksIndex).get(position % listFavoriteBooksInSharedPrefs.get(0).size());

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OneBookAllInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "see");
                bundle.putString("prevActivity", "favoriteList");
                bundle.putString("indexInSharedPrefs", holder.sharedPrefIndexTxt);
                bundle.putString("title", holder.titleTxtV.getText().toString());
                bundle.putString("volume", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksVolume).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("serie", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksVolume).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("author", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksAuthor).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("releaseDate", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksReleaseDate).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("addDate", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksAddDate).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                Boolean readStatus = listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksReadStatus).get(position % listFavoriteBooksInSharedPrefs.get(0).size()).equals("true") ? true : false;
                bundle.putBoolean("readStatus", readStatus);
                bundle.putString("description", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksDescription).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("summary", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksSummary).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("mark", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksMark).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                Boolean isFavorite = listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksIsFavorite).get(position % listFavoriteBooksInSharedPrefs.get(0).size()).equals("true") ? true : false;
                bundle.putBoolean("isFavorite", isFavorite);
                bundle.putString("tag1", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksTag1).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("tag2", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksTag2).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                bundle.putString("tag3", listFavoriteBooksInSharedPrefs.get(indexInSharedPrefBooksTag3).get(position % listFavoriteBooksInSharedPrefs.get(0).size()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    /*
    @Override
    public int getItemCount() {
        return listFavoriteBooksInSharedPrefs.get(0).size();
    }
    */
    @Override
    public int getItemCount() {
        return listFavoriteBooksInSharedPrefs.get(0) == null ? 0 : listFavoriteBooksInSharedPrefs.get(0).size() * 2;
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView titleTxtV;
        String sharedPrefIndexTxt;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            cover = itemView.findViewById(R.id.coverOneFavorite);
            titleTxtV = itemView.findViewById(R.id.titleFavoriteTxtV);
        }
    }
}
