package com.example.alexandrie;

import java.util.ArrayList;
import java.util.Date;

public class API
{
    ArrayList<Book> all_books_from_api;

    public API(ArrayList<Book> all_books_from_api)
    {
        this.all_books_from_api = all_books_from_api;
    }

    public API()
    {
    }

    public ArrayList<Book> getAll_books_from_api() {
        return all_books_from_api;
    }

    public void setAll_books_from_api(ArrayList<Book> all_books_from_api) {
        this.all_books_from_api = all_books_from_api;
    }

    public ArrayList<Book> getAllBooks()
    {
        this.all_books_from_api = new ArrayList<>();

        Integer[] image_livres = {R.drawable.central_park, R.drawable.demain,
                R.drawable.germinal, R.drawable.mon_amie_adele, R.drawable.sauve_moi,
                R.drawable.devenir, R.drawable.billy_summers, R.drawable.six_ans_deja
        };
        String[] titre_livres = {"Central Park", "Demain", "Germinal", "Mon amie adèle", "Sauve-moi", "Devenir", "Billy Summers", "Six ans déjà"};
        String[] auteur_livres = { "Guillaume Musso", "Guillaume Musso", "Emile Zola", "Sarah Pinborough", "Guillaume Musso", "Michelle Obama", "Stephen King", "Harlan Coben"};
        String[] resume_livres = {"Alice et Gabriel n'ont aucun souvenir de la nuit dernière... Pourtant, ils ne sont pas près de l'oublier. New York, huit heures du matin. Alice, jeune flic parisienne, et Gabriel, pianiste de jazz américain, se réveillent menottés l'un à l'autre sur un banc de Central Park. Ils ne se connaissent pas et n'ont aucun souvenir de leur rencontre. La veille au soir, Alice faisait la fête avec ses copines sur les Champs-Elysées tandis que Gabriel jouait du piano dans un club de Dublin. Impossible ?",
                "Emma vit à New York. À trente-deux ans, elle continue à chercher l'homme de sa vie. Matthew habite Boston. Il a perdu sa femme dans un terrible accident et élève seul sa petite fille.\n" + "\n" + "Ils font connaissance grâce à Internet et, désireux de se rencontrer, se donnent bientôt rendez-vous dans un restaurant de Manhattan. Le même jour à la même heure, ils poussent chacun à leur tour la porte de l'établissement, sont conduits à la même table et pourtant... ils ne se croiseront jamais.",
                "Etienne Lantier est un jeune machiniste qui vient de perdre son travail, parce qu'il a giflé son chef sous l'emprise de l'alcool. Il cherche un travail dans les mines de Montsou. Il y rencontre Maheu et Maheude et leurs enfants parmi lesquels Catherine, dont il tombe amoureux. Très apprécié à la mine, il initie un mouvement de grève.",
                "LOUISE\n" + "Mère célibataire, elle est coincée dans un quotidien minuté. Un soir pourtant elle embrasse un homme dans un bar… sans savoir qu’il est son nouveau patron.\n" + "\n" + "DAVID\n" + "Psychiatre renommé et dévoué à sa femme, il regrette ce baiser mais ne peut s’empêcher de tomber amoureux de son assistante.\n" + "\n" + "ADÈLE\n" + "L’épouse de David semble n’avoir aucun défaut. Si ce n’est de vouloir à tout prix devenir l’amie de Louise... Fascinée par ce couple modèle, Louise se retrouve malgré elle piégée au coeur de leur mariage. Et peu à peu, elle commence à entrevoir des failles.\n" + "\n" + "David est-il l’homme qu’il prétend être ?\n" + "Adèle, aussi vulnérable qu’elle y paraît ?\n" + "Et par quel secret inavouable sont-ils liés l’un à l’autre ?",
                "Sam Galoway, new-yorkais d'une trentaine d'années et travailleur opiniâtre depuis le suicide de sa femme Federica, rencontre un jour à Broadway Juliette, jolie Française de 28 ans, serveuse dans un bar, qui rêve de devenir actrice. Ils tombent amoureux, et vivent un long week-end de bonheur. Chacun a menti à l'autre pendant ces quelques jours passés ensemble. Sam se disant encore marié et Juliette se prétendant avocate1. Mais Juliette doit repartir à Paris pour retrouver sa famille. Sam la laisse à l’aéroport, et désespéré, va dans un bar et entend à la télévision que l’avion de Juliette a explosé en plein vol...",
                "\"Il y a encore tant de choses que j'ignore au sujet de l'Amérique, de la vie, et de ce que l'avenir nous réserve. Mais je sais qui je suis. Mon père, Fraser, m'a appris à travailler dur, à rire souvent et à tenir parole. Ma mère, Marian, à penser par moi-même et à faire entendre ma voix. Tous les deux ensemble, dans notre petit appartement du quartier du South Side de Chicago, ils m'ont aidée à saisir ce qui faisait la valeur de notre histoire, de mon histoire, et plus largement de l'histoire de notre pays. Même quand elle est loin d'être belle et parfaite. Même quand la réalité se rappelle à vous plus que vous ne l'auriez souhaité. Votre histoire vous appartient, et elle vous appartiendra toujours. À vous de vous en emparer.\"\n" +
                        "Michelle Obama",
                "L'histoire d'un type bien, qui fait un sale boulot. Billy Summers est un tueur à gages, le meilleur de sa profession, mais il n'accepte de liquider que les salauds. Aujourd'hui, Billy veut décrocher. Avant cela, seul dans sa chambre, il se prépare pour sa dernière mission...À la fois thriller, récit de guerre, road trip et déclaration d'amour à l'Amérique des petites villes, Billy Summers est l'un des romans les plus surprenants dans l'œuvre de Stephen King, qui y a mis tout son génie et son humanité.",
                "Six ans ont passé depuis que Jake a vu Natalie, la femme de sa vie, en épouser un autre. Six ans à lutter contre lui-même pour tenir sa promesse de ne pas chercher à la revoir. Et puis un jour, une nécro : Natalie est veuve. Et soudain, l'espoir renaît. Mais aux funérailles, c'est une parfaite inconnue qui apparaît. Où est Natalie ? Pourquoi s'est-elle évaporée six ans plus tôt ? Jusqu'où lui a-t-elle menti ? Déterminé à retrouver celle qui lui a brisé le cur, Jake va devenir la proie d'une machination meurtrière assassine. Et découvrir qu'en amour, il est des vérités qui tuent..."
        };
        Date[] date_books = {new Date(2014,3,27), new Date(2013, 2, 28),
                new Date(1993, 9, 29),  new Date(2016, 12, 9),
                new Date(2005, 01,01), new Date(2018, 11, 13),
                new Date(2021, 8, 3), new Date(2013,1,1)};
        Integer[] rating_books = {2, 3, 4, 5, 3, 4, 3, 4};
        String[][] list_of_tags_books = {{"fantastique"}, {"sci-fi"}, {"manga"}, {"horreur"}, {"thriller"}, {"auto-biographique"}, {"policier", "thriller"}, {"policier", "thriller"}};

        for (int i=0; i<image_livres.length; i++)
        {
            com.example.alexandrie.Book livre_à_ajouter = new com.example.alexandrie.Book(image_livres[i], titre_livres[i], auteur_livres[i],date_books[i], resume_livres[i], rating_books[i], list_of_tags_books[i]);
            this.all_books_from_api.add(livre_à_ajouter);
        }

        return this.all_books_from_api;
    }
}
