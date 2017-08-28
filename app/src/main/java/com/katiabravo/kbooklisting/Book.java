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
    private String authorString;
    public static String na = "N/A";

    public Book(double avgRating, String title, JSONArray author, int pgCount){
        if (avgRating == -1.0){mAvgRating = na;} else { mAvgRating = "" + avgRating;}
        if (pgCount == -1){mPgCount = "Page Count: " + na;} else { mPgCount = "Pg count: " + pgCount;}
        mTitle = title;
        authorString = "" + author;
        mAuthor = "By: " + authorString.replace("[", "").replace("]", "").replaceAll("\"", "").replaceAll(",", ", ");
    }

    public String getAvgRating() {return mAvgRating;}
    public String getTitle() {return mTitle;}
    public String getAuthor() {return mAuthor;}
    public String getPgCount() {return mPgCount;}
}

//TODO: make sure it is rotatable