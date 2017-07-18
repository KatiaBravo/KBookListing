package com.katiabravo.kbooklisting;

import org.json.JSONArray;

/**
 * Created by wendy on 7/18/2017.
 */

public class Book {

    private JSONArray mAuthor;
    private String mTitle;
    private String mLevel;

    public Book(JSONArray author, String title, String level){
        mAuthor = author;
        mLevel = "By: " + level;
        mTitle = title;
    }

    public JSONArray getAuthor() {return mAuthor;}
    public String getTitle() {return mTitle;}
    public String getLevel() {return mLevel;}
}