package com.example.alexandrie;

import java.util.List;

class BookAPIRequestResult {

    private String isbn; // Using ISBN 13

    private String title;
    private List<String> authors;
    private String coverUrl;
    private String publishYear;
    private String publisher;

    public BookAPIRequestResult(String _isbn, String _title, List<String> _authors, String _coverUrl, String _publishYear, String _publisher)
    {
        isbn = _isbn;
        title = _title;
        authors = _authors;
        coverUrl = _coverUrl;
        publishYear = _publishYear;
        publisher = _publisher;
    }

    // Getters / Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String _isbn) { isbn = _isbn; }
    public String getTitle() { return title; }
    public void setTitle(String _title) { title = _title; }
    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> _authors) { authors = _authors; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String _coverUrl) { coverUrl = _coverUrl; }
    public String getPublishYear() { return publishYear; }
    public void setPublishYear(String _publishYear) { publishYear = _publishYear; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String _publisher) { publisher = _publisher; }

}
