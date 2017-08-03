package com.katiabravo.kbooklisting;

import org.json.JSONArray;

/**
 * Created by wendy on 7/18/2017.
 */

public class Book {
    private String mAvgRating;
    private String mTitle;
    private String mAuthor;
    private String mPgCount;

    public Book(double avgRating, String title, JSONArray author, int pgCount){
        if (avgRating == -1.0){mAvgRating = "N/A";} else { mAvgRating = "" + avgRating;}
        mTitle = title;
        mAuthor = "By: " + author;
        mPgCount = "Pg count: " + pgCount;
    }

    public String getAvgRating() {return mAvgRating;}
    public String getTitle() {return mTitle;}
    public String getAuthor() {return mAuthor;}
    public String getPgCount() {return mPgCount;}
}