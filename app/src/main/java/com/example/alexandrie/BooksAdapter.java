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
import java.util.Arrays;
import java.util.HashSet;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    String dataTitles[], dataVolumes[], dataAuthors[], dataTags1[], dataTags2[], dataTags3[], dataReadStatus[];
    int images[];
    Context context;
    private ViewGroup recyclerviewVG;
    private RecyclerView recyclerViewBooks;
    private Boolean isLongClicked = false, allBooksSelected = false, allBooksUnselected = false;
    private View selectAllItemsCheckboxView;
    private CheckBox selectAllItemsCheckbox;
    private TextView swipeTxtTV, nbSelectedBooksTxtTV;
    private ObservableInteger nbSelectedBooks = new ObservableInteger();

    public BooksAdapter(Context ctx, String strTitles[], String strVolumes[], String strAuthors[],
                        String strTags1[], String strTags2[], String strTags3[],
                        String strReadStatus[], int imgs[], RecyclerView recyclerView,
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
        selectAllItemsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectAllItemsCheckbox.isChecked()) {
                    allBooksSelected = true;
                    allBooksUnselected = false;
                    selectAllCheckBoxes();
                }
                else {
                    allBooksSelected = false;
                    allBooksUnselected = true;
                    unselectAllCheckBoxes();
                }
            }
        });
        swipeTxtTV = swipeTextTxtView;
        nbSelectedBooksTxtTV = nbSelectedBooksTextTxtV;
        nbSelectedBooks.set(0);
        nbSelectedBooks.setOnIntegerChangeListener(new OnIntegerChangeListener() {
            @Override
            public void onIntegerChanged(int newValue) {
                int nbSelectedBooksForTxt = nbSelectedBooks.get();
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
        recyclerviewVG = parent;
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.titleTxt.setText(dataTitles[position]);
        holder.volumeTxt.setText("Tome " + dataVolumes[position]);
        holder.authorTxt.setText(dataAuthors[position]);
        onBindViewHolderTags(holder, position);
        onBindViewHolderReadStatus(holder, position);
        holder.coverImgV.setImageResource(images[position]);

        holder.oneBookInListLyt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("\t\tLong click !");
                isLongClicked = true;

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.addDelFragContainerV, new DeleteFragment()).commit();

                swipeTxtTV.setVisibility(View.GONE);
                nbSelectedBooksTxtTV.setVisibility(View.VISIBLE);

                selectAllItemsCheckboxView.setVisibility(View.VISIBLE);

                holder.selectedBookCheckbox.setVisibility(View.VISIBLE);
                holder.selectedBookCheckbox.setChecked(true);
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
                nbSelectedBooks.set(nbSelectedBooks.get() + 1);

                displayAllCheckBoxes();
                return true;
            }
        });

        holder.oneBookInListLyt.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (isLongClicked)
                    displayAllCheckBoxes();
                if (allBooksSelected)
                    selectAllCheckBoxes();
                if (allBooksUnselected)
                    unselectAllCheckBoxes();
            }
        });

        holder.oneBookInListLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                selectAndUnselectBooksInList(holder);
            }
        });

        holder.selectedBookCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View InputFragmentView) {
                holder.selectedBookCheckbox.setChecked(!holder.selectedBookCheckbox.isChecked());
                selectAndUnselectBooksInList(holder);
            }
        });
    }

    private void onBindViewHolderTags(@NonNull BooksViewHolder holder, int position) {
        holder.tag1Txt.setText("");
        holder.tag2Txt.setText("");
        holder.tag3Txt.setText("");
        if (dataTags1[position].length() == 0) {
            if (dataTags2[position].length() == 0) {
                if (dataTags3[position].length() != 0)
                    holder.tag1Txt.setText("#" + dataTags3[position]);
            }
            else {
                holder.tag1Txt.setText("#" + dataTags2[position]);
                if (dataTags3[position].length() != 0)
                    holder.tag2Txt.setText("#" + dataTags3[position]);
                else
                    holder.tag2Txt.setBackgroundResource(0);
                holder.tag3Txt.setBackgroundResource(0);
            }
        }
        else {
            holder.tag1Txt.setText("#" + dataTags1[position]);
            if (dataTags2[position].length() == 0) {
                if (dataTags3[position].length() != 0)
                    holder.tag2Txt.setText("#" + dataTags3[position]);
                else
                    holder.tag2Txt.setBackgroundResource(0);
                holder.tag3Txt.setBackgroundResource(0);
            }
            else {
                holder.tag2Txt.setText("#" + dataTags2[position]);
                if (dataTags3[position].length() != 0)
                    holder.tag3Txt.setText("#" + dataTags3[position]);
                else
                    holder.tag3Txt.setBackgroundResource(0);
            }
        }
    }

    private void onBindViewHolderReadStatus(@NonNull BooksViewHolder holder, int position) {
        Boolean readStatus = dataReadStatus[position].equals("true");
        if (readStatus) {
            holder.unreadImgV.setVisibility(View.GONE);
            holder.readImgV.setVisibility(View.VISIBLE);
        }
        else {
            holder.unreadImgV.setVisibility(View.VISIBLE);
            holder.readImgV.setVisibility(View.GONE);
        }
    }

    private void displayAllCheckBoxes() {
        View child;
        for (int i = 0; i <= recyclerviewVG.getChildCount(); i++) {
            child = recyclerviewVG.getChildAt(i);
            if (child != null)
                child.findViewById(R.id.checkReadNotRead).setVisibility(View.VISIBLE);
        }
    }

    private void selectAllCheckBoxes() {
        View child;
        for (int i = 0; i <= recyclerviewVG.getChildCount(); i++) {
            child = recyclerviewVG.getChildAt(i);
            nbSelectedBooks.set(recyclerViewBooks.getAdapter().getItemCount());
            if (child != null) {
                ((CheckBox) child.findViewById(R.id.checkReadNotRead)).setChecked(true);
                child.findViewById(R.id.oneBookInListLyt). setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
            }
        }
    }

    private void unselectAllCheckBoxes() {
        View child;
        for (int i = 0; i <= recyclerviewVG.getChildCount(); i++) {
            child = recyclerviewVG.getChildAt(i);
            nbSelectedBooks.set(0);
            if (child != null) {
                ((CheckBox) child.findViewById(R.id.checkReadNotRead)).setChecked(false);
                child.findViewById(R.id.oneBookInListLyt).setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_background_color));
            }
        }
    }

    private void selectAndUnselectBooksInList(@NonNull BooksViewHolder holder) {
        if (isLongClicked) {
            if (holder.selectedBookCheckbox.isChecked()) {
                holder.selectedBookCheckbox.setChecked(false);
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_background_color));
                nbSelectedBooks.set(nbSelectedBooks.get() - 1);
            }
            else {
                holder.selectedBookCheckbox.setChecked(true);
                holder.oneBookInListLyt.setBackground(context.getResources().getDrawable(R.drawable.background_one_book_in_list_first_dom_light_color));
                nbSelectedBooks.set(nbSelectedBooks.get() + 1);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataTitles.length;
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

        TextView titleTxt, volumeTxt, authorTxt, tag1Txt, tag2Txt, tag3Txt;
        ImageView coverImgV, unreadImgV, readImgV;
        View oneBookInListLyt;
        CheckBox selectedBookCheckbox;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxtView);
            volumeTxt = itemView.findViewById(R.id.volumeTxtView);
            authorTxt = itemView.findViewById(R.id.authorTxtView);
            tag1Txt = itemView.findViewById(R.id.tag1TxtView);
            tag2Txt = itemView.findViewById(R.id.tag2TxtView);
            tag3Txt = itemView.findViewById(R.id.tag3TxtView);
            coverImgV = itemView.findViewById(R.id.coverImgV);
            unreadImgV = itemView.findViewById(R.id.unreadImgV);
            readImgV = itemView.findViewById(R.id.readImgV);
            oneBookInListLyt = itemView.findViewById(R.id.oneBookInListLyt);
            selectedBookCheckbox = itemView.findViewById(R.id.checkReadNotRead);
        }
    }
}