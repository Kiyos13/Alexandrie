package com.example.alexandrie;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{
    ArrayList<Book> books;
    Context context;

    public MenuAdapter(Context context, ArrayList<Book> books)
    {
        this.context = context;
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_menu_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.imageView.setImageResource(books.get(position).getImage());
        holder.titreView.setText(books.get(position).getTitle());
        holder.auteurView.setText(books.get(position).getAuthor());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, DisplayDetailBook.class);

                ArrayList<Book> selected_item_list = books;

                intent.putExtra("IMAGE", selected_item_list.get(position).getImage());
                intent.putExtra("TITRE", selected_item_list.get(position).getTitle());
                intent.putExtra("AUTEUR", selected_item_list.get(position).getAuthor());
                intent.putExtra("RESUME", selected_item_list.get(position).getSummary());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return books.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView titreView;
        TextView auteurView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.image_view);
            titreView = itemView.findViewById(R.id.titre_view);
            auteurView = itemView.findViewById(R.id.auteur_view);
        }
    }
}