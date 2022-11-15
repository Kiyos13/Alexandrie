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

public class ActivityDisplayMenu extends AppCompatActivity  implements RecyclerViewInterface
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

        all_books = getAllBooks();

        // RecyclerView pour les livres les mieux notés
        books_well_rated = getWellRatedBooks(all_books);
        LinearLayoutManager layoutManager_books_well_rated = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_well_rated.setLayoutManager(layoutManager_books_well_rated);
        recycler_view_books_well_rated.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_books_well_rated = new MenuAdapter(ActivityDisplayMenu.this, books_well_rated, this);
        recycler_view_books_well_rated.setAdapter(menuAdapter_books_well_rated);

        // RecyclerView pour les nouveautés
        books_recently_published = getRecentBooks(all_books);
        LinearLayoutManager layoutManager_recently_published = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_recently_published.setLayoutManager(layoutManager_recently_published);
        recycler_view_books_recently_published.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_recently_published = new MenuAdapter(ActivityDisplayMenu.this, books_recently_published, this);
        recycler_view_books_recently_published.setAdapter(menuAdapter_recently_published);

        // RecyclerView pour les recommandations en fonction des tags
        String[] reader_tags = {"fantastique", "manga"};
        books_for_reader = getBooksForReader(all_books, reader_tags);
        LinearLayoutManager layoutManager_currently_read = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_books_for_reader.setLayoutManager(layoutManager_currently_read);
        recycler_view_books_for_reader.setItemAnimator(new DefaultItemAnimator());
        menuAdapter_for_reader = new MenuAdapter(ActivityDisplayMenu.this, books_for_reader , this);
        recycler_view_books_for_reader .setAdapter(menuAdapter_for_reader );
    }

    private ArrayList<Book> getAllBooks()
    {
        ArrayList<Book> allBooks = new ArrayList<>();

        Integer[] image_livres = {R.drawable.central_park, R.drawable.demain,
                R.drawable.germinal, R.drawable.mon_amie_adele};
        String[] titre_livres = {"Central Park", "Demain", "Germinal", "Mon amie adèle"};
        String[] auteur_livres = { "Guillaume Musso", "Guillaume Musso", "Emile Zola", "Sarah Pinborough"};
        String[] resume_livres = {"Alice et Gabriel n'ont aucun souvenir de la nuit dernière... Pourtant, ils ne sont pas près de l'oublier. New York, huit heures du matin. Alice, jeune flic parisienne, et Gabriel, pianiste de jazz américain, se réveillent menottés l'un à l'autre sur un banc de Central Park. Ils ne se connaissent pas et n'ont aucun souvenir de leur rencontre. La veille au soir, Alice faisait la fête avec ses copines sur les Champs-Elysées tandis que Gabriel jouait du piano dans un club de Dublin. Impossible ?",
                "Emma vit à New York. À trente-deux ans, elle continue à chercher l'homme de sa vie. Matthew habite Boston. Il a perdu sa femme dans un terrible accident et élève seul sa petite fille.\n" + "\n" + "Ils font connaissance grâce à Internet et, désireux de se rencontrer, se donnent bientôt rendez-vous dans un restaurant de Manhattan. Le même jour à la même heure, ils poussent chacun à leur tour la porte de l'établissement, sont conduits à la même table et pourtant... ils ne se croiseront jamais.",
                "Etienne Lantier est un jeune machiniste qui vient de perdre son travail, parce qu'il a giflé son chef sous l'emprise de l'alcool. Il cherche un travail dans les mines de Montsou. Il y rencontre Maheu et Maheude et leurs enfants parmi lesquels Catherine, dont il tombe amoureux. Très apprécié à la mine, il initie un mouvement de grève.",
                "LOUISE\n" + "Mère célibataire, elle est coincée dans un quotidien minuté. Un soir pourtant elle embrasse un homme dans un bar… sans savoir qu’il est son nouveau patron.\n" + "\n" + "DAVID\n" + "Psychiatre renommé et dévoué à sa femme, il regrette ce baiser mais ne peut s’empêcher de tomber amoureux de son assistante.\n" + "\n" + "ADÈLE\n" + "L’épouse de David semble n’avoir aucun défaut. Si ce n’est de vouloir à tout prix devenir l’amie de Louise... Fascinée par ce couple modèle, Louise se retrouve malgré elle piégée au coeur de leur mariage. Et peu à peu, elle commence à entrevoir des failles.\n" + "\n" + "David est-il l’homme qu’il prétend être ?\n" + "Adèle, aussi vulnérable qu’elle y paraît ?\n" + "Et par quel secret inavouable sont-ils liés l’un à l’autre ?"
        };
        Date[] date_books = {new Date(2014,3,27), new Date(2013, 2, 28),  new Date(1993, 9, 29),  new Date(2016, 12, 9),};
        Integer[] rating_books = {2, 3, 4, 5};
        String[][] list_of_tags_books = {{"fantastique"}, {"sci-fi"}, {"manga"}, {"horreur"}};

        for (int i=0; i<image_livres.length; i++)
        {
            Book livre_à_ajouter = new Book(image_livres[i], titre_livres[i], auteur_livres[i],date_books[i], resume_livres[i], rating_books[i], list_of_tags_books[i]);
            allBooks.add(livre_à_ajouter);
        }

        return allBooks;
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

    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(ActivityDisplayMenu.this, DisplayDetailBook.class);

        ArrayList<Book> selected_item_list = books_well_rated;

        intent.putExtra("IMAGE", selected_item_list.get(position).getImage());
        intent.putExtra("TITRE", selected_item_list.get(position).getTitle());
        intent.putExtra("AUTEUR", selected_item_list.get(position).getAuthor());
        intent.putExtra("RESUME", selected_item_list.get(position).getSummary());

        startActivity(intent);
    }
}