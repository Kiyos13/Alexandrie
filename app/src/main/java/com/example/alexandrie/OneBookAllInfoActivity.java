package com.example.alexandrie;

import static com.example.alexandrie.GenresSelectorFragment.initListBookGenresAndListBookGenresSelected;
import static com.example.alexandrie.GenresSelectorFragment.listBookGenres;
import static com.example.alexandrie.GenresSelectorFragment.listBookGenresSelected;
import static com.example.alexandrie.ListBooksActivity.sharedPrefBooks;
import static com.example.alexandrie.LoginConnectionActivity.SortStringListByFirstChar;
import static com.example.alexandrie.LoginConnectionActivity.colorSystemBarTop;
import static com.example.alexandrie.LoginConnectionActivity.enableDisableView;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OneBookAllInfoActivity extends AppCompatActivity {

    private ScrollView oneBookAllInfosScrollV;
    private View editLayout, seeLayout, globalLytEdit;
    private String mode, previousActivity;
    private android.widget.Button saveBookButton, saveEditBookButton;
    private TextInputLayout titleTxtInputLytEdit, volumeTxtInputLytEdit, serieTxtInputLytEdit, authorTxtInputLytEdit, releaseDateTxtInputLytEdit, descriptionTxtInputLytEdit, summaryTxtInputLytEdit;
    private TextInputLayout titleTxtInputLytSee, volumeTxtInputLytSee, serieTxtInputLytSee, authorTxtInputLytSee, releaseDateTxtInputLytSee, descriptionTxtInputLytSee, summaryTxtInputLytSee;
    private TextView addDateTxtVEdit, addDateTxtVSee, genresListTxtVSee, genresListTitleTxtVEdit;
    public static TextView genresListTxtVEdit;
    private ImageView readImgVEdit, notReadImgVEdit, readImgVSee, notReadImgVSee;
    private ImageView favoriteImgVEdit, notFavoriteImgVEdit, favoriteImgVSee, notFavoriteImgVSee;
    private ImageView emptyStar1ImgVEdit, emptyStar2ImgVEdit, emptyStar3ImgVEdit, emptyStar4ImgVEdit, emptyStar5ImgVEdit;
    private ImageView fullStar1ImgVEdit, fullStar2ImgVEdit, fullStar3ImgVEdit, fullStar4ImgVEdit, fullStar5ImgVEdit;
    private ImageView emptyStar1ImgVSee, emptyStar2ImgVSee, emptyStar3ImgVSee, emptyStar4ImgVSee, emptyStar5ImgVSee;
    private ImageView fullStar1ImgVSee, fullStar2ImgVSee, fullStar3ImgVSee, fullStar4ImgVSee, fullStar5ImgVSee;
    private String indexInSharedPrefs, title, volume, serie, author, addDate, releaseDate, description, summary, mark = "0";
    private String[] tags;
    private Boolean isRead, isFavorite;
    public static int indexInSharedPrefBooksIndex = 0, indexInSharedPrefBooksTitle = 1, indexInSharedPrefBooksVolume = 2;
    public static int indexInSharedPrefBooksSerie = 3, indexInSharedPrefBooksAuthor = 4, indexInSharedPrefBooksTag1 = 5;
    public static int indexInSharedPrefBooksTag2 = 6, indexInSharedPrefBooksTag3 = 7, indexInSharedPrefBooksReadStatus = 8;
    public static int indexInSharedPrefBooksDescription = 9, indexInSharedPrefBooksAddDate = 10;
    public static int indexInSharedPrefBooksReleaseDate = 11, indexInSharedPrefBooksIsFavorite = 12;
    public static int indexInSharedPrefBooksSummary = 13, indexInSharedPrefBooksMark = 14;
    public static int nbFieldsInSharedPrefBooks = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book_all_info);
        colorSystemBarTop(getWindow(), getResources(), this); // Set the color of the system bar at the top

        initGlobalLyts();
        initEditElements();
        initSeeElements();

        Bundle bundle = getIntent().getExtras();
        mode = bundle.getString("mode");
        previousActivity = bundle.getString("prevActivity");

        // Display return arrow (with linked intent)
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent returnIntent;
        if (previousActivity.equals("scan"))
            returnIntent = new Intent(OneBookAllInfoActivity.this, ScanQRCodeActivity.class);
        else if (previousActivity.equals("favoriteList"))
            returnIntent = new Intent(OneBookAllInfoActivity.this, FavoritesShowcaseActivity.class);
        else
            returnIntent = new Intent(OneBookAllInfoActivity.this, ListBooksActivity.class);

        if (mode.equals("create")) {
            editLayout.setVisibility(View.VISIBLE);
            saveBookButton.setVisibility(View.VISIBLE);
            saveEditBookButton.setVisibility(View.GONE);
            seeLayout.setVisibility(View.GONE);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(System.currentTimeMillis());
            addDate = formatter.format(date); // Set the add date
            addDateTxtVEdit.setText(addDate);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVEdit, new AppBarFragment(returnIntent)).commit();
        }
        else if (mode.equals("see")) {
            editLayout.setVisibility(View.GONE);
            seeLayout.setVisibility(View.VISIBLE);

            retrieveBookInfosFromBundle(bundle);
            String[] strings = {
                    indexInSharedPrefs, title, volume, serie, author, addDate, releaseDate, description, summary, mark, tags[0], tags[1], tags[2]
            };
            Boolean[] booleans = { isRead, isFavorite };
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVSee, new AppBarFragment(returnIntent, previousActivity, strings, booleans)).commit();
            setBookInfosConnectionSee(bundle);
        }
        else if (mode.equals("edit")) {
            saveEditBookButton.setVisibility(View.VISIBLE);
            saveBookButton.setVisibility(View.GONE);
            fragmentManager.beginTransaction().add(R.id.topBarOneBookInfoFragContainerVEdit, new AppBarFragment(returnIntent)).commit();
            setBookInfosEdit(bundle);
        }

        // Save a book button click listener
        saveBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousActivity.equals("verticalList") || previousActivity.equals("favoriteList")) {
                    updateBookInfosCreateAndEditModes(); // Retrieve and update book infos

                    int indexBook = 0, maxIndexBooks = 0;
                    String bookData;
                    Map<String, ?> allEntries = sharedPrefBooks.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        bookData = entry.getValue().toString();
                        bookData = bookData.substring(1);
                        bookData = bookData.substring(0, bookData.length() - 1);

                        List<String> bookDataList = new ArrayList<String>(Arrays.asList(bookData.split(", ")));
                        SortStringListByFirstChar(bookDataList);

                        String currentData = bookDataList.get(0).substring(2);
                        int currentDataInt = Integer.parseInt(currentData);
                        if (currentDataInt > maxIndexBooks)
                            maxIndexBooks = currentDataInt;
                    }
                    indexBook = maxIndexBooks;
                    indexBook++;
                    indexInSharedPrefs = String.valueOf(indexBook);

                    Set<String> set = createAndFillSet(); // Create Set
                    SharedPreferences.Editor editor = sharedPrefBooks.edit();
                    editor.putStringSet(String.valueOf(indexBook), set); // Add current set to SharedPreferences
                    editor.commit();

                    startActivity(new Intent(OneBookAllInfoActivity.this, ListBooksActivity.class));
                }
            }
        });

        // Save modifications on a book button click listener
        saveEditBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBookInfosCreateAndEditModes();

                Set<String> set = createAndFillSet(); // Create Set
                // Edit SharedPrefs
                SharedPreferences.Editor editor = sharedPrefBooks.edit();
                editor.remove(indexInSharedPrefs).commit();
                editor.putStringSet(indexInSharedPrefs, set).commit();

                // Replace current activity by the same but in see mode
                Intent intent = new Intent(OneBookAllInfoActivity.this, OneBookAllInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "see");
                bundle.putString("prevActivity", previousActivity);
                bundle.putString("indexInSharedPrefs", indexInSharedPrefs);
                bundle.putString("title", title);
                bundle.putString("volume", volume);
                bundle.putString("serie", serie);
                bundle.putString("author", author);
                bundle.putString("addDate", addDate);
                bundle.putString("releaseDate", releaseDate);
                bundle.putString("description", description);
                bundle.putString("summary", summary);
                bundle.putString("mark", mark);
                bundle.putString("tag1", tags[0]);
                bundle.putString("tag2", tags[1]);
                bundle.putString("tag3", tags[2]);
                bundle.putBoolean("readStatus", isRead);
                bundle.putBoolean("isFavorite", isFavorite);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Genres selector click listener
        genresListTitleTxtVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update listBookGenresSelected from the selected genres of the book
                updateBookInfosCreateAndEditModes();

                // Update
                updateListBookGenresSelectedFromTags();

                // Display genres selector fragment
                getSupportFragmentManager().beginTransaction().add(R.id.genresSelectorFragContainerVEdit, new GenresSelectorFragment(globalLytEdit, oneBookAllInfosScrollV, "nonFilter")).commit();
                enableDisableView(globalLytEdit, false);

                // Scroll to the top
                oneBookAllInfosScrollV.post(new Runnable() {
                    public void run() {
                        oneBookAllInfosScrollV.fullScroll(oneBookAllInfosScrollV.FOCUS_UP);
                    }
                });

                // Disable scroll
                oneBookAllInfosScrollV.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        });

        // Read status icon click listener (see)
        readImgVSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    readImgVSee.setVisibility(View.GONE);
                    notReadImgVSee.setVisibility(View.VISIBLE);
                    isRead = false;
                }
            }
        });

        notReadImgVSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    readImgVSee.setVisibility(View.VISIBLE);
                    notReadImgVSee.setVisibility(View.GONE);
                    isRead = true;
                }
            }
        });

        // Read status icon click listener (edit)
        readImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    readImgVEdit.setVisibility(View.GONE);
                    notReadImgVEdit.setVisibility(View.VISIBLE);
                    isRead = false;
                }
            }
        });

        notReadImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    readImgVEdit.setVisibility(View.VISIBLE);
                    notReadImgVEdit.setVisibility(View.GONE);
                    isRead = true;
                }
            }
        });

        // Favorite status icon click listener (see)
        favoriteImgVSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    favoriteImgVSee.setVisibility(View.GONE);
                    notFavoriteImgVSee.setVisibility(View.VISIBLE);
                    isFavorite = false;
                }
            }
        });

        notFavoriteImgVSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    favoriteImgVSee.setVisibility(View.VISIBLE);
                    notFavoriteImgVSee.setVisibility(View.GONE);
                    isFavorite = true;
                }
            }
        });

        // Favorite status icon click listener (edit)
        favoriteImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    favoriteImgVEdit.setVisibility(View.GONE);
                    notFavoriteImgVEdit.setVisibility(View.VISIBLE);
                    isFavorite = false;
                }
            }
        });

        notFavoriteImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    favoriteImgVEdit.setVisibility(View.VISIBLE);
                    notFavoriteImgVEdit.setVisibility(View.GONE);
                    isFavorite = true;
                }
            }
        });

        // Mark empty edit mode
        emptyStar1ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setOneStarMarkEdit();
                }
            }
        });

        emptyStar2ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setTwoStarsMarkEdit();
                }
            }
        });

        emptyStar3ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setThreeStarsMarkEdit();
                }
            }
        });

        emptyStar4ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setFourStarsMarkEdit();
                }
            }
        });

        emptyStar5ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setFiveStarsMarkEdit();
                }
            }
        });

        // Mark full edit mode
        fullStar1ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setOneStarMarkEdit();
                }
            }
        });

        fullStar2ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setTwoStarsMarkEdit();
                }
            }
        });

        fullStar3ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setThreeStarsMarkEdit();
                }
            }
        });

        fullStar4ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setFourStarsMarkEdit();
                }
            }
        });

        fullStar5ImgVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.equals("see")) {
                    setFiveStarsMarkEdit();
                }
            }
        });

    }

    private void initGlobalLyts() {
        oneBookAllInfosScrollV = findViewById(R.id.oneBookAllInfosScrollV);
        editLayout = findViewById(R.id.oneBookAllLytEdit);
        seeLayout = findViewById(R.id.oneBookAllLytSee);
        globalLytEdit = findViewById(R.id.oneBookInfosGlobalLytEdit);
        saveBookButton = findViewById(R.id.saveOneBookBtnEdit);
        saveEditBookButton = findViewById(R.id.saveEditOneBookBtnEdit);
        tags = new String[3];
        isFavorite = false;
        isRead = false;
    }

    private void initEditElements() {
        titleTxtInputLytEdit = findViewById(R.id.titleOneBookInfoTxtInputLytEdit);
        volumeTxtInputLytEdit = findViewById(R.id.volumeOneBookInfoTxtInputLytEdit);
        serieTxtInputLytEdit = findViewById(R.id.serieOneBookInfoTxtInputLytEdit);
        authorTxtInputLytEdit = findViewById(R.id.authorOneBookInfoTxtInputLytEdit);
        addDateTxtVEdit = findViewById(R.id.addDateOneBookInfoTxtInputLytEdit);
        releaseDateTxtInputLytEdit = findViewById(R.id.releaseDateOneBookInfoTxtInputLytEdit);
        descriptionTxtInputLytEdit = findViewById(R.id.descriptionOneBookInfoTxtInputLytEdit);
        summaryTxtInputLytEdit = findViewById(R.id.summaryOneBookInfoTxtInputLytEdit);
        genresListTitleTxtVEdit = findViewById(R.id.genreOneBookTxtVEdit);
        genresListTxtVEdit = findViewById(R.id.genresListOneBookTxtVEdit);
        readImgVEdit = findViewById(R.id.fullCandleImgVEdit);
        notReadImgVEdit = findViewById(R.id.emptyCandleImgVEdit);
        favoriteImgVEdit = findViewById(R.id.favoriteFullImgVEdit);
        notFavoriteImgVEdit = findViewById(R.id.favoriteEmptyImgVEdit);
        emptyStar1ImgVEdit = findViewById(R.id.emptyStar1ImgVEdit);
        emptyStar2ImgVEdit = findViewById(R.id.emptyStar2ImgVEdit);
        emptyStar3ImgVEdit = findViewById(R.id.emptyStar3ImgVEdit);
        emptyStar4ImgVEdit = findViewById(R.id.emptyStar4ImgVEdit);
        emptyStar5ImgVEdit = findViewById(R.id.emptyStar5ImgVEdit);
        fullStar1ImgVEdit = findViewById(R.id.fullStar1ImgVEdit);
        fullStar2ImgVEdit = findViewById(R.id.fullStar2ImgVEdit);
        fullStar3ImgVEdit = findViewById(R.id.fullStar3ImgVEdit);
        fullStar4ImgVEdit = findViewById(R.id.fullStar4ImgVEdit);
        fullStar5ImgVEdit = findViewById(R.id.fullStar5ImgVEdit);
    }

    private void initSeeElements() {
        titleTxtInputLytSee = findViewById(R.id.titleOneBookInfoTxtInputLytSee);
        volumeTxtInputLytSee = findViewById(R.id.volumeOneBookInfoTxtInputLytSee);
        serieTxtInputLytSee = findViewById(R.id.serieOneBookInfoTxtInputLytSee);
        authorTxtInputLytSee = findViewById(R.id.authorOneBookInfoTxtInputLytSee);
        addDateTxtVSee = findViewById(R.id.addDateOneBookInfoTxtInputLytSee);
        releaseDateTxtInputLytSee = findViewById(R.id.releaseDateOneBookInfoTxtInputLytSee);
        descriptionTxtInputLytSee = findViewById(R.id.descriptionOneBookInfoTxtInputLytSee);
        summaryTxtInputLytSee = findViewById(R.id.summaryOneBookInfoTxtInputLytSee);
        genresListTxtVSee = findViewById(R.id.genresListOneBookTxtVSee);
        readImgVSee = findViewById(R.id.fullCandleImgVSee);
        notReadImgVSee = findViewById(R.id.emptyCandleImgVSee);
        favoriteImgVSee = findViewById(R.id.favoriteFullImgVSee);
        notFavoriteImgVSee = findViewById(R.id.favoriteEmptyImgVSee);
        emptyStar1ImgVSee = findViewById(R.id.emptyStar1ImgVSee);
        emptyStar2ImgVSee = findViewById(R.id.emptyStar2ImgVSee);
        emptyStar3ImgVSee = findViewById(R.id.emptyStar3ImgVSee);
        emptyStar4ImgVSee = findViewById(R.id.emptyStar4ImgVSee);
        emptyStar5ImgVSee = findViewById(R.id.emptyStar5ImgVSee);
        fullStar1ImgVSee = findViewById(R.id.fullStar1ImgVSee);
        fullStar2ImgVSee = findViewById(R.id.fullStar2ImgVSee);
        fullStar3ImgVSee = findViewById(R.id.fullStar3ImgVSee);
        fullStar4ImgVSee = findViewById(R.id.fullStar4ImgVSee);
        fullStar5ImgVSee = findViewById(R.id.fullStar5ImgVSee);
    }

    private void setOneStarMarkEdit() {
        emptyStar1ImgVEdit.setVisibility(View.GONE);
        fullStar1ImgVEdit.setVisibility(View.VISIBLE);
        mark = "1";
        emptyStar2ImgVEdit.setVisibility(View.VISIBLE);
        fullStar2ImgVEdit.setVisibility(View.GONE);
        emptyStar3ImgVEdit.setVisibility(View.VISIBLE);
        fullStar3ImgVEdit.setVisibility(View.GONE);
        emptyStar4ImgVEdit.setVisibility(View.VISIBLE);
        fullStar4ImgVEdit.setVisibility(View.GONE);
        emptyStar5ImgVEdit.setVisibility(View.VISIBLE);
        fullStar5ImgVEdit.setVisibility(View.GONE);
    }

    private void setTwoStarsMarkEdit() {
        emptyStar1ImgVEdit.setVisibility(View.GONE);
        fullStar1ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar2ImgVEdit.setVisibility(View.GONE);
        fullStar2ImgVEdit.setVisibility(View.VISIBLE);
        mark = "2";
        emptyStar3ImgVEdit.setVisibility(View.VISIBLE);
        fullStar3ImgVEdit.setVisibility(View.GONE);
        emptyStar4ImgVEdit.setVisibility(View.VISIBLE);
        fullStar4ImgVEdit.setVisibility(View.GONE);
        emptyStar5ImgVEdit.setVisibility(View.VISIBLE);
        fullStar5ImgVEdit.setVisibility(View.GONE);
    }

    private void setThreeStarsMarkEdit() {
        emptyStar1ImgVEdit.setVisibility(View.GONE);
        fullStar1ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar2ImgVEdit.setVisibility(View.GONE);
        fullStar2ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar3ImgVEdit.setVisibility(View.GONE);
        fullStar3ImgVEdit.setVisibility(View.VISIBLE);
        mark = "3";
        emptyStar4ImgVEdit.setVisibility(View.VISIBLE);
        fullStar4ImgVEdit.setVisibility(View.GONE);
        emptyStar5ImgVEdit.setVisibility(View.VISIBLE);
        fullStar5ImgVEdit.setVisibility(View.GONE);
    }

    private void setFourStarsMarkEdit() {
        emptyStar1ImgVEdit.setVisibility(View.GONE);
        fullStar1ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar2ImgVEdit.setVisibility(View.GONE);
        fullStar2ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar3ImgVEdit.setVisibility(View.GONE);
        fullStar3ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar4ImgVEdit.setVisibility(View.GONE);
        fullStar4ImgVEdit.setVisibility(View.VISIBLE);
        mark = "4";
        emptyStar5ImgVEdit.setVisibility(View.VISIBLE);
        fullStar5ImgVEdit.setVisibility(View.GONE);
    }

    private void setFiveStarsMarkEdit() {
        emptyStar1ImgVEdit.setVisibility(View.GONE);
        fullStar1ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar2ImgVEdit.setVisibility(View.GONE);
        fullStar2ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar3ImgVEdit.setVisibility(View.GONE);
        fullStar3ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar4ImgVEdit.setVisibility(View.GONE);
        fullStar4ImgVEdit.setVisibility(View.VISIBLE);
        emptyStar5ImgVEdit.setVisibility(View.GONE);
        fullStar5ImgVEdit.setVisibility(View.VISIBLE);
        mark = "5";
    }

    private void setStarsMarkSeeFromEdit() {
        emptyStar1ImgVSee.setVisibility(emptyStar1ImgVEdit.getVisibility());
        fullStar1ImgVSee.setVisibility(fullStar1ImgVEdit.getVisibility());
        emptyStar2ImgVSee.setVisibility(emptyStar2ImgVEdit.getVisibility());
        fullStar2ImgVSee.setVisibility(fullStar2ImgVEdit.getVisibility());
        emptyStar3ImgVSee.setVisibility(emptyStar3ImgVEdit.getVisibility());
        fullStar3ImgVSee.setVisibility(fullStar3ImgVEdit.getVisibility());
        emptyStar4ImgVSee.setVisibility(emptyStar4ImgVEdit.getVisibility());
        fullStar4ImgVSee.setVisibility(fullStar4ImgVEdit.getVisibility());
        emptyStar5ImgVSee.setVisibility(emptyStar5ImgVEdit.getVisibility());
        fullStar5ImgVSee.setVisibility(fullStar5ImgVEdit.getVisibility());
    }

    private Set<String> createAndFillSet() {
        Set<String> set = new HashSet<>();
        set.add("a_" + indexInSharedPrefs); // Add title
        set.add("b_" + title); // Add title
        set.add("c_" + volume); // Add volume
        set.add("d_" + serie); // Add serie
        set.add("e_" + author); // Add author
        set.add("f_" + tags[0]); // Add first tag
        set.add("g_" + tags[1]); // Add second tag
        set.add("h_" + tags[2]); // Add third tag
        set.add("i_" + isRead.toString()); // Add isRead
        set.add("j_" + description); // Add description
        set.add("k_" + addDate); // Add releaseDate
        set.add("l_" + releaseDate); // Add releaseDate
        set.add("m_" + isFavorite.toString()); // Add isFavorite
        set.add("n_" + summary); // Add summary
        set.add("o_" + mark); // Add mark
        return set;
    }

    private void updateBookInfosCreateAndEditModes() {
        title = titleTxtInputLytEdit.getEditText().getText().toString(); // Set the title
        volume = volumeTxtInputLytEdit.getEditText().getText().toString(); // Set the volume
        serie = serieTxtInputLytEdit.getEditText().getText().toString(); // Set the serie
        author = authorTxtInputLytEdit.getEditText().getText().toString(); // Set the author
        releaseDate = releaseDateTxtInputLytEdit.getEditText().getText().toString(); // Set the release date
        description = descriptionTxtInputLytEdit.getEditText().getText().toString(); // Set the description
        summary = summaryTxtInputLytEdit.getEditText().getText().toString(); // Set the summary

        String genres = genresListTxtVEdit.getText().toString();
        List<String> genresList = new ArrayList<>(Arrays.asList(genres.split("\n")));
        tags[0] = "";
        tags[1] = "";
        tags[2] = "";
        if (genresList.size() >= 1) {
            tags[0] = genresList.get(0);
            if (genresList.size() >= 2) {
                tags[1] = genresList.get(1);
                if (genresList.size() >= 3)
                    tags[2] = genresList.get(2);
            }
        }
    }

    private void retrieveBookInfosFromBundle(Bundle bundle) {
        indexInSharedPrefs = bundle.getString("indexInSharedPrefs");
        title = bundle.getString("title");
        volume = bundle.getString("volume");
        serie = bundle.getString("serie");
        author = bundle.getString("author");
        addDate = bundle.getString("addDate");
        releaseDate = bundle.getString("releaseDate");
        description = bundle.getString("description");
        summary = bundle.getString("summary");
        mark = bundle.getString("mark");
        tags[0] = bundle.getString("tag1");
        tags[1] = bundle.getString("tag2");
        tags[2] = bundle.getString("tag3");
        isRead = (bundle.getBoolean("readStatus"));
        isFavorite = (bundle.getBoolean("isFavorite"));

        if (mark.equals("1"))
            setOneStarMarkEdit();
        else if (mark.equals("2"))
            setTwoStarsMarkEdit();
        else if (mark.equals("3"))
            setThreeStarsMarkEdit();
        else if (mark.equals("4"))
            setFourStarsMarkEdit();
        else if (mark.equals("5"))
            setFiveStarsMarkEdit();
        setStarsMarkSeeFromEdit();
    }

    // Set book infos (edit mode)
    private void setBookInfosEdit(Bundle bundle) {
        retrieveBookInfosFromBundle(bundle);

        titleTxtInputLytEdit.getEditText().setText(title); // Set the title
        volumeTxtInputLytEdit.getEditText().setText(volume); // Set the volume
        serieTxtInputLytEdit.getEditText().setText(serie); // Set the serie
        authorTxtInputLytEdit.getEditText().setText(author); // Set the author
        releaseDateTxtInputLytEdit.getEditText().setText(releaseDate); // Set the release date
        descriptionTxtInputLytEdit.getEditText().setText(description); // Set the description
        summaryTxtInputLytEdit.getEditText().setText(summary); // Set the summary
        // Set the tags
        Boolean isNullLengthTag1 = (tags[0].length() == 0);
        Boolean isNullLengthTag2 = (tags[1].length() == 0);
        Boolean isNullLengthTag3 = (tags[2].length() == 0);
        if (isNullLengthTag1 && isNullLengthTag2 && isNullLengthTag3)
            genresListTxtVEdit.setText("");
        else if (isNullLengthTag2 && isNullLengthTag3)
            genresListTxtVEdit.setText(tags[0]);
        else if (isNullLengthTag3)
            genresListTxtVEdit.setText(tags[0] + "\n" + tags[1]);
        else
            genresListTxtVEdit.setText(tags[0] + "\n" + tags[1] + "\n" + tags[2]);

        readImgVSee = findViewById(R.id.fullCandleImgVEdit);
        notReadImgVSee = findViewById(R.id.emptyCandleImgVEdit);
        favoriteImgVSee = findViewById(R.id.favoriteFullImgVEdit);
        notFavoriteImgVSee = findViewById(R.id.favoriteEmptyImgVEdit);

        if (isRead) {
            readImgVEdit.setVisibility(View.VISIBLE);
            notReadImgVEdit.setVisibility(View.GONE);
        }
        else {
            readImgVEdit.setVisibility(View.GONE);
            notReadImgVEdit.setVisibility(View.VISIBLE);
        }
        if (isFavorite) {
            favoriteImgVEdit.setVisibility(View.VISIBLE);
            notFavoriteImgVEdit.setVisibility(View.GONE);
        }
        else {
            favoriteImgVEdit.setVisibility(View.GONE);
            notFavoriteImgVEdit.setVisibility(View.VISIBLE);
        }
    }

    // Set book infos (see mode)
    private void setBookInfosConnectionSee(Bundle bundle) {
        retrieveBookInfosFromBundle(bundle);

        titleTxtInputLytSee.getEditText().setText(title); // Set the title
        volumeTxtInputLytSee.getEditText().setText(volume); // Set the volume
        serieTxtInputLytSee.getEditText().setText(serie); // Set the serie
        authorTxtInputLytSee.getEditText().setText(author); // Set the author
        addDateTxtVSee.setText(addDate); // Set the add date
        releaseDateTxtInputLytSee.getEditText().setText(releaseDate); // Set the release date
        descriptionTxtInputLytSee.getEditText().setText(description); // Set the description
        summaryTxtInputLytSee.getEditText().setText(summary); // Set the summary
        // Set the tags
        Boolean isNullLengthTag1 = (tags[0].length() == 0);
        Boolean isNullLengthTag2 = (tags[1].length() == 0);
        Boolean isNullLengthTag3 = (tags[2].length() == 0);
        if (isNullLengthTag1 && isNullLengthTag2 && isNullLengthTag3)
            genresListTxtVSee.setText("");
        else if (isNullLengthTag2 && isNullLengthTag3)
            genresListTxtVSee.setText(tags[0]);
        else if (isNullLengthTag3)
            genresListTxtVSee.setText(tags[0] + "\n" + tags[1]);
        else
            genresListTxtVSee.setText(tags[0] + "\n" + tags[1] + "\n" + tags[2]);

        readImgVSee = findViewById(R.id.fullCandleImgVSee);
        notReadImgVSee = findViewById(R.id.emptyCandleImgVSee);
        favoriteImgVSee = findViewById(R.id.favoriteFullImgVSee);
        notFavoriteImgVSee = findViewById(R.id.favoriteEmptyImgVSee);

        if (isRead) {
            readImgVSee.setVisibility(View.VISIBLE);
            notReadImgVSee.setVisibility(View.GONE);
        }
        else {
            readImgVSee.setVisibility(View.GONE);
            notReadImgVSee.setVisibility(View.VISIBLE);
        }
        if (isFavorite) {
            favoriteImgVSee.setVisibility(View.VISIBLE);
            notFavoriteImgVSee.setVisibility(View.GONE);
        }
        else {
            favoriteImgVSee.setVisibility(View.GONE);
            notFavoriteImgVSee.setVisibility(View.VISIBLE);
        }
    }

    private void updateListBookGenresSelectedFromTags() {
        String[] genresArray = getResources().getStringArray(R.array.all_book_genres);
        initListBookGenresAndListBookGenresSelected(genresArray);
        String currentGenre;
        Boolean firstTag, secondTag, thirdTag;
        for (int i = 0; i < listBookGenres.size(); i++) {
            currentGenre = listBookGenres.get(i);
            firstTag = currentGenre.equals(tags[0]);
            secondTag = currentGenre.equals(tags[1]);
            thirdTag = currentGenre.equals(tags[2]);
            if (firstTag || secondTag || thirdTag)
                listBookGenresSelected.set(i, true);
            else
                listBookGenresSelected.set(i, false);
        }
    }
}