package com.example.alexandrie;

import androidx.appcompat.app.AppCompatActivity;
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
    ArrayList<Book> tous_les_livres;

    RecyclerView recycler_view_livres_en_cours_de_lecture;
    ArrayList<Book> livres_en_cours_de_lecture;
    MenuAdapter mainAdapter_livres_en_cours_de_lecture;

    RecyclerView recycler_view_livres_parus_récemment;
    ArrayList<Book> livres_parus_récemment;
    MenuAdapter mainAdapter_livres_parus_récemment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        recycler_view_livres_en_cours_de_lecture = findViewById(R.id.livres_en_cours_de_lecture);
        recycler_view_livres_parus_récemment = findViewById(R.id.livres_parus_récemment);

        Integer[] image_livres = {R.drawable.persuasion, R.drawable.central_park, R.drawable.demain,
                R.drawable.germinal, R.drawable.mon_amie_adele};
        String[] titre_livres = {"Persuasion", "Central Park", "Demain", "Germinal", "Mon amie adèle"};
        String[] auteur_livres = {"Jane Austen", "Guillaume Musso", "Guillaume Musso", "Emile Zola", "Sarah Pinborough"};
        String[] resume_livres = {"Sept ans auparavant, Anne Eliott a laissé filer l’amour en la personne de Frederick Wentworth, un jeune officier de marine avec lequel elle s’était secrètement fiancée et à qui elle s’est finalement laissée convaincre de refuser le mariage.\n" + "Les années ont passé et l’amour semble avoir disparu à l’horizon tel le navire ayant emporté aux sons des canons lointains son amour déçu…\n" + "C’était sans compter sur les vagues qui ramènent toujours tôt ou tard ce qu’elles prennent…",
                "Alice et Gabriel n'ont aucun souvenir de la nuit dernière... Pourtant, ils ne sont pas près de l'oublier. New York, huit heures du matin. Alice, jeune flic parisienne, et Gabriel, pianiste de jazz américain, se réveillent menottés l'un à l'autre sur un banc de Central Park. Ils ne se connaissent pas et n'ont aucun souvenir de leur rencontre. La veille au soir, Alice faisait la fête avec ses copines sur les Champs-Elysées tandis que Gabriel jouait du piano dans un club de Dublin. Impossible ?",
                "Emma vit à New York. À trente-deux ans, elle continue à chercher l'homme de sa vie. Matthew habite Boston. Il a perdu sa femme dans un terrible accident et élève seul sa petite fille.\n" + "\n" + "Ils font connaissance grâce à Internet et, désireux de se rencontrer, se donnent bientôt rendez-vous dans un restaurant de Manhattan. Le même jour à la même heure, ils poussent chacun à leur tour la porte de l'établissement, sont conduits à la même table et pourtant... ils ne se croiseront jamais.",
                "Etienne Lantier est un jeune machiniste qui vient de perdre son travail, parce qu'il a giflé son chef sous l'emprise de l'alcool. Il cherche un travail dans les mines de Montsou. Il y rencontre Maheu et Maheude et leurs enfants parmi lesquels Catherine, dont il tombe amoureux. Très apprécié à la mine, il initie un mouvement de grève.",
                "LOUISE\n" + "Mère célibataire, elle est coincée dans un quotidien minuté. Un soir pourtant elle embrasse un homme dans un bar… sans savoir qu’il est son nouveau patron.\n" + "\n" + "DAVID\n" + "Psychiatre renommé et dévoué à sa femme, il regrette ce baiser mais ne peut s’empêcher de tomber amoureux de son assistante.\n" + "\n" + "ADÈLE\n" + "L’épouse de David semble n’avoir aucun défaut. Si ce n’est de vouloir à tout prix devenir l’amie de Louise... Fascinée par ce couple modèle, Louise se retrouve malgré elle piégée au coeur de leur mariage. Et peu à peu, elle commence à entrevoir des failles.\n" + "\n" + "David est-il l’homme qu’il prétend être ?\n" + "Adèle, aussi vulnérable qu’elle y paraît ?\n" + "Et par quel secret inavouable sont-ils liés l’un à l’autre ?"
        };

        tous_les_livres = new ArrayList<>();
        livres_en_cours_de_lecture = new ArrayList<>();
        livres_parus_récemment = new ArrayList<>();
        for(int i=0; i<image_livres.length; i++)
        {
            Book livre_à_ajouter = new Book(image_livres[i], titre_livres[i], auteur_livres[i], resume_livres[i]);
            tous_les_livres.add(livre_à_ajouter);
        }

        LinearLayoutManager layoutManager_livres_en_cours_de_lecture = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );

        recycler_view_livres_en_cours_de_lecture.setLayoutManager(layoutManager_livres_en_cours_de_lecture);
        recycler_view_livres_en_cours_de_lecture.setItemAnimator(new DefaultItemAnimator());

        mainAdapter_livres_en_cours_de_lecture = new MenuAdapter(ActivityDisplayMenu.this, tous_les_livres, this);
        recycler_view_livres_en_cours_de_lecture.setAdapter(mainAdapter_livres_en_cours_de_lecture);

        LinearLayoutManager layoutManager_livres_parus_récemment = new LinearLayoutManager(
                ActivityDisplayMenu.this, LinearLayoutManager.HORIZONTAL, false
        );
        recycler_view_livres_parus_récemment.setLayoutManager(layoutManager_livres_parus_récemment);
        recycler_view_livres_parus_récemment.setItemAnimator(new DefaultItemAnimator());

        mainAdapter_livres_parus_récemment = new MenuAdapter(ActivityDisplayMenu.this, tous_les_livres, this);
        recycler_view_livres_parus_récemment.setAdapter(mainAdapter_livres_parus_récemment);
    }

    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(ActivityDisplayMenu.this, DisplayDetailBook.class);

        intent.putExtra("IMAGE", tous_les_livres.get(position).getImage());
        intent.putExtra("TITRE", tous_les_livres.get(position).getTitle());
        intent.putExtra("AUTEUR", tous_les_livres.get(position).getAuthor());
        intent.putExtra("RESUME", tous_les_livres.get(position).getSummary());

        startActivity(intent);
    }
}