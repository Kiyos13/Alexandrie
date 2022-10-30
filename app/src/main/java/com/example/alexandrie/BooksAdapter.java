package com.example.alexandrie;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private ArrayList<String> dataTitles, dataVolumes, dataAuthors, dataTags1, dataTags2, dataTags3, dataReadStatus;
    private int images[];
    private Context context;
    private ViewGroup recyclerviewVG;
    private RecyclerView recyclerViewBooks;
    private Boolean isLongClicked = false, allBooksSelected = false, allBooksUnselected = false;
    private View selectAllItemsCheckboxView;
    private CheckBox selectAllItemsCheckbox;
    private TextView swipeTxtTV, nbSelectedBooksTxtTV;
    private ObservableInteger nbSelectedBooks = new ObservableInteger();

    public BooksAdapter(Context ctx, ArrayList<String> strTitles, ArrayList<String> strVolumes, ArrayList<String> strAuthors,
                        ArrayList<String> strTags1, ArrayList<String> strTags2, ArrayList<String> strTags3,
                        ArrayList<String> strReadStatus, int imgs[], RecyclerView recyclerView,
                        View checkboxView, TextView swipeTextTxtView, TextView nbSelectedBooksTextTxtV) {
        context = ctx;
        dataTitles = strTitles;
        dataVolumes = strVolumes;
        dataAuthors = strAuthors;
        dataTags1 = strTags1;
        dataTags2 = strTags2;
        dataTags3 = strTags3;
        dataReadStatus = strReadStatus;
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
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.titleTxt.setText(dataTitles.get(position));
        holder.volumeTxt.setText("Tome " + dataVolumes.get(position));
        holder.authorTxt.setText(dataAuthors.get(position));
        onBindViewHolderTags(holder, position); // Set tags
        onBindViewHolderReadStatus(holder, position); // Set read status and corresponding icon
        holder.coverImgV.setImageResource(images[position]);

        // Book item layout long click listener
        holder.oneBookInListLyt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("\t\tLong click !");
                isLongClicked = true; // Boolean to check if user has long clicked on item set to true

                // Replace AddFragment by DeleteFragment
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
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

                displayAllCheckBoxes(); // Display all the checkboxes of all book items in recyclerView
                return true;
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
                selectAndUnselectBooksInList(holder); // Select or Unselect item book
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

        if (dataTags1.get(position).length() == 0) {
            if (dataTags2.get(position).length() == 0) {
                if (dataTags3.get(position).length() != 0)
                    holder.tag1Txt.setText("#" + dataTags3.get(position));
            }
            else {
                holder.tag1Txt.setText("#" + dataTags2.get(position));
                if (dataTags3.get(position).length() != 0)
                    holder.tag2Txt.setText("#" + dataTags3.get(position));
                else
                    holder.tag2Txt.setBackgroundResource(0);
                holder.tag3Txt.setBackgroundResource(0);
            }
        }
        else {
            holder.tag1Txt.setText("#" + dataTags1.get(position));
            if (dataTags2.get(position).length() == 0) {
                if (dataTags3.get(position).length() != 0)
                    holder.tag2Txt.setText("#" + dataTags3.get(position));
                else
                    holder.tag2Txt.setBackgroundResource(0);
                holder.tag3Txt.setBackgroundResource(0);
            }
            else {
                holder.tag2Txt.setText("#" + dataTags2.get(position));
                if (dataTags3.get(position).length() != 0)
                    holder.tag3Txt.setText("#" + dataTags3.get(position));
                else
                    holder.tag3Txt.setBackgroundResource(0);
            }
        }
    }

    // Update book item icon (read/unread)
    private void onBindViewHolderReadStatus(@NonNull BooksViewHolder holder, int position) {
        // Boolean readStatus = dataReadStatus[position].equals("true"); // Check if book item status is read
        Boolean readStatus = dataReadStatus.get(position).equals("true"); // Check if book item status is read
        if (readStatus) { // If the book is read
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
    }

    // Select or Unselect book item when a long click on an item hapened
    private void selectAndUnselectBooksInList(@NonNull BooksViewHolder holder) {
        if (isLongClicked) {
            if (holder.selectedBookCheckbox.isChecked()) {
                holder.selectedBookCheckbox.setChecked(false); // Uncheck the checkbox of the current book item
                // Change book item background
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_background_color));
                nbSelectedBooks.set(nbSelectedBooks.get() - 1); // Decrease the number of selected items by one
            }
            else {
                holder.selectedBookCheckbox.setChecked(true); // Check the checkbox of the current book item
                // Change book item background
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
                nbSelectedBooks.set(nbSelectedBooks.get() + 1); // Increase the number of selected items by one
            }
        }
    }

    @Override
    public int getItemCount() {
        // return dataTitles.length;
        return dataTitles.size();
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