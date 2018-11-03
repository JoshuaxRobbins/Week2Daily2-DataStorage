package com.example.josh.week2daily2;

import android.provider.BaseColumns;

import java.sql.Blob;

public class PictureContract {

    public static final String NAME = "Picture.db";
    public static final int VERSION = 1;

    public static final String GET_KEY = "SELECT " +
            FeedEntry.COL_PICTURE + " FROM " +
            FeedEntry.TABLE_NAME + " WHERE " +
            FeedEntry._ID + " =?";

    public static final String UPDATE_KEY = FeedEntry._ID + " =?";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;



    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "picturestable";
        public static final String COL_PICTURE = "picture";

    }

    public static String CREATE_TABLE = "CREATE TABLE " +
            FeedEntry.TABLE_NAME + "(" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COL_PICTURE + " BLOB)";

}
