package com.example.alexandrie;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayDetailBook extends AppCompatActivity
{
    ArrayList<Comment> comments_book;
    RecyclerView recycler_view_comments_book;
    RatingBar rating_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_book);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent = new Intent(DisplayDetailBook.this, ActivityDisplayMenu.class);
        fragmentManager.beginTransaction().add(R.id.topBarDisplayDetailBook, new AppBarFragment(returnIntent)).commit();

        recycler_view_comments_book = findViewById(R.id.comments_of_lectors);

        Integer image = getIntent().getIntExtra("IMAGE", 0);
        String titre = getIntent().getStringExtra("TITRE");
        String auteur = getIntent().getStringExtra("AUTEUR");
        String resume = getIntent().getStringExtra("RESUME");

        ImageView image_view = findViewById(R.id.image_view_edit);
        TextView titre_view = findViewById(R.id.titre_view_edit);
        TextView auteur_view = findViewById(R.id.auteur_view_edit);
        TextView resume_view = findViewById(R.id.resume_view_edit);

        image_view.setImageResource(image);
        titre_view.setText(titre);
        auteur_view.setText(auteur);
        resume_view.setText(resume);


        comments_book = new ArrayList<>();
        for(int i=0; i<3; i++)
        {
            Comment comment_to_add = new Comment("Sarah", "Trop bien");
            comments_book.add(comment_to_add);
        }

        LinearLayoutManager layoutManager_comments_book = new LinearLayoutManager(
                DisplayDetailBook.this, LinearLayoutManager.HORIZONTAL, false
        );

        recycler_view_comments_book.setLayoutManager(layoutManager_comments_book);
        recycler_view_comments_book.setItemAnimator(new DefaultItemAnimator());

        CommentsAdapter adapter_comments_book = new CommentsAdapter(DisplayDetailBook.this, comments_book);
        recycler_view_comments_book.setAdapter(adapter_comments_book);

        rating_bar = (RatingBar) findViewById(R.id.rating_bar_view);
        rating_bar.setRating(2);
    }
}