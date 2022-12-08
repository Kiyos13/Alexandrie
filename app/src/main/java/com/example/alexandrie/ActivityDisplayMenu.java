package com.example.alexandrie;

import static com.example.alexandrie.GenresSelectorFragment.initListBookGenresAndListBookGenresSelected;
import static com.example.alexandrie.GenresSelectorFragment.listBookGenres;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksAddDate;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksReadStatus;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag1;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag2;
import static com.example.alexandrie.OneBookAllInfoActivity.indexInSharedPrefBooksTag3;
import static com.example.alexandrie.OneBookAllInfoActivity.nbFieldsInSharedPrefBooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ActivityDisplayMenu extends AppCompatActivity
{
    ArrayList<Book> all_books;

    RecyclerView recycler_view_books_favorite_genre;
    ArrayList<Book> books_favorite_genre;
    MenuAdapter menuAdapter_books_favorite_genre;

    RecyclerView recycler_view_books_recently_added;
    ArrayList<Book> books_recently_added;
    MenuAdapter menuAdapter_recently_added;

    RecyclerView recycler_view_books_not_read;
    ArrayList<Book> books_not_read;
    MenuAdapter menuAdapter_not_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent;
        intent = new Intent(ActivityDisplayMenu.this, ListBooksActivity.class);
        // if (activityToReturnStr.equals("ListBooksActivity"))
        fragmentManager.beginTransaction().add(R.id.topBarActivityDisplayMenu, new AppBarFragment(intent)).commit();

        recycler_view_books_favorite_genre = findViewById(R.id.layout_books_favorite_genre);
        recycler_view_books_recently_added = findViewById(R.id.layout_books_recently_added);
        recycler_view_books_not_read = findViewById(R.id.layout_books_not_read);

        API api_books = new API();
        all_books = api_books.getAllBooks();

        // RecyclerView for the books of the most added genre
        // books_favorite_genre = getWellRatedBooks(all_books);
        books_favorite_genre = retrieveBooksFromCondition(sharedPrefBooks, "favoriteGenre");
        LinearLayoutManager layoutManager_books_favorite_genre = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_favorite_genre.setLayoutManager(layoutManager_books_favorite_genre);
        recycler_view_books_favorite_genre.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_books_favorite_genre = new MenuAdapter(ActivityDisplayMenu.this, books_favorite_genre);
        recycler_view_books_favorite_genre.setAdapter(menuAdapter_books_favorite_genre);

        // RecyclerView for recently added books
        // books_recently_added = getRecentBooks(all_books);
        books_recently_added = retrieveBooksFromCondition(sharedPrefBooks, "recentlyAdded");
        LinearLayoutManager layoutManager_recently_added = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_recently_added.setLayoutManager(layoutManager_recently_added);
        recycler_view_books_recently_added.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_recently_added = new MenuAdapter(ActivityDisplayMenu.this, books_recently_added);
        recycler_view_books_recently_added.setAdapter(menuAdapter_recently_added);

        // RecyclerView for books not read yet
        // String[] reader_tags = {"fantastique", "manga"};
        // books_not_read = getBooksForReader(all_books, reader_tags);
        books_not_read = retrieveBooksFromCondition(sharedPrefBooks, "notRead");
        LinearLayoutManager layoutManager_not_read = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_not_read.setLayoutManager(layoutManager_not_read);
        recycler_view_books_not_read.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_not_read = new MenuAdapter(ActivityDisplayMenu.this, books_not_read);
        recycler_view_books_not_read .setAdapter(menuAdapter_not_read);
    }



    private ArrayList<Book> retrieveBooksFromCondition(SharedPreferences sharedPreferences, String condition) {
        ArrayList<ArrayList<String>> books = new ArrayList<>();
        for (int i = 0; i <= nbFieldsInSharedPrefBooks; i++) {
            books.add(i, new ArrayList<String>());
        }

        Map<String, ?> allEntries = sharedPreferences.getAll();
        String bookData;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            bookData = entry.getValue().toString();
            bookData = bookData.substring(1);
            bookData = bookData.substring(0, bookData.length() - 1);

            List<String> bookDataList = new ArrayList<String>(Arrays.asList(bookData.split(", ")));
            SortStringListByFirstChar(bookDataList);

            int bookDataListLength = bookDataList.size();
            String currentData;
            for (int i = 0; i < bookDataListLength; i++) {
                currentData = bookDataList.get(i).substring(2);
                bookDataList.set(i, currentData);
            }

            int nbFields = books.size();

            if (condition.equals("favoriteGenre")) {
                String favoriteGenre = retrieveFavoriteGenreFromSharedPreferences(sharedPreferences);
                boolean hasFavoriteGenreTag1 = (bookDataList.get(indexInSharedPrefBooksTag1).equals(favoriteGenre));
                boolean hasFavoriteGenreTag2 = (bookDataList.get(indexInSharedPrefBooksTag2).equals(favoriteGenre));
                boolean hasFavoriteGenreTag3 = (bookDataList.get(indexInSharedPrefBooksTag3).equals(favoriteGenre));
                boolean hasFavoriteGenreTag = hasFavoriteGenreTag1 || hasFavoriteGenreTag2 || hasFavoriteGenreTag3;
                if ((bookDataList.size() >= nbFields) && hasFavoriteGenreTag) {
                    for (int i = 0; i < nbFields; i++)
                        books.get(i).add(bookDataList.get(i));
                }
            }
            else if (condition.equals("notRead")) {
                boolean isNotRead = (Boolean.parseBoolean(bookDataList.get(indexInSharedPrefBooksReadStatus)) == false);
                if ((bookDataList.size() >= nbFields) && isNotRead) {
                    for (int i = 0; i < nbFields; i++)
                        books.get(i).add(bookDataList.get(i));
                }
            }
            else if (condition.equals("recentlyAdded")) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = formatter.parse(bookDataList.get(indexInSharedPrefBooksAddDate));
                    Date now = new Date(System.currentTimeMillis());
                    long difference = Math.abs(date.getTime() - now.getTime());
                    long differenceDatesInDays = difference / (24 * 60 * 60 * 1000);
                    boolean dateIsLessThan90Days = (differenceDatesInDays <= 90);
                    if ((bookDataList.size() >= nbFields) && dateIsLessThan90Days) {
                        for (int i = 0; i < nbFields; i++)
                            books.get(i).add(bookDataList.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return arrayListOfArrayListStringToBookArrayList(books);
    }

    private ArrayList<Book> arrayListOfArrayListStringToBookArrayList(ArrayList<ArrayList<String>> booksArrayList) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String> bookParams = new ArrayList<>();
        for (int i = 0; i < booksArrayList.get(0).size(); i++) {
            for (int j = 0; j <= nbFieldsInSharedPrefBooks; j++)
                bookParams.add(j, booksArrayList.get(j).get(i));
            books.add(new Book(bookParams));
        }
        return books;
    }

    private String retrieveFavoriteGenreFromSharedPreferences(SharedPreferences sharedPreferences) {
        String[] genresArray = getResources().getStringArray(R.array.all_book_genres);
        initListBookGenresAndListBookGenresSelected(genresArray);
        int nbGenres = listBookGenres.size();
        ArrayList<Integer> genresOccurrences = new ArrayList<>();
        for (int i = 0; i < nbGenres; i++)
            genresOccurrences.add(0);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        String bookData;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            bookData = entry.getValue().toString();
            bookData = bookData.substring(1);
            bookData = bookData.substring(0, bookData.length() - 1);

            List<String> bookDataList = new ArrayList<String>(Arrays.asList(bookData.split(", ")));
            SortStringListByFirstChar(bookDataList);

            int bookDataListLength = bookDataList.size();
            String currentData;
            for (int i = 0; i < bookDataListLength; i++) {
                currentData = bookDataList.get(i).substring(2);
                bookDataList.set(i, currentData);
            }

            for (int i = 0; i < nbGenres; i++) {
                if (bookDataList.size() >= nbFieldsInSharedPrefBooks) {
                    boolean tag1 = listBookGenres.get(i).equals(bookDataList.get(indexInSharedPrefBooksTag1));
                    boolean tag2 = listBookGenres.get(i).equals(bookDataList.get(indexInSharedPrefBooksTag2));
                    boolean tag3 = listBookGenres.get(i).equals(bookDataList.get(indexInSharedPrefBooksTag3));
                    if (tag1 || tag2 || tag3) {
                        genresOccurrences.set(i, genresOccurrences.get(i) + 1);
                    }
                }
            }
        }

        Integer maxOccurrence = Collections.max(genresOccurrences);
        Integer maxIndex = genresOccurrences.indexOf(maxOccurrence);

        return listBookGenres.get(maxIndex);
    }





    /*
    private ArrayList<Book> getRecentBooks(ArrayList<Book> allBooksReceived)
    {
        ArrayList<Book> recentBooks = new ArrayList<>();

        for (int i=0; i<allBooksReceived.size(); i++)
        {
            Book book = allBooksReceived.get(i);
            Integer this_year = 2022;

            if((this_year - 10) <= book.getDate().getYear())
            {
                if(book.getDate().getYear() <= this_year)
                {
                    recentBooks.add(book);
                }
            }
        }

        return recentBooks;
    }

    private ArrayList<Book> getWellRatedBooks(ArrayList<Book> allBooksReceived)
    {
        ArrayList<Book> wellRatedBooks = new ArrayList<>();

        for (int i=0; i<allBooksReceived.size(); i++)
        {
            Book book = allBooksReceived.get(i);

            if(book.getAverage_rating() > 3)
            {
                wellRatedBooks.add(book);
            }
        }

        return wellRatedBooks;
    }

    private ArrayList<Book> getBooksForReader(ArrayList<Book> allBooksReceived, String[] tags_of_reader)
    {
        ArrayList<Book> booksForReader = new ArrayList<>();

        for (int i=0; i<allBooksReceived.size(); i++)
        {
            Book book = allBooksReceived.get(i);
            for (int j=0; j<tags_of_reader.length; j++)
            {
                if(Arrays.asList(book.getList_of_tags()).contains(tags_of_reader[j]) == true)
                {
                    if(booksForReader.contains(book) == false)
                    {
                        booksForReader.add(book);
                    }
                }
            }
        }

        return booksForReader;
    }
    */

}