package com.example.alexandrie;

public class Book
{
    Integer image;
    String title;
    String author;
    String summary;

    public Book(Integer image, String title, String author)
    {
        this.image = image;
        this.title = title;
        this.author = author;
    }

    public Book(Integer image, String title, String author, String summary)
    {
        this.image = image;
        this.title = title;
        this.author = author;
        this.summary = summary;
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
}

