package com.example.alexandrie;

import static com.example.alexandrie.GenresSelectorFragment.listBookGenres;
import static com.example.alexandrie.GenresSelectorFragment.listBookGenresSelected;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GenresSelectorAdapter extends RecyclerView.Adapter<GenresSelectorAdapter.GenresSelectorViewHolder> {

    private Context context;

    public GenresSelectorAdapter(Context ctx) {
        context = ctx;
    }

    @NonNull
    @Override
    public GenresSelectorAdapter.GenresSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_one_genre_in_selector, parent, false);
        return new GenresSelectorAdapter.GenresSelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresSelectorAdapter.GenresSelectorViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.genreTxtV.setText(listBookGenres.get(position));
        holder.genreCheckBox.setChecked(listBookGenresSelected.get(position));

        holder.genreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listBookGenresSelected.set(position, holder.genreCheckBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBookGenres.size();
    }

    public class GenresSelectorViewHolder extends RecyclerView.ViewHolder {

        TextView genreTxtV;
        CheckBox genreCheckBox;

        public GenresSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTxtV = itemView.findViewById(R.id.oneGenreInListTxtV);
            genreCheckBox = itemView.findViewById(R.id.oneGenreInListCheckBox);
        }
    }
}
