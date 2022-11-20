package com.example.alexandrie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ActivityDisplayMenu extends AppCompatActivity
{
    ArrayList<Book> all_books;

    RecyclerView recycler_view_books_well_rated;
    ArrayList<Book> books_well_rated;
    MenuAdapter menuAdapter_books_well_rated;

    RecyclerView recycler_view_books_recently_published;
    ArrayList<Book> books_recently_published;
    MenuAdapter menuAdapter_recently_published;

    RecyclerView recycler_view_books_for_reader;
    ArrayList<Book> books_for_reader;
    MenuAdapter menuAdapter_for_reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent;
        intent = new Intent(ActivityDisplayMenu.this, ListBooksActivity.class);
        // if (activityToReturnStr.equals("ListBooksActivity"))
        fragmentManager.beginTransaction().add(R.id.topBarActivityDisplayMenu, new AppBarFragment(intent)).commit();

        recycler_view_books_well_rated = findViewById(R.id.layout_books_well_rated);
        recycler_view_books_recently_published = findViewById(R.id.layout_books_recently_published);
        recycler_view_books_for_reader = findViewById(R.id.layout_books_for_reader);

        API api_books = new API();
        all_books = api_books.getAllBooks();

        // RecyclerView pour les livres les mieux notés
        books_well_rated = getWellRatedBooks(all_books);
        LinearLayoutManager layoutManager_books_well_rated = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_well_rated.setLayoutManager(layoutManager_books_well_rated);
        recycler_view_books_well_rated.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_books_well_rated = new MenuAdapter(ActivityDisplayMenu.this, books_well_rated);
        recycler_view_books_well_rated.setAdapter(menuAdapter_books_well_rated);

        // RecyclerView pour les nouveautés
        books_recently_published = getRecentBooks(all_books);
        LinearLayoutManager layoutManager_recently_published = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_recently_published.setLayoutManager(layoutManager_recently_published);
        recycler_view_books_recently_published.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_recently_published = new MenuAdapter(ActivityDisplayMenu.this, books_recently_published);
        recycler_view_books_recently_published.setAdapter(menuAdapter_recently_published);

        // RecyclerView pour les recommandations en fonction des tags
        String[] reader_tags = {"fantastique", "manga"};
        books_for_reader = getBooksForReader(all_books, reader_tags);
        LinearLayoutManager layoutManager_currently_read = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_for_reader.setLayoutManager(layoutManager_currently_read);
        recycler_view_books_for_reader.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_for_reader = new MenuAdapter(ActivityDisplayMenu.this, books_for_reader);
        recycler_view_books_for_reader .setAdapter(menuAdapter_for_reader );
    }


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

}