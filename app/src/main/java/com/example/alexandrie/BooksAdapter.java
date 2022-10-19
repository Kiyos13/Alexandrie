package com.example.alexandrie;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    String dataTitles[], dataVolumes[], dataAuthors[], dataTags1[], dataTags2[], dataTags3[], dataChecks[];
    int images[];
    Context context;

    public BooksAdapter(Context ctx, String strTitles[], String strVolumes[], String strAuthors[],
                        String strTags1[], String strTags2[], String strTags3[], int imgs[], String boolChecks[]) {
        context = ctx;
        dataTitles = strTitles;
        dataVolumes = strVolumes;
        dataAuthors = strAuthors;
        dataTags1 = strTags1;
        dataTags2 = strTags2;
        dataTags3 = strTags3;
        images = imgs;
        dataChecks = boolChecks;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_one_book_in_list_books, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.titleTxt.setText(dataTitles[position]);
        holder.volumeTxt.setText("Tome " + dataVolumes[position]);
        holder.authorTxt.setText(dataAuthors[position]);
        onBindViewHolderTags(holder, position);
        holder.imageView.setImageResource(images[position]);
        if ((dataChecks[position].equals("true")) || (dataChecks[position].equals("True")))
            holder.checkBool.setChecked(true);
        else
            holder.checkBool.setChecked(false);

        holder.checkBool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked !!!!!!!!!!!!!!!!!!!!!!!!");

                boolean is_checked = holder.checkBool.isChecked();
                System.out.println("Is checked : " + is_checked);
            }
        });

        /*
        HashSet<String> valuesSharedPrefs = new HashSet<String>();
        valuesSharedPrefs.add(String.valueOf(position));
        valuesSharedPrefs.add(holder.titleTxt.getText().toString());
        valuesSharedPrefs.add(holder.volumeTxt.getText().toString());
        valuesSharedPrefs.add(holder.serieTxt.getText().toString());
        valuesSharedPrefs.add(holder.authorTxt.getText().toString());
        valuesSharedPrefs.add(holder.tag1Txt.getText().toString());
        valuesSharedPrefs.add(holder.tag2Txt.getText().toString());
        valuesSharedPrefs.add(holder.tag3Txt.getText().toString());
        valuesSharedPrefs.add(String.valueOf(holder.checkBool));
        System.out.println("valuesSharedPrefs content = " + valuesSharedPrefs);
         */
    }

    private void onBindViewHolderTags(@NonNull BooksViewHolder holder, int position) {
        holder.tag1Txt.setText("");
        holder.tag2Txt.setText("");
        holder.tag3Txt.setText("");
        if (dataTags1[position].length() == 0) {
            if (dataTags2[position].length() == 0) {
                if (dataTags3[position].length() != 0)
                    holder.tag1Txt.setText("#" + dataTags3[position]);
            }
            else {
                holder.tag1Txt.setText("#" + dataTags2[position]);
                if (dataTags3[position].length() != 0)
                    holder.tag2Txt.setText("#" + dataTags3[position]);
            }
        }
        else {
            holder.tag1Txt.setText("#" + dataTags1[position]);
            if (dataTags2[position].length() == 0) {
                if (dataTags3[position].length() != 0)
                    holder.tag2Txt.setText("#" + dataTags3[position]);
            }
            else {
                holder.tag2Txt.setText("#" + dataTags2[position]);
                if (dataTags3[position].length() != 0)
                    holder.tag3Txt.setText("#" + dataTags3[position]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataTitles.length;
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {

        TextView titleTxt, volumeTxt, authorTxt, tag1Txt, tag2Txt, tag3Txt;
        ImageView imageView;
        CheckBox checkBool;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxtView);
            volumeTxt = itemView.findViewById(R.id.volumeTxtView);
            authorTxt = itemView.findViewById(R.id.authorTxtView);
            tag1Txt = itemView.findViewById(R.id.tag1TxtView);
            tag2Txt = itemView.findViewById(R.id.tag2TxtView);
            tag3Txt = itemView.findViewById(R.id.tag3TxtView);
            imageView = itemView.findViewById(R.id.coverImgV);
            checkBool = itemView.findViewById(R.id.checkReadNotRead);
        }
    }
}