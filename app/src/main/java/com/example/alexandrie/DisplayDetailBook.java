package com.example.alexandrie;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayDetailBook extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_book);

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
    }
}