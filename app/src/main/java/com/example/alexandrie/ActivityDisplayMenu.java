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

public class ActivityDisplayMenu extends AppCompatActivity implements RecyclerViewInterface
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);


        RecyclerView rvItem = findViewById(R.id.tous_les_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityDisplayMenu.this, LinearLayoutManager.VERTICAL,false );
        MenuSupItemAdapter itemAdapter = new MenuSupItemAdapter(buildItemList());
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(layoutManager);
    }

    private ArrayList<MenuSupItem> buildItemList()
    {
        ArrayList<MenuSupItem> itemList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            MenuSupItem item = new MenuSupItem("Liste "+i, buildSubItemList());
            itemList.add(item);
        }
        return itemList;
    }

    private ArrayList<Book> buildSubItemList()
    {
        ArrayList<Book> subItemList = new ArrayList<>();

        Integer[] image_livres = {R.drawable.central_park, R.drawable.demain,
                R.drawable.germinal, R.drawable.mon_amie_adele};
        String[] titre_livres = {"Central Park", "Demain", "Germinal", "Mon amie adèle"};
        String[] auteur_livres = { "Guillaume Musso", "Guillaume Musso", "Emile Zola", "Sarah Pinborough"};
        String[] resume_livres = {"Alice et Gabriel n'ont aucun souvenir de la nuit dernière... Pourtant, ils ne sont pas près de l'oublier. New York, huit heures du matin. Alice, jeune flic parisienne, et Gabriel, pianiste de jazz américain, se réveillent menottés l'un à l'autre sur un banc de Central Park. Ils ne se connaissent pas et n'ont aucun souvenir de leur rencontre. La veille au soir, Alice faisait la fête avec ses copines sur les Champs-Elysées tandis que Gabriel jouait du piano dans un club de Dublin. Impossible ?",
                "Emma vit à New York. À trente-deux ans, elle continue à chercher l'homme de sa vie. Matthew habite Boston. Il a perdu sa femme dans un terrible accident et élève seul sa petite fille.\n" + "\n" + "Ils font connaissance grâce à Internet et, désireux de se rencontrer, se donnent bientôt rendez-vous dans un restaurant de Manhattan. Le même jour à la même heure, ils poussent chacun à leur tour la porte de l'établissement, sont conduits à la même table et pourtant... ils ne se croiseront jamais.",
                "Etienne Lantier est un jeune machiniste qui vient de perdre son travail, parce qu'il a giflé son chef sous l'emprise de l'alcool. Il cherche un travail dans les mines de Montsou. Il y rencontre Maheu et Maheude et leurs enfants parmi lesquels Catherine, dont il tombe amoureux. Très apprécié à la mine, il initie un mouvement de grève.",
                "LOUISE\n" + "Mère célibataire, elle est coincée dans un quotidien minuté. Un soir pourtant elle embrasse un homme dans un bar… sans savoir qu’il est son nouveau patron.\n" + "\n" + "DAVID\n" + "Psychiatre renommé et dévoué à sa femme, il regrette ce baiser mais ne peut s’empêcher de tomber amoureux de son assistante.\n" + "\n" + "ADÈLE\n" + "L’épouse de David semble n’avoir aucun défaut. Si ce n’est de vouloir à tout prix devenir l’amie de Louise... Fascinée par ce couple modèle, Louise se retrouve malgré elle piégée au coeur de leur mariage. Et peu à peu, elle commence à entrevoir des failles.\n" + "\n" + "David est-il l’homme qu’il prétend être ?\n" + "Adèle, aussi vulnérable qu’elle y paraît ?\n" + "Et par quel secret inavouable sont-ils liés l’un à l’autre ?"
        };

        for (int i=0; i<image_livres.length; i++) {
            Book livre_à_ajouter = new Book(image_livres[i], titre_livres[i], auteur_livres[i], resume_livres[i], 2);
            subItemList.add(livre_à_ajouter);
        }
        return subItemList;
    }

    @Override
    public void onItemClick(int position)
    {
        ArrayList<Book> all_books = buildSubItemList();
        Intent intent = new Intent(ActivityDisplayMenu.this, DisplayDetailBook.class);

        intent.putExtra("IMAGE", all_books.get(position).getImage());
        intent.putExtra("TITRE", all_books.get(position).getTitle());
        intent.putExtra("AUTEUR", all_books.get(position).getAuthor());
        intent.putExtra("RESUME", all_books.get(position).getSummary());

        startActivity(intent);
    }
}