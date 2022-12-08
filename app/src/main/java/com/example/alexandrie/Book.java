package com.example.alexandrie;

import static com.example.alexandrie.OneBookAllInfoActivity.getIndexInSharedPrefBooksCoverUrl;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAddDate;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAuthor;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksDescription;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIndex;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksIsFavorite;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksMark;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReadStatus;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReleaseDate;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksSerie;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksSummary;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag1;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag2;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag3;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTitle;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksVolume;

import java.util.ArrayList;
import java.util.Date;

public class Book
{
    Integer image;
    String title;
    String author;
    String summary;
    Date date;
    Integer average_rating;
    String isbn;
    String[] list_of_tags;

    private String indexParam, titleParam, volumeParam, serieParam, authorParam;
    private String addDateParam, releaseDateParam, descriptionParam, summaryParam;
    private String tag1Param, tag2Param, tag3Param, markParam, coverUrlParam;
    private Boolean isReadParam, isFavoriteParam;

    public Book(ArrayList<String> bookParams) {
        this.indexParam = bookParams.get(indexInSharedPrefBooksIndex);
        this.titleParam = bookParams.get(indexInSharedPrefBooksTitle);
        this.volumeParam = bookParams.get(indexInSharedPrefBooksVolume);
        this.serieParam = bookParams.get(indexInSharedPrefBooksSerie);
        this.authorParam = bookParams.get(indexInSharedPrefBooksAuthor);
        this.addDateParam = bookParams.get(indexInSharedPrefBooksAddDate);
        this.releaseDateParam = bookParams.get(indexInSharedPrefBooksReleaseDate);
        this.descriptionParam = bookParams.get(indexInSharedPrefBooksDescription);
        this.summaryParam = bookParams.get(indexInSharedPrefBooksSummary);
        this.tag1Param = bookParams.get(indexInSharedPrefBooksTag1);
        this.tag2Param = bookParams.get(indexInSharedPrefBooksTag2);
        this.tag3Param = bookParams.get(indexInSharedPrefBooksTag3);
        this.markParam = bookParams.get(indexInSharedPrefBooksMark);
        this.coverUrlParam = bookParams.get(getIndexInSharedPrefBooksCoverUrl);
        this.isReadParam = Boolean.parseBoolean(bookParams.get(indexInSharedPrefBooksReadStatus));
        this.isFavoriteParam = Boolean.parseBoolean(bookParams.get(indexInSharedPrefBooksIsFavorite));
    }

    public String getIndexParam() { return indexParam; }
    public String getTitleParam() { return titleParam; }
    public String getVolumeParam() { return volumeParam; }
    public String getSerieParam() { return serieParam; }
    public String getAuthorParam() { return authorParam; }
    public String getAddDateParam() { return addDateParam; }
    public String getReleaseDateParam() { return releaseDateParam; }
    public String getDescriptionParam() { return descriptionParam; }
    public String getSummaryParam() { return summaryParam; }
    public String getTag1Param() { return tag1Param; }
    public String getTag2Param() { return tag2Param; }
    public String getTag3Param() { return tag3Param; }
    public String getMarkParam() { return markParam; }
    public String getCoverUrlParam() { return coverUrlParam; }
    public Boolean getIsReadParam() { return isReadParam; }
    public Boolean getIsFavoriteParam() { return isFavoriteParam; }

    public void setIndexParam(String index) { indexParam = index; }
    public void setTitleParam(String title) { titleParam = title; }
    public void setVolumeParam(String volume) { volumeParam = volume; }
    public void setSerieParam(String serie) { serieParam = serie; }
    public void setAuthorParam(String author) { authorParam = author; }
    public void setAddDateParam(String addDate) { addDateParam = addDate; }
    public void setReleaseDateParam(String releaseDate) { releaseDateParam = releaseDate; }
    public void setDescriptionParam(String description) { descriptionParam = description; }
    public void setSummaryParam(String summary) { summaryParam = summary; }
    public void setTag1Param(String tag1) { tag1Param = tag1; }
    public void setTag2Param(String tag2) { tag2Param = tag2; }
    public void setTag3Param(String tag3) { tag3Param = tag3; }
    public void setMarkParam(String mark) { markParam = mark; }
    public void setCoverUrlParam(String coverUrl) { coverUrlParam = coverUrl; }
    public void setIsReadParam(Boolean isRead) { isReadParam = isRead; }
    public void setIsFavoriteParam(Boolean isFavorite) { isFavoriteParam = isFavorite; }









    public Book(Integer image, String title, String author, Date date, String summary, Integer average_rating, String[] list_of_tags)
    {
        this.image = image;
        this.title = title;
        this.author = author;
        this.date = date;
        this.summary = summary;
        this.average_rating = average_rating;
        this.list_of_tags = list_of_tags;
    }

    public Book(Integer image, String title, String author, Date date, String summary, Integer average_rating, String isbn, String[] list_of_tags)
    {
        this.image = image;
        this.title = title;
        this.author = author;
        this.date = date;
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

    public String[] getList_of_tags() {
        return list_of_tags;
    }

    public void setList_of_tags(String[] list_of_tags) {
        this.list_of_tags = list_of_tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

