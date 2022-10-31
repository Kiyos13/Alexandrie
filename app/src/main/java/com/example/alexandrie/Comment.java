package com.example.alexandrie;

public class Comment
{
    String name_of_writer_comment;
    String text;

    public Comment(String name_of_writer_comment, String text)
    {
        this.name_of_writer_comment = name_of_writer_comment;
        this.text = text;
    }

    public String getName_of_writer_comment()
    {
        return name_of_writer_comment;
    }

    public void setName_of_writer_comment(String name_of_writer_comment)
    {
        this.name_of_writer_comment = name_of_writer_comment;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
