package com.example.josh.week2daily2;

import android.provider.BaseColumns;

import java.sql.Blob;

public class PictureContract {

    private PictureContract(){

    }

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "picturetable";
        public static final String KEY = "keys";
        public static final String PICTURE = "picture";

    }
}
