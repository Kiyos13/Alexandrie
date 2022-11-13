package com.example.alexandrie;

import java.util.ArrayList;

public class MenuSupItem
{
    public String SupItemTitle;
    public ArrayList<Book> SupItemList;

    public MenuSupItem(String SupItemTitle, ArrayList<Book> SupItemList)
    {
        this.SupItemTitle = SupItemTitle;
        this.SupItemList = SupItemList;
    }

    public ArrayList<Book> getSupItemList() {
        return SupItemList;
    }

    public void setSupItemList(ArrayList<Book> supItemList) {
        SupItemList = supItemList;
    }

    public String getSupItemTitle() {
        return SupItemTitle;
    }

    public void setSupItemTitle(String supItemTitle) {
        SupItemTitle = supItemTitle;
    }
}
