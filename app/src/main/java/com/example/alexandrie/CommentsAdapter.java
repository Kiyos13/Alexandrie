package com.example.alexandrie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>
{
    ArrayList<Comment> comments;
    Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> comments)
    {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_comment_item_row, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position)
    {
        holder.name_of_writer_commentView.setText(comments.get(position).getName_of_writer_comment());
        holder.text_commentView.setText(comments.get(position).getText());
    }

    @Override
    public int getItemCount()
    {
        return comments.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name_of_writer_commentView;
        TextView text_commentView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name_of_writer_commentView = itemView.findViewById(R.id.name_of_writer_comment_view);
            text_commentView = itemView.findViewById(R.id.text_comment_view);
        }
    }
}
