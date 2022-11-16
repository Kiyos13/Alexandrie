package com.example.alexandrie;

import static com.example.alexandrie.ListBooksActivity.listBooksInSharedPrefs;
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

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private int images[];
    private Context context;
    private ViewGroup recyclerviewVG;
    private RecyclerView recyclerViewBooks;
    private Boolean isLongClicked = false, allBooksSelected = false, allBooksUnselected = false;
    private View selectAllItemsCheckboxView;
    private CheckBox selectAllItemsCheckbox;
    private TextView swipeTxtTV, nbSelectedBooksTxtTV;
    private ObservableInteger nbSelectedBooks = new ObservableInteger();
    public static ArrayList<Integer> indexListSelectedItemBooks;

    public BooksAdapter(Context ctx, int imgs[], RecyclerView recyclerView,
        View checkboxView, TextView swipeTextTxtView, TextView nbSelectedBooksTextTxtV) {
        context = ctx;
        images = imgs;
        recyclerViewBooks = recyclerView;
        selectAllItemsCheckboxView = checkboxView;
        selectAllItemsCheckbox = selectAllItemsCheckboxView.findViewById(R.id.checkboxSelectAllBooks);
        // Checkbox to select all book items change listener
        selectAllItemsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectAllItemsCheckbox.isChecked()) {
                    allBooksSelected = true;
                    allBooksUnselected = false;
                    selectAllCheckBoxes(); // Select all the items
                }
                else {
                    allBooksSelected = false;
                    allBooksUnselected = true;
                    unselectAllCheckBoxes(); // Unselect all the items
                }
            }
        });
        swipeTxtTV = swipeTextTxtView;
        nbSelectedBooksTxtTV = nbSelectedBooksTextTxtV;
        nbSelectedBooks.set(0);
        // Change listener on the number of selected book items to update the text with the number of selected items
        nbSelectedBooks.setOnIntegerChangeListener(new OnIntegerChangeListener() {
            @Override
            public void onIntegerChanged(int newValue) {
                int nbSelectedBooksForTxt = nbSelectedBooks.get(); // Retrieve the number of selected books
                if (nbSelectedBooksForTxt == 0)
                    nbSelectedBooksTxtTV.setText("\nAucun livre n'est sélectionné\n");
                else if (nbSelectedBooksForTxt == 1)
                    nbSelectedBooksTxtTV.setText("\n1 livre est sélectionné\n");
                else
                    nbSelectedBooksTxtTV.setText("\n" + String.valueOf(nbSelectedBooksForTxt) + " sont livres sélectionnés\n");
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_one_book_in_list_books, parent, false);
        recyclerviewVG = parent; // ViewGroup global variable
        indexListSelectedItemBooks = new ArrayList<Integer>();
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sharedPrefIndexTxt = listBooksInSharedPrefs.get(indexInSharedPrefBooksIndex).get(position);
        holder.titleTxt.setText(listBooksInSharedPrefs.get(indexInSharedPrefBooksTitle).get(position));
        holder.volumeTxt.setText("Tome " + listBooksInSharedPrefs.get(indexInSharedPrefBooksVolume).get(position));
        holder.serieTxt = listBooksInSharedPrefs.get(indexInSharedPrefBooksSerie).get(position);
        holder.authorTxt.setText(listBooksInSharedPrefs.get(indexInSharedPrefBooksAuthor).get(position));
        holder.isRead = (listBooksInSharedPrefs.get(indexInSharedPrefBooksReadStatus).get(position).equals("true")) ? true : false;
        holder.descriptionTxt = listBooksInSharedPrefs.get(indexInSharedPrefBooksDescription).get(position);
        holder.addDateTxt = listBooksInSharedPrefs.get(indexInSharedPrefBooksAddDate).get(position);
        holder.releaseDateTxt = listBooksInSharedPrefs.get(indexInSharedPrefBooksReleaseDate).get(position);
        holder.isFavorite = (listBooksInSharedPrefs.get(indexInSharedPrefBooksIsFavorite).get(position).equals("true")) ? true : false;
        holder.summaryTxt = (listBooksInSharedPrefs.get(indexInSharedPrefBooksSummary).get(position));
        holder.markTxt = (listBooksInSharedPrefs.get(indexInSharedPrefBooksMark).get(position));
        onBindViewHolderTags(holder, position); // Set tags
        onBindViewHolderReadStatus(holder, position); // Set read status and corresponding icon
        holder.coverImgV.setImageResource(images[0]);

        // Book item layout long click listener
        holder.oneBookInListLyt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("\t\tLong click !");
                if (!isLongClicked) {
                    isLongClicked = true; // Boolean to check if user has long clicked on item set to true

                    // Manage fragments
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    // Replace top bar (AppBarFragment) to add return arrow in order to leave multi-selection mode
                    fragmentManager.beginTransaction().replace(R.id.topBarLBFragContainerV, new AppBarFragment(new Intent(context, context.getClass()))).commit();
                    // Replace AddFragment by DeleteFragment
                    fragmentManager.beginTransaction().replace(R.id.addDelFragContainerV, new DeleteFragment()).commit();

                    // Replace swipe text by number off selected items text
                    swipeTxtTV.setVisibility(View.GONE);
                    nbSelectedBooksTxtTV.setVisibility(View.VISIBLE);

                    // Show checkbox to select all book items
                    selectAllItemsCheckboxView.setVisibility(View.VISIBLE);

                    // Display and check checkbox user long click
                    // Change background of item under long click
                    holder.selectedBookCheckbox.setVisibility(View.VISIBLE);
                    holder.selectedBookCheckbox.setChecked(true);
                    holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
                    nbSelectedBooks.set(nbSelectedBooks.get() + 1);

                    // Add current index to the list of selected book items
                    indexListSelectedItemBooks.add(Integer.parseInt(holder.sharedPrefIndexTxt));

                    displayAllCheckBoxes(); // Display all the checkboxes of all book items in recyclerView
                    return true;
                }
                return false;
            }
        });

        // RecyclerView scroll listener
        holder.oneBookInListLyt.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (isLongClicked)
                    displayAllCheckBoxes(); // Display all the checkboxes of all book items in recyclerView
                if (allBooksSelected)
                    selectAllCheckBoxes(); // Select all the checkboxes of all book items in recyclerView
                if (allBooksUnselected)
                    unselectAllCheckBoxes(); // Unselect all the checkboxes of all book items in recyclerView
            }
        });

        // Book item layout click listener
        holder.oneBookInListLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                if (isLongClicked)
                    selectAndUnselectBooksInList(holder); // Select or Unselect item book
                else {
                    Intent intent = new Intent(context, OneBookAllInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mode", "see");
                    bundle.putString("prevActivity", "verticalList");
                    bundle.putString("indexInSharedPrefs", holder.sharedPrefIndexTxt);
                    bundle.putString("title", holder.titleTxt.getText().toString());
                    bundle.putString("volume", holder.volumeTxt.getText().toString());
                    bundle.putString("serie", holder.serieTxt);
                    bundle.putString("author", holder.authorTxt.getText().toString());
                    bundle.putString("releaseDate", holder.releaseDateTxt);
                    bundle.putString("addDate", holder.addDateTxt);
                    bundle.putBoolean("readStatus", holder.isRead);
                    bundle.putString("description", holder.descriptionTxt);
                    bundle.putString("summary", holder.summaryTxt);
                    bundle.putString("mark", holder.markTxt);
                    bundle.putBoolean("isFavorite", holder.isFavorite);
                    String tag1 = holder.tag1Txt.getText().toString();
                    String tag2 = holder.tag2Txt.getText().toString();
                    String tag3 = holder.tag3Txt.getText().toString();
                    if (tag1.length() != 0)
                        tag1 = tag1.substring(1);
                    if (tag2.length() != 0)
                        tag2 = tag2.substring(1);
                    if (tag3.length() != 0)
                        tag3 = tag3.substring(1);
                    bundle.putString("tag1", tag1);
                    bundle.putString("tag2", tag2);
                    bundle.putString("tag3", tag3);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });

        // Book item checkbox click listener
        holder.selectedBookCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                // Switch item checkbox checked status
                holder.selectedBookCheckbox.setChecked(!holder.selectedBookCheckbox.isChecked());
                selectAndUnselectBooksInList(holder); // Select or Unselect item book
            }
        });
    }

    // Set tags without space if one before or in the middle is empty (tag offset)
    private void onBindViewHolderTags(@NonNull BooksViewHolder holder, int position) {
        holder.tag1Txt.setText("");
        holder.tag2Txt.setText("");
        holder.tag3Txt.setText("");

        ArrayList<String> tags1 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag1);
        ArrayList<String> tags2 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag2);
        ArrayList<String> tags3 = listBooksInSharedPrefs.get(indexInSharedPrefBooksTag3);

        if (tags1.get(position).length() == 0) {
            if (tags2.get(position).length() == 0) {
                if (tags3.get(position).length() != 0)
                    holder.tag1Txt.setText("#" + tags3.get(position));
                else
                    holder.tag1Txt.setBackgroundResource(0);
                holder.tag2Txt.setBackgroundResource(0);
            }
            else {
                holder.tag1Txt.setText("#" + tags2.get(position));
                if (tags3.get(position).length() != 0)
                    holder.tag2Txt.setText("#" + tags3.get(position));
                else
                    holder.tag2Txt.setBackgroundResource(0);
            }
            holder.tag3Txt.setBackgroundResource(0);
        }
        else {
            holder.tag1Txt.setText("#" + tags1.get(position));
            if (tags2.get(position).length() == 0) {
                if (tags3.get(position).length() != 0)
                    holder.tag2Txt.setText("#" + tags3.get(position));
                else
                    holder.tag2Txt.setBackgroundResource(0);
                holder.tag3Txt.setBackgroundResource(0);
            }
            else {
                holder.tag2Txt.setText("#" + tags2.get(position));
                if (tags3.get(position).length() != 0)
                    holder.tag3Txt.setText("#" + tags3.get(position));
                else
                    holder.tag3Txt.setBackgroundResource(0);
            }
        }
    }

    // Update book item icon (read/unread)
    private void onBindViewHolderReadStatus(@NonNull BooksViewHolder holder, int position) {
        if (holder.isRead) { // If the book is read
            holder.unreadImgV.setVisibility(View.GONE); // Remove unread icon
            holder.readImgV.setVisibility(View.VISIBLE); // Display read icon
        }
        else {
            holder.unreadImgV.setVisibility(View.VISIBLE); // Display unread icon
            holder.readImgV.setVisibility(View.GONE); // Remove read icon
        }
    }

    // Display checkboxes of all book items
    private void displayAllCheckBoxes() {
        View child;
        for (int i = 0; i <= recyclerviewVG.getChildCount(); i++) {
            child = recyclerviewVG.getChildAt(i); // Retrieve child in groupView
            if (child != null)
                child.findViewById(R.id.checkReadNotRead).setVisibility(View.VISIBLE); // Set visible the child checkbox
        }
    }

    // Select checkboxes of all book items
    private void selectAllCheckBoxes() {
        View child;
        for (int i = 0; i <= recyclerviewVG.getChildCount(); i++) {
            child = recyclerviewVG.getChildAt(i); // Retrieve child in groupView
            // Set the number of selected book items to the maximum
            nbSelectedBooks.set(recyclerViewBooks.getAdapter().getItemCount());
            if (child != null) {
                // Check the child checkbox
                ((CheckBox) child.findViewById(R.id.checkReadNotRead)).setChecked(true);
                // Change the child background
                child.findViewById(R.id.oneBookInListLyt). setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
            }
        }
        // Add all indexes to the list of selected book items
        indexListSelectedItemBooks = new ArrayList<Integer>();
        BooksViewHolder holder;
        for (int i = 0; i < this.getItemCount(); i++) {
            holder = (BooksViewHolder) recyclerViewBooks.findViewHolderForAdapterPosition(i);
            indexListSelectedItemBooks.add(Integer.parseInt(holder.sharedPrefIndexTxt));
        }
    }

    // Remove checkboxes of all book items
    private void unselectAllCheckBoxes() {
        View child;
        for (int i = 0; i <= recyclerviewVG.getChildCount(); i++) {
            child = recyclerviewVG.getChildAt(i); // Retrieve child in groupView
            // Set the number of selected book items to 0
            nbSelectedBooks.set(0);
            if (child != null) {
                // Uncheck the child checkbox
                ((CheckBox) child.findViewById(R.id.checkReadNotRead)).setChecked(false);
                // Change the child background
                child.findViewById(R.id.oneBookInListLyt).setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_background_color));
            }
        }
        // Remove all indexes of the list of selected book items
        indexListSelectedItemBooks = new ArrayList<Integer>();
    }

    // Select or Unselect book item when a long click on an item hapened
    private void selectAndUnselectBooksInList(@NonNull BooksViewHolder holder) {
        if (isLongClicked) {
            if (holder.selectedBookCheckbox.isChecked()) {
                holder.selectedBookCheckbox.setChecked(false); // Uncheck the checkbox of the current book item
                // Change book item background
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_background_color));
                nbSelectedBooks.set(nbSelectedBooks.get() - 1); // Decrease the number of selected items by one

                // Remove current index from the list of selected book items
                indexListSelectedItemBooks.remove(Integer.parseInt(holder.sharedPrefIndexTxt));
            }
            else {
                holder.selectedBookCheckbox.setChecked(true); // Check the checkbox of the current book item
                // Change book item background
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
                nbSelectedBooks.set(nbSelectedBooks.get() + 1); // Increase the number of selected items by one

                // Add current index to the list of selected book items
                indexListSelectedItemBooks.add(Integer.parseInt(holder.sharedPrefIndexTxt));
            }
        }
    }

    @Override
    public int getItemCount() {
        return listBooksInSharedPrefs.get(indexInSharedPrefBooksIndex).size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {

        // Elements in each book item
        TextView titleTxt, volumeTxt, authorTxt, tag1Txt, tag2Txt, tag3Txt;
        ImageView coverImgV, unreadImgV, readImgV;
        View oneBookInListLyt;
        CheckBox selectedBookCheckbox;
        Boolean isRead, isFavorite;
        String serieTxt, releaseDateTxt, addDateTxt, descriptionTxt, sharedPrefIndexTxt, summaryTxt, markTxt;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxtView); // Title of the book item
            volumeTxt = itemView.findViewById(R.id.volumeTxtView); // Volume of the book item
            authorTxt = itemView.findViewById(R.id.authorTxtView); // Author of the book item
            tag1Txt = itemView.findViewById(R.id.tag1TxtView); // First tag of the book item
            tag2Txt = itemView.findViewById(R.id.tag2TxtView); // Second tag of the book item
            tag3Txt = itemView.findViewById(R.id.tag3TxtView); // Third tag of the book item
            coverImgV = itemView.findViewById(R.id.coverImgV); // Cover image of the book item
            unreadImgV = itemView.findViewById(R.id.unreadImgV); // Unread icon of the book item
            readImgV = itemView.findViewById(R.id.readImgV); // Read icon of the book item
            oneBookInListLyt = itemView.findViewById(R.id.oneBookInListLyt); // All layout of the book item
            selectedBookCheckbox = itemView.findViewById(R.id.checkReadNotRead); // Checkbox of the book item
        }
    }
}