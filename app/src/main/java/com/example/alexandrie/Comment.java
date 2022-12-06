package com.example.alexandrie;

public class Comment
{
    String name_of_writer_comment;
    String title_of_commented_book;
    String author_of_commented_book;
    String text;

    public Comment(String name_of_writer_comment, String title_of_commented_book, String author_of_commented_book, String text)
    {
        this.name_of_writer_comment = name_of_writer_comment;
        this.title_of_commented_book = title_of_commented_book;
        this.author_of_commented_book = author_of_commented_book;
        this.text = text;
    }

    public String getName_of_writer_comment() {
        return name_of_writer_comment;
    }

    public void setName_of_writer_comment(String name_of_writer_comment) {
        this.name_of_writer_comment = name_of_writer_comment;
    }

    public String getTitle_of_commented_book() {
        return title_of_commented_book;
    }

    public void setTitle_of_commented_book(String title_of_commented_book) {
        this.title_of_commented_book = title_of_commented_book;
    }

    public String getAuthor_of_commented_book() {
        return author_of_commented_book;
    }

    public void setAuthor_of_commented_book(String author_of_commented_book) {
        this.author_of_commented_book = author_of_commented_book;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
