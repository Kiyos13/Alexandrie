package com.example.alexandrie;

import java.util.ArrayList;

public class Book
{
    Integer image;
    String title;
    String author;
    String summary;
    Integer average_rating;
    String isbn;
    ArrayList<String> list_of_tags;

    public Book(Integer image, String title, String author, Integer average_rating)
    {
        this.image = image;
        this.title = title;
        this.author = author;
        this.average_rating = average_rating;
    }

    public Book(Integer image, String title, String author, String summary, Integer average_rating)
    {
        this.image = image;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.average_rating = average_rating;
    }

    public Book(Integer image, String title, String author, String summary, Integer average_rating, String isbn, ArrayList<String> list_of_tags)
    {
        this.image = image;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.average_rating = average_rating;
        this.isbn = isbn;
        this.list_of_tags = list_of_tags;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Integer average_rating) {
        this.average_rating = average_rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public ArrayList<String> getList_of_tags() {
        return list_of_tags;
    }

    public void setList_of_tags(ArrayList<String> list_of_tags) {
        this.list_of_tags = list_of_tags;
    }
}

