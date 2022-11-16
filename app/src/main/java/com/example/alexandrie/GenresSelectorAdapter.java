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
    private int counterSelectedGenres;

    public GenresSelectorAdapter(Context ctx) {
        context = ctx;
        setHasStableIds(true);
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

        int counterSelectedGenresPlusOrMinus = (holder.genreCheckBox.isChecked()) ? 1 : 0;
        counterSelectedGenres = counterSelectedGenres + counterSelectedGenresPlusOrMinus;

        holder.genreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listBookGenresSelected.set(position, holder.genreCheckBox.isChecked());
                int counterSelectedGenresPlusOrMinus = (holder.genreCheckBox.isChecked()) ? 1 : -1;
                counterSelectedGenres = counterSelectedGenres + counterSelectedGenresPlusOrMinus;

                // Max 3 tags
                if (counterSelectedGenres > 3) {
                    holder.genreCheckBox.setChecked(false);
                    listBookGenresSelected.set(position, false);
                }
            }
        });

        holder.allItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.genreCheckBox.setChecked(!holder.genreCheckBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBookGenres.size();
    }

    public class GenresSelectorViewHolder extends RecyclerView.ViewHolder {

        View allItem;
        TextView genreTxtV;
        CheckBox genreCheckBox;

        public GenresSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            allItem = itemView.findViewById(R.id.oneGenreAllItem);
            genreTxtV = itemView.findViewById(R.id.oneGenreInListTxtV);
            genreCheckBox = itemView.findViewById(R.id.oneGenreInListCheckBox);
        }
    }
}
